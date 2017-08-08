package com.ellactron.provissioning.services

/**
  * Created by ji.wang on 2017-05-10.
  */

import java.net.URLEncoder
import java.util
import java.util.Date

import com.ellactron.common.forms.CredentialForm
import com.ellactron.common.models.Account
import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
import net.tinybrick.security.authentication.{Authority, ISecurityService, Principal}
import net.tinybrick.security.social.IOAuth2SecurityService
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.{Authentication, AuthenticationException}
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Service

@Service
class SecurityService extends IOAuth2SecurityService[String] with ISecurityService[Principal] {
  val logger = LogManager.getLogger(this.getClass())
  @Autowired val accountService: AccountService = null
  @Autowired val encryptionManager: IEncryptionManager = null

  override def validate(token: UsernamePasswordAuthenticationToken): Unit = {
    val principal = token.getPrincipal
    principal match {
      case p: Principal => {
        val account = new Account(p.getUsername, token.getCredentials.toString)
        account.setRealm(p.getRealm)
        accountService.verifyCredential(account) match {
          case _: Account => return
          case null => throw new AuthenticationException("Invalid credential") {}
        }
      }
      case _ => throw new AuthenticationException("Invalid credential") {}
    }
  }

  override def getPrincipal(token: Authentication): Principal = {
    token match {
      case t: UsernamePasswordAuthenticationToken => {
        val principal = new Principal()

        val usernameParts = t.getPrincipal().asInstanceOf[String].split("\\\\")
        if (usernameParts.size > 1) {
          principal.setUsername(usernameParts(1))
          principal.setRealm(usernameParts(0))
        }
        else {
          principal.setUsername(usernameParts(0))
        }

        principal
      }
      case _ => throw new AuthenticationException("Invalid credential") {}
    }
  }

  override def getAuthorities(iAuthenticationToken: Principal): util.List[Authority[_, _]] = {
    null
  }

  def registerUser(newUser: CredentialForm): Account = {
    accountService.registerUser(new Account(newUser.getUsername, newUser.getPassword))
  }

  override def registerSocialUser(authentication: OAuth2Authentication, source: IOAuth2SecurityService.SOCIAL_SOURCE): String = {
    val username = URLEncoder.encode(authentication.getPrincipal.toString, "UTF-8")
    val password = String.valueOf(new Date().getTime)

    val account = new Account(username, password)
    account.setRealm(source.toString)
    val user = accountService.registerUser(account, false)
    val socialUsername =
      if (null == user.getRealm || "DEFAULT" == user.getRealm) user.getUsername
      else user.getRealm + "\\" + user.getUsername
    encryptionManager.encrypt(socialUsername + ":" + user.getPassword)
  }
}
