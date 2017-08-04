package com.ellactron.provissioning;

/**
 * Created by ji.wang on 2017-05-09.
 */

import com.ellactron.provissioning.configuration.ApplicationConfiguration;
import net.tinybrick.database.tx.configuration.TransactionManagerConfigure;
import net.tinybrick.security.configure.SecurityConfiguration;
import net.tinybrick.security.social.configure.SecuritySocialConfigure;
import net.tinybrick.web.configure.ApplicationCoreConfigure;
import net.tinybrick.web.configure.ShutdownManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableWebMvc
@ComponentScan
@Import(value = {
        ApplicationCoreConfigure.class,
        SecurityConfiguration.class,
        SecuritySocialConfigure.class,
        TransactionManagerConfigure.class,
        ApplicationConfiguration.class/*,
        AipDocConfigure.class*/})
public class MainClass {
    static Logger logger = Logger.getLogger(MainClass.class);

    public static void main(String[] args) throws UnknownHostException {
        System.setProperty("HOSTNAME", InetAddress.getLocalHost().getHostName());

        SpringApplication app = new SpringApplication(MainClass.class);
        app.setBannerMode(Banner.Mode.OFF);

        if(args==null || args.length==0) {
            logger.info("Server is running...");
            app.addListeners(new ApplicationPidFileWriter("application.pid"));
            app.run(args);
        }
        else if (args[0].toLowerCase().equals(ShutdownManager.shutdown_command) && args.length == 3){
            logger.info("Server is sutting down...");
            try {
                ShutdownManager.shutdown(args[1], Integer.valueOf(args[2]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
