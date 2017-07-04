package com.ellactron.provissioning.user.services;

import com.ellactron.common.rest.CredentialForm;
import com.ellactron.provissioning.configuration.RepositoryConfiguration;
import com.ellactron.provissioning.configuration.ServiceConfigure;
import com.ellactron.provissioning.entities.User;
import com.ellactron.provissioning.services.AccountService;
import net.tinybrick.test.web.unit.ServiceUnitTestBase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            accountService.registerUser(
                    new CredentialForm(
                            "username@domain.com",
                            "bbb"));
        }
        catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testVerifyCredential() {
        User user = accountService.verifyCredential(new CredentialForm(
                "username@domain.com",
                "bbb"));
        Assert.assertEquals("username@domain.com", user.getUsername());
        Assert.assertNull(user.getPassword());
    }

    @Test
    public void testVerifyNonCredential() {
        User user = accountService.verifyCredential(new CredentialForm(
                "none",
                "bbb"));
        Assert.assertNull(user);
    }
}
