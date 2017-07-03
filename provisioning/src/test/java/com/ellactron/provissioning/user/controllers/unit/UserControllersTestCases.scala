package com.ellactron.provissioning.user.controllers.unit

/**
  * Created by ji.wang on 2017-05-09.
  */
import java.util.HashMap

import com.ellactron.provissioning.MainClass
import net.tinybrick.test.web.unit.ControllerTestBase
import org.junit.Test
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestPropertySource(locations = Array("classpath:config/config.properties"))
@SpringApplicationConfiguration(classes = Array(classOf[MainClass]))
class UserControllersTestCases extends ControllerTestBase {
  @Test
  @throws[Exception]
  def TestUserRegisterController() {
    val resultActions = mockMvc.perform(post("/rest/v1/user/register")
      .session(session)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("username","username@domain.com")
      .param("password","pa55w0rd")
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isOk())
  }

  @Test
  @throws[Exception]
  def TestUserRegisterControllerWithInvalidUsername() {
    val resultActions = mockMvc.perform(post("/rest/v1/user/register")
      .session(session)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("username","username")
      .param("password","pa55w0rd")
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isBadRequest())
  }

  @Test
  @throws[Exception]
  def TestProvintionDeviceController() {
    val data = new HashMap[String, Object]()
    val deviceId = Array[Byte]('A','B','C','D','E','F','G','H','I','J','K','L')
    data.put("deviceId", deviceId)

    val result = POST("/rest/v1/user/provisiondevice",
      MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_JSON,
      data)

    result.andDo(print()).andExpect(status().isOk())
  }
}
