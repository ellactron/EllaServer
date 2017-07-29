package com.ellactron.provissioning.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Configuration, Import, PropertySource}

/**
  * Created by ji.wang on 2017-07-29.
  */
@EnableAutoConfiguration
@Configuration
@Import(Array(classOf[RepositoryConfiguration], classOf[ServiceConfiguration], classOf[RestServiceConfiguration]))
@PropertySource(Array("classpath:config/config.properties"))
class TestApplicationConfiguration {

}
