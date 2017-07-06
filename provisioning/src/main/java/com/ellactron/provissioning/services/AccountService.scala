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
  private def registerUser(newUser: CredentialForm): User = {
    val now = new Date();

    val user = new User()
    user.setLastActiviteDate(now)
    user.setUsername(newUser.getUsername)
    user.setPassword(MySQL.password(newUser.getPassword).asInstanceOf[String])
    user.setRegisterDate(now)
    try {
      logger.debug("Account " + newUser.getUsername + " is going to be created.")
      usersRepository.save(user)
    }
    catch {
      case e: Exception => throw new UserIsExistingException(e.getMessage);
    }

    logger.debug("Account " + user.getUsername + " is created.")
    user
  }

  /**
    *
    * @param newUser
    * @param failIfExisting
    * @throws com.ellactron.provissioning.exceptions.UserIsExistingException
    * @return
    */
  @throws(classOf[UserIsExistingException])
  def registerUser(newUser: CredentialForm, failIfExisting: Boolean=true): User = {
    val now = new Date();

    usersRepository.findByUsername(newUser.getUsername) match {
      case userList: util.ArrayList[User] => {
        if (userList.size == 0) {
          try {
            registerUser(newUser)
          }
          catch {
            case e: Exception => throw new UserIsExistingException(e.getMessage);
          }
        }
        else {
          if (failIfExisting) {
            throw new UserIsExistingException(newUser.getUsername + " is existing")
          }
          else userList.get(0);
        }
      }
    }
  }

  /*
  @Transactional
  def refactorUser(id:Int, user:User): User ={
    val now = new Date();

    user.setId(id)
    user.setLastActiviteDate(now)
    try {
      usersRepository.save(user)
      logger.debug("Account " + user.getUsername + " is updated.")
    }
    catch {
      case e: Exception => throw new InvalidInputException(e.getMessage);
    }
    user
  }
  */

  /**
    *
    * @param credentialForm
    * @return
    */
  def verifyCredential(credentialForm: CredentialForm): User = {
    try {
      val users = usersRepository.findByCredential(credentialForm.getUsername, MySQL.password(credentialForm.getPassword).asInstanceOf[String])
      users match {
        case null => null
        case list => list.size() match {
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
