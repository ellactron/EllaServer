package com.ellactron.provissioning.user.controllers.unit

/**
  * Created by ji.wang on 2017-05-09.
  */

import java.util.HashMap

import com.ellactron.provissioning.MainClass
import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
import net.tinybrick.test.web.unit.ControllerTestBase
import net.tinybrick.utils.crypto.Codec
import org.json.JSONObject
import org.junit.{Assert, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestPropertySource(locations = Array("classpath:config/config.properties"))
@SpringApplicationConfiguration(classes = Array(classOf[MainClass]))
class UserControllersTestCases extends ControllerTestBase {
  @Autowired(required = false) val encryptionManager: IEncryptionManager = null;

  override def getUsername = "username@domain.com"
  override def getPassword = "pa55w0rd"

  @Test
  @throws[Exception]
  def testUserRegisterController() {
    val resultActions = mockMvc.perform(post("/rest/v1/user/register")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("username", getUsername)
      .param("password", getPassword)
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isCreated())
  }

  @Test
  @throws[Exception]
  def testUserRegisterControllerWithInvalidUsername() {
    val resultActions = mockMvc.perform(post("/rest/v1/user/register")
      .session(session)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("username", "username")
      .param("password", getPassword)
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isBadRequest())
  }

  @Test
  @throws[Exception]
  def testGetLoginToken(): Unit = {
    val resultActions = GET("/login/token/" + getUsername + "/" + getPassword, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    resultActions.andDo(print).andExpect(status.isOk)
    val tokenJson = new JSONObject(resultActions.andReturn.getResponse.getContentAsString)
    val token = tokenJson.getString("token")
    if (null != encryptionManager) Assert.assertEquals(getUsername + ":" + getPassword, encryptionManager.decrypt(token))
    else Assert.assertEquals(getUsername + ":" + getPassword, Codec.stringFromBas64(token))
  }

  @Test
  @throws[Exception]
  def testProvintionDeviceController() {
    val data = new HashMap[String, Object]()
    val deviceId = Array[Byte]('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L')
    data.put("deviceId", deviceId)

    val result = POST("/rest/v1/user/provisiondevice",
      MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_JSON,
      data)

    result.andDo(print()).andExpect(status().isOk())
  }
}
