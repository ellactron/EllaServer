package com.ellactron.ui;

import com.ellactron.ui.configuration.UIConfiguration;
import net.tinybrick.security.configure.SecurityConfigure;
import net.tinybrick.web.configure.ApplicationCoreConfigure;
import org.apache.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by ji.wang on 2017-06-21.
 */
@ComponentScan
@Import(value = {
        ApplicationCoreConfigure.class,
        SecurityConfigure.class,
        UIConfiguration.class})
public class MainClass {
    static Logger logger = Logger.getLogger(MainClass.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainClass.class);
        app.setBannerMode(Banner.Mode.OFF);

        logger.info("Server is running...");
        app.run(args);
    }
}
