package com.ellactron.provissioning.user.controllers.it

import net.tinybrick.security.authentication.filter.EnhancedBasicAuthenticationFilter
import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
import net.tinybrick.test.web.it.IntegrationTestBase
import net.tinybrick.utils.rest.IRestClient.AUTHENTICATION_METHOD
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired

/**
  * Created by ji.wang on 2017-05-09.
  */
class ITTestBase extends IntegrationTestBase{
  private[it] val logger: Logger = Logger.getLogger(this.getClass)

  override def getAuthenticationMethod: AUTHENTICATION_METHOD = AUTHENTICATION_METHOD.Bearer
  override def getUsername():String = "user"
  override def getPassword():String = "user"

  override def getBearer: String = {
    try
      this.encrypt(this.getUsername + ":" + this.getPassword)
    catch {
      case e: Exception => {
        logger.error(e.getMessage, e)
        throw e
      }
    }
  }

  @Autowired(required = false) private[it] val encryptionManager: IEncryptionManager = null

  @throws[Exception]
  override def encrypt(str: String): String = {
    if (null != encryptionManager && getEnhancedBasic) encryptionManager.encrypt(str)
    else super.encrypt(str)
  }
}
