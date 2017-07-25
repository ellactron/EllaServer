package com.ellactron.provissioning.user.services;

import com.ellactron.common.models.Account;
import com.ellactron.provissioning.configuration.RepositoryConfiguration;
import com.ellactron.provissioning.configuration.ServiceConfigure;
import com.ellactron.provissioning.services.AccountService;
import com.google.common.base.Function;
import net.tinybrick.test.web.unit.ServiceUnitTestBase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;

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
    AccountService accountService;

    @Test
    public void testRegisterUser() {
        try {
            accountService.registerUser(
                    new Account(
                            "username@domain.com",
                            "pa55w0rd"), true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testVerifyCredential() {
        Account user = accountService.verifyCredential(new Account(
                "username@domain.com",
                "pa55w0rd"));
        Assert.assertEquals("username@domain.com", user.getUsername());
        Assert.assertNull(user.getPassword());
    }

    @Test
    public void testVerifyNonCredential() {
        Account user = accountService.verifyCredential(new Account(
                "none",
                "pa55w0rd"));
        Assert.assertNull(user);
    }

    @Test
    public void testTimeExpiry() {
        Function<Date, Boolean> dateObjectFunction = (Date a) -> {
            return accountService.verifyTimestamp(a);
        };
        Assert.assertTrue(dateObjectFunction.apply(new Date()));

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -8);
        Assert.assertFalse(dateObjectFunction.apply(cal.getTime()));
    }
}
