package com.ellactron.provissioning

/**
  * Created by ji.wang on 2017-05-10.
  */
package security {
  import java.util

  import com.ellactron.common.rest.CredentialForm
  import com.ellactron.provissioning.entities.User
  import com.ellactron.provissioning.services.AccountService
  import net.tinybrick.security.authentication.{Authority, IAuthenticationToken, ISecurityService, UsernamePasswordToken}
  import org.apache.log4j.Logger
  import org.springframework.beans.factory.annotation.Autowired
  import org.springframework.security.core.AuthenticationException

  class SecurityService extends ISecurityService {
    val logger = Logger.getLogger(this.getClass())
    @Autowired val accountService: AccountService = null

    override def validate(token: IAuthenticationToken[_]): Unit = {
      token match {
        case t: UsernamePasswordToken => {
          val credential = new CredentialForm(t.getUsername, t.getPassword )
          accountService.verifyCredential(credential) match{
            case _: User => return
            case null => throw new AuthenticationException("Invalid credential"){}
          }
        }
        case _ => throw new AuthenticationException("Invalid credential"){}
      }
    }

    override def getAuthorities(iAuthenticationToken: IAuthenticationToken[_]): util.List[Authority[_, _]] = {
      null
    }
  }
}
