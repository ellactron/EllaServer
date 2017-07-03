package com.ellactron.provissioning

/**
  * Created by ji.wang on 2017-05-10.
  */
package security {
  import java.util

  import net.tinybrick.security.authentication.{Authority, IAuthenticationToken, ISecurityService}
  import org.apache.log4j.Logger
  import org.springframework.context.annotation.Bean

  class Authentication extends ISecurityService {
    val logger = Logger.getLogger(this.getClass())
    override def validate(token: IAuthenticationToken[_]): Unit = {
      logger.debug("User :" + token.getUsername)
    }

    override def getAuthorities(iAuthenticationToken: IAuthenticationToken[_]): util.List[Authority[_, _]] = {
      null
    }
  }
}
