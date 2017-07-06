package com.ellactron.provissioning

/**
  * Created by ji.wang on 2017-05-10.
  */
package security {
  import java.net.URLEncoder
  import java.util
  import java.util.Date

  import com.ellactron.common.forms.CredentialForm
  import com.ellactron.common.models.Account
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
          /*val usrname = if ((null == t.getRealm || t.getRealm.toUpperCase == "DEFAULT")) {
            t.getUsername
          }
          else {
            t.getRealm.toUpperCase + "\\" + t.getUsername
          }*/
          val credential = new Account(t.getUsername, t.getPassword )
          credential.setRealm(t.getRealm)
          accountService.verifyCredential(credential) match{
            case _: Account => return
            case null => throw new AuthenticationException("Invalid credential"){}
          }
        }
        case _ => throw new AuthenticationException("Invalid credential"){}
      }
    }

    override def getAuthorities(iAuthenticationToken: IAuthenticationToken[_]): util.List[Authority[_, _]] = {
      null
    }

    def registerUser(newUser: CredentialForm): Account = {
      accountService.registerUser(new Account(newUser.getUsername, newUser.getPassword))
    }

    override def registerSocialUser(authentication: OAuth2Authentication, source: IOAuth2SecurityService.SOCIAL_SOURCE): AnyRef = {
      val username = URLEncoder.encode(authentication.getPrincipal.toString, "UTF-8")
      val password = String.valueOf(new Date().getTime)

      val account = new Account(username, password)
      account.setRealm(source.toString)
      val user = accountService.registerUser(account, false)
      val socialUsername =
        if( null == user.getRealm || "DEFAULT" == user.getRealm)user.getUsername
        else user.getRealm + "\\" + user.getUsername
      encryptionManager.encrypt(socialUsername + ":" + user.getPassword)
    }
  }
}
