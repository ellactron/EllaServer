package com.ellactron.provissioning.user.controllers.it

import java.io.{File, FileInputStream}

import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
import net.tinybrick.test.web.it.IntegrationTestBase
import net.tinybrick.utils.rest.IRestClient.AUTHENTICATION_METHOD
import org.apache.logging.log4j.{LogManager, Logger}
import org.springframework.beans.factory.annotation.Autowired

/**
  * Created by ji.wang on 2017-05-09.
  */
class ITTestBase extends IntegrationTestBase {
  private[it] val logger: Logger = LogManager.getLogger(this.getClass)

  override def getAuthenticationMethod: AUTHENTICATION_METHOD = AUTHENTICATION_METHOD.Bearer

  override def getUsername(): String = "username@domain.com"

  override def getPassword(): String = "pa55w0rd"

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

  override def getCertificateInput() = new FileInputStream(new File("C:/Users/ji.wang/pki/clientCerts.crt"))

  override def getPrivateKeyInput() = new FileInputStream(new File("C:/Users/ji.wang/pki/id_rsa.client"))

  override def getKeyType() = "RSA"

  override def getPrivateKeyPass() = "pa55w0rd".toCharArray()

  @Autowired(required = false) private[it] val encryptionManager: IEncryptionManager = null

  @throws[Exception]
  override def encrypt(str: String): String = {
    if (null != encryptionManager && getEnhancedBasic) encryptionManager.encrypt(str)
    else super.encrypt(str)
  }
}
