package com.ellactron.provissioning.user.repositories;

import com.ellactron.provissioning.configuration.RepositoryConfiguration;
import com.ellactron.provissioning.entities.User;
import com.ellactron.provissioning.repositories.UsersRepository;
import net.tinybrick.test.web.unit.DbUnitTestBase;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by ji.wang on 2017-07-02.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RepositoryConfiguration.class)
public class UserRepositoryTestCase extends DbUnitTestBase {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired private UsersRepository usersRepository;

    @Test
    public void testCreation() throws Exception {
        logger.debug("testCreation()");
        //usersRepository.save(new User("AAA", "aaa"));
        Assert.assertTrue(1 <= usersRepository.findAll().size());
    }

    @Test
    public void testFindAccount() throws Exception {
        logger.debug("testFindAccount()");
        List<User> users = usersRepository.findAccount("123456");
        Assert.assertEquals(1, users.size());
    }
}
