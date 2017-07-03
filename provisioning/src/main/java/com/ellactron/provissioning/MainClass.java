package com.ellactron.provissioning;

/**
 * Created by ji.wang on 2017-05-09.
 */

import com.ellactron.provissioning.configuration.RepositoryConfiguration;
import com.ellactron.provissioning.configuration.ServiceConfigure;
import net.tinybrick.security.configure.SecurityConfigure;
import net.tinybrick.web.configure.ApplicationCoreConfigure;
import org.apache.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(value = {
        ApplicationCoreConfigure.class,
        ServiceConfigure.class,
        RepositoryConfiguration.class,
        SecurityConfigure.class/*,
        AipDocConfigure.class*/})
public class MainClass {
    static Logger logger = Logger.getLogger(MainClass.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainClass.class);
        app.setBannerMode(Banner.Mode.OFF);

        logger.info("Server is running...");
        app.run(args);
    }
}
