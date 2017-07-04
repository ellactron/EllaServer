package com.ellactron.provissioning.services

import javax.transaction.Transactional

import com.ellactron.common.rest.RegisterUserForm
import com.ellactron.provissioning.entities.User
import com.ellactron.provissioning.exceptions.UserIsExistingException
import com.ellactron.provissioning.repositories.UsersRepository
import com.ellactron.provissioning.utils.MySQL
import org.apache.log4j.Logger
import org.hibernate.exception.ConstraintViolationException
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
  def registerUser(registerUserForm: RegisterUserForm): Int = {
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
}
