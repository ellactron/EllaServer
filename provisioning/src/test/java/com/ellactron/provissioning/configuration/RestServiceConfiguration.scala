package com.ellactron.provissioning.configuration

import com.ellactron.provissioning.controllers.UserRegister
import com.ellactron.provissioning.services.SecurityService
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}

/**
  * Created by ji.wang on 2017-07-28.
  */
@EnableAutoConfiguration
@Configuration
@PropertySource(Array("classpath:config/config.properties"))
class RestServiceConfiguration {
  @Bean
  @ConditionalOnBean(Array(classOf[SecurityService]))
  def getUserRegister = new UserRegister
}
