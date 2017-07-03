package com.ellactron.provissioning.services

import com.ellactron.common.rest.RegisterUserForm
import com.ellactron.provissioning.exceptions.UserIsExistingException
import com.ellactron.provissioning.repositories.UsersRepository
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * Created by ji.wang on 2017-07-02.
  */
@Service
class AccountService {
  private val logger = Logger.getLogger(this.getClass.getName)

  @throws(classOf[UserIsExistingException])
  @Autowired private val usersRepository: UsersRepository = null

  def registerUser(registerUserForm: RegisterUserForm): Unit = {
    logger.debug("")
  }
}
