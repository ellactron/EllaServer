package com.ellactron.provissioning.user.services;

import com.ellactron.common.rest.RegisterUserForm;
import com.ellactron.provissioning.configuration.RepositoryConfiguration;
import com.ellactron.provissioning.configuration.ServiceConfigure;
import com.ellactron.provissioning.services.AccountService;
import net.tinybrick.test.web.unit.ServiceUnitTestBase;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.ellactron.provissioning.exceptions.UserIsExistingException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ji.wang on 2017-07-03.
 */
@SpringApplicationConfiguration(value = {
        ServiceConfigure.class,
        RepositoryConfiguration.class,
        ServiceTestCase.TestConfiguration.class
})
public class ServiceTestCase extends ServiceUnitTestBase {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Configuration
    static class TestConfiguration {
        @Bean
        public AccountService getAccountService() {
            return new AccountService();
        }
    }

    @Autowired
    AccountService accountService;;

    @Test
    public void testRegisterUser() {
        try {
            accountService.registerUser(new RegisterUserForm("username@domain.com", "bbb"));
        }
        catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }
}