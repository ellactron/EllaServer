package com.ellactron.provissioning.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.orm.jpa.EntityScan
import org.springframework.context.annotation.{Configuration, PropertySource}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
  * Created by ji.wang on 2017-07-02.
  */
@EnableAutoConfiguration
@Configuration
@EnableJpaRepositories(Array("com.ellactron.provissioning.repositories"))
@EntityScan(Array("com.ellactron.provissioning.entities"))
@PropertySource(value = Array("classpath:config/database.properties"))
class RepositoryConfiguration {

}
