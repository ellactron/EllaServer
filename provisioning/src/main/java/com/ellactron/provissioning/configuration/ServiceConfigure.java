package com.ellactron.provissioning.configuration;

import com.ellactron.provissioning.controllers.UserRegister;
import com.ellactron.provissioning.services.SecurityService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ji.wang on 2017-07-24.
 */
@EnableAutoConfiguration
@Configuration
@PropertySource({"classpath:config/config.properties"})
public class ServiceConfigure {
    @Bean
    @ConditionalOnBean(SecurityService.class)
    public UserRegister getUserRegister() {
        return new UserRegister();
    }

    @Bean
    public SecurityService getSecurityService() {
        return new SecurityService();
    }
}


