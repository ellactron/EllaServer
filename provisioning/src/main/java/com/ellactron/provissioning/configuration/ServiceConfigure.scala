package com.ellactron.provissioning.configuration

/**
  * Created by ji.wang on 2017-05-09.
  */

import net.tinybrick.security.authentication.ISecurityService
import org.apache.log4j.Logger
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@EnableAutoConfiguration
@Configuration
@PropertySource(value = Array(
  "classpath:config/config.properties"))
class ServiceConfigure {
  val logger = Logger.getLogger(this.getClass())

  @Bean def propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer()
  //@Bean def SecurityService: ISecurityService = new com.ellactron.provissioning.security.SecurityService()
}
