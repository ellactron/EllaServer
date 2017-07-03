package com.ellactron.provissioning.configuration

/**
  * Created by ji.wang on 2017-05-09.
  */
import java.util
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context

import net.tinybrick.security.authentication.ISecurityService
import org.apache.log4j.Logger
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseBody}
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@EnableAutoConfiguration
@Configuration
//@EnableConfigurationProperties(value = Array(classOf[PropertySourcesPlaceholderConfigurer]))
@PropertySource(value = Array(
  "classpath:config/config.properties",
  "classpath:config/database.properties"))
class ServiceConfigure {
  val logger = Logger.getLogger(this.getClass())

  @Bean def propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer()

  @Bean def SecurityService: ISecurityService = new com.ellactron.provissioning.security.Authentication()
}
