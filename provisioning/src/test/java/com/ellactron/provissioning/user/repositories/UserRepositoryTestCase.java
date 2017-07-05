package com.ellactron.provissioning.user.repositories;

import com.ellactron.provissioning.configuration.RepositoryConfiguration;
import com.ellactron.provissioning.entities.User;
import com.ellactron.provissioning.repositories.UsersRepository;
import com.ellactron.provissioning.utils.MySQL;
import net.tinybrick.test.web.unit.DbUnitTestBase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ji.wang on 2017-07-02.
 */
@SpringApplicationConfiguration(RepositoryConfiguration.class)
public class UserRepositoryTestCase extends DbUnitTestBase {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Transactional
    public void testCreation() throws Exception {
        logger.debug("testCreation()");
        User newUser = new User("username", (String) MySQL.password("pa55w0rd"));
        usersRepository.save(newUser);
        Assert.assertTrue(1 == usersRepository.findAccountByUsername("username").size());
    }

    @Test
    public void testFindAccountByName() throws Exception {
        logger.debug("testFindAccount()");
        List<User> users = usersRepository.findAccountByUsername("123456");
        Assert.assertEquals(1, users.size());
    }
}
