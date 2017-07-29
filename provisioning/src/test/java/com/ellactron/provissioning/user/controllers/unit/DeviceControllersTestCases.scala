package com.ellactron.provissioning.user.controllers.unit

import com.ellactron.provissioning.configuration.TestApplicationConfiguration
import net.tinybrick.database.tx.configuration.TransactionManagerConfigure
import net.tinybrick.security.configure.SecurityConfiguration
import net.tinybrick.security.social.configure.SecuritySocialConfigure
import net.tinybrick.test.web.unit.ControllerTestBase
import net.tinybrick.web.configure.ApplicationCoreConfigure
import org.junit.Test
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.TestPropertySource

/**
  * Created by ji.wang on 2017-05-15.
  */

@TestPropertySource(locations = Array("classpath:config/config.properties"))
@SpringApplicationConfiguration(classes = Array(
  classOf[ApplicationCoreConfigure],
  classOf[SecurityConfiguration],
  classOf[SecuritySocialConfigure],
  classOf[TransactionManagerConfigure],
  classOf[TestApplicationConfiguration]))
class DeviceControllersTestCases extends ControllerTestBase {
  //@Autowired(required = false) val encryptionManager: IEncryptionManager = null;

  @Test
  @throws[Exception]
  def TestDeviceProvisioningController() {
    /*val resultActions = mockMvc.perform(post("/rest/v1/device/provisioning").session(session).contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isOk())*/
    return
  }
}
