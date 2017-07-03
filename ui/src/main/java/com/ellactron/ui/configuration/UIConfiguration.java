package com.ellactron.ui.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by ji.wang on 2017-06-21.
 */

@EnableWebMvc
@EnableAutoConfiguration
@Configuration
@PropertySource(value = "classpath:config/ui.properties")
public class UIConfiguration {
}
