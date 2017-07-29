package com.ellactron.provissioning.configuration

import com.ellactron.provissioning.services.{AccountService, SecurityService}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}

/**
  * Created by ji.wang on 2017-07-28.
  */
@EnableAutoConfiguration
@Configuration
@PropertySource(Array("classpath:config/config.properties"))
class ServiceConfiguration {
  @Bean def getSecurityService = new SecurityService

  @Bean def getAccountService = new AccountService
}
