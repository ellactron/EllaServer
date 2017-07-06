package com.ellactron.provissioning

/**
  * Created by ji.wang on 2017-05-10.
  */
package security {
  import java.net.URLEncoder
  import java.util
  import java.util.Date

  import com.ellactron.common.rest.CredentialForm
  import com.ellactron.provissioning.entities.User
  import com.ellactron.provissioning.services.AccountService
  import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
  import net.tinybrick.security.authentication.{Authority, IAuthenticationToken, UsernamePasswordToken}
  import net.tinybrick.security.social.IOAuth2SecurityService
  import org.apache.log4j.Logger
  import org.springframework.beans.factory.annotation.Autowired
  import org.springframework.security.core.AuthenticationException
  import org.springframework.security.oauth2.provider.OAuth2Authentication
  import org.springframework.stereotype.Service

  @Service
  class SecurityService extends IOAuth2SecurityService {
    val logger = Logger.getLogger(this.getClass())
    @Autowired val accountService: AccountService = null
    @Autowired val encryptionManager: IEncryptionManager = null

    override def validate(token: IAuthenticationToken[_]): Unit = {
      token match {
        case t: UsernamePasswordToken => {
          val usrname = if ((null == t.getRealm || t.getRealm.toUpperCase == "DEFAULT")) {
            t.getUsername
          }
          else {
            t.getRealm.toUpperCase + "\\" + t.getUsername
          }
          val credential = new CredentialForm(usrname, t.getPassword )
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

    def registerUser(newUser: CredentialForm): User = {
      accountService.registerUser(newUser)
    }

    override def registerSocialUser(authentication: OAuth2Authentication, source: IOAuth2SecurityService.SOCIAL_SOURCE): AnyRef = {
      val username = source.toString + "\\" + URLEncoder.encode(authentication.getPrincipal.toString, "UTF-8")
      val password = String.valueOf(new Date().getTime)

      val user = new CredentialForm(username, password)
      accountService.registerUser(user, false)

      encryptionManager.encrypt(username + ":" + password)
    }
  }
}
