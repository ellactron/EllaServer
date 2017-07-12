package com.ellactron.provissioning.services

import java.util
import java.util.Date
import javax.transaction.Transactional

import com.ellactron.common.models.Account
import com.ellactron.provissioning.entities.User
import com.ellactron.provissioning.exceptions.{RecordVarifyException, TokenExpiryException, UserIsExistingException}
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
  private def registerUser(account: Account): Account = {
    val now = new Date();

    val user = getUserEntityFromAccount(account)
    user.setLastActiviteDate(now)
    user.setRegisterDate(now)
    try {
      logger.debug("Account " + account.getUsername + " is going to be created.")
      usersRepository.save(user)
    }
    catch {
      case e: Exception => throw new UserIsExistingException(e.getMessage);
    }

    logger.debug("Account " + user.getUsername + " is created.")
    getAccountFromUserEntity(user)
  }

  /**
    *
    * @param account
    * @param failIfExisting
    * @throws com.ellactron.provissioning.exceptions.UserIsExistingException
    * @return
    */
  @throws(classOf[UserIsExistingException])
  def registerUser(account: Account, failIfExisting: Boolean = true): Account = {
    val now = new Date();
    usersRepository.findByUsername(getUserEntityFromAccount(account).getUsername) match {
      case userList: util.ArrayList[User] => {
        if (userList.size == 0) {
          try {
            registerUser(account)
          }
          catch {
            case e: Exception => throw new UserIsExistingException(e.getMessage);
          }
        }
        else {
          if (failIfExisting) {
            throw new UserIsExistingException(account.getUsername + " is existing")
          }
          else {
            account.setId(userList.get(0).getId)
            account
          }
        }
      }
    }
  }

  private def getUserEntityFromAccount(account: Account): User = {
    if (null == account.getRealm || "DEFAULT" == account.getRealm) {
      new User(account.getUsername, account.getPassword match {
        case null => null
        case password: String =>{
          MySQL.password(password).asInstanceOf[String]
        }
      })
    }
    else {
      new User(account.getRealm + "\\" + account.getUsername, account.getPassword)
    }
  }

  private def getAccountFromUserEntity(user: User): Account = {
    val account = new Account()
    account.setPassword(user.getPassword)
    account.setId(user.getId)
    val usernameParts = user.getUsername.split("\\\\")
    val a = if (usernameParts.length > 1) {
      account.setRealm(usernameParts(0))
      account.setUsername(usernameParts(1))
    }
    else
      account.setUsername(usernameParts(0))

    account
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
    * @param credential
    * @return
    */
  def verifyCredential(credential: Account): Account = {
    val user = getUserEntityFromAccount(credential)

    if (null == credential.getRealm || "DEFAULT" == credential.getRealm) {
      try {
        val users = usersRepository.findByCredential(user.getUsername, user.getPassword)
        users match {
          case null => null
          case list => list.size() match {
            case 0 => null
            case _ => getAccountFromUserEntity(list.get(0))
          }
        }
      }
      catch {
        case e: Exception => throw new RecordVarifyException(e.getMessage);
      }
    }
    else {
      if(!verifyTimestamp(new Date(java.lang.Long.valueOf(credential.getPassword)))) {
        throw new TokenExpiryException("Token is expiry")
      }
      credential.setPassword((new Date).getTime().toString)
      return credential
    }
  }

  def verifyTimestamp(timeStamp:Date): Boolean = {
    import java.util.Calendar
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.WEEK_OF_MONTH, -1)
    val deadline = calendar.getTime
    deadline.before(timeStamp)
  }
}
