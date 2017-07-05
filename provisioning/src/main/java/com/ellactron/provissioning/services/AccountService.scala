package com.ellactron.provissioning.services

import javax.transaction.Transactional

import com.ellactron.common.rest.CredentialForm
import com.ellactron.provissioning.entities.User
import com.ellactron.provissioning.exceptions.{RecordVarifyException, UserIsExistingException}
import com.ellactron.provissioning.repositories.UsersRepository
import com.ellactron.provissioning.utils.MySQL
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
  * Created by ji.wang on 2017-07-02.
  */
@Service
@EnableTransactionManagement
class AccountService {
  private val logger = Logger.getLogger(this.getClass.getName)

  @Autowired private val usersRepository: UsersRepository = null

  @throws(classOf[UserIsExistingException])
  @Transactional
  def registerUser(registerUserForm: CredentialForm): Int = {
    val user = new User()
    user.setUsername(registerUserForm.getUsername)
    user.setPassword(MySQL.password(registerUserForm.getPassword).asInstanceOf[String])
    try {
      usersRepository.save(user)
    }
    catch {
      case e: Exception => throw new UserIsExistingException(e.getMessage);
    }

    logger.debug("Account " + user.getUsername + " is created.")
    user.getId
  }

  def verifyCredential(credentialForm:CredentialForm): User = {
    try {
      val users = usersRepository.findByCredential(credentialForm.getUsername, MySQL.password(credentialForm.getPassword).asInstanceOf[String])
      users match {
        case null => null
        case list => list.size() match{
          case 0 => null
          case _ => list.get(0)
        }
      }
    }
    catch {
      case e: Exception => throw new RecordVarifyException(e.getMessage);
    }
  }
}
