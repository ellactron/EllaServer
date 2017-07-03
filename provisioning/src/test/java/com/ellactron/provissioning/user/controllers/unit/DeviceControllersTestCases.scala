package com.ellactron.provissioning.user.controllers.unit

import com.ellactron.provissioning.MainClass
import com.ellactron.provissioning.configuration.ServiceConfigure
import com.ellactron.provissioning.controllers.UserRegister
import net.tinybrick.test.web.unit.ControllerTestBase
import net.tinybrick.web.configure.ApplicationCoreConfigure
import org.junit.Test
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders._
import org.springframework.test.web.servlet.result.MockMvcResultHandlers._
import org.springframework.test.web.servlet.result.MockMvcResultMatchers._

/**
  * Created by ji.wang on 2017-05-15.
  */

@TestPropertySource(locations = Array("classpath:config/config.properties"))
@SpringApplicationConfiguration(classes = Array(classOf[MainClass]))
class DeviceControllersTestCases extends ControllerTestBase{
  @Test
  @throws[Exception]
  def TestDeviceProvisioningController() {
    /*val resultActions = mockMvc.perform(post("/rest/v1/device/provisioning").session(session).contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isOk())*/
    return
  }
}
