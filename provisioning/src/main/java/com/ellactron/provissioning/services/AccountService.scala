package com.ellactron.provissioning.services

import java.util
import java.util.Date
import javax.transaction.Transactional

import com.ellactron.common.rest.CredentialForm
import com.ellactron.provissioning.entities.User
import com.ellactron.provissioning.exceptions.{InvalidInputException, RecordVarifyException, UserIsExistingException}
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
  def registerUser(newUser: CredentialForm): Int = {
    val now = new Date();

    val user = new User()
    user.setLastActiviteDate(now)
    user.setUsername(newUser.getUsername)
    user.setPassword(MySQL.password(newUser.getPassword).asInstanceOf[String])
    user.setRegisterDate(now)
    try {
      usersRepository.save(user)
    }
    catch {
      case e: Exception => throw new UserIsExistingException(e.getMessage);
    }

    logger.debug("Account " + user.getUsername + " is created.")
    user.getId
  }

  def refactorUser(id:Int, user:User): Int ={
    val now = new Date();

    user.setId(id)
    user.setLastActiviteDate(now)
    try {
      usersRepository.save(user)
    }
    catch {
      case e: Exception => throw new InvalidInputException(e.getMessage);
    }
    user.getId
  }

  @throws(classOf[UserIsExistingException])
  @Transactional
  def registerOrUpdateUser(registerUserForm: CredentialForm): Int = {
    val now = new Date();

    try {
      usersRepository.findByUsername(registerUserForm.getUsername) match {
        case userList: util.ArrayList[User] => {
          if (userList.size > 0) {
            logger.debug("Account " + registerUserForm.getUsername + " is going to be created.")
            val user = userList.get(0);
            user.setPassword(MySQL.password(registerUserForm.getPassword).asInstanceOf[String])
            refactorUser(user.getId, user)
          }
          else {
            registerUser(registerUserForm)
          }
        }
      }
    }
    catch {
      case e: Exception => throw new UserIsExistingException(e.getMessage);
    }
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
