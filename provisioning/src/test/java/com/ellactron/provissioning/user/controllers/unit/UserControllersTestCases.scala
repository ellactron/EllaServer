package com.ellactron.provissioning.user.controllers.unit

/**
  * Created by ji.wang on 2017-05-09.
  */

import java.util
import java.util.HashMap
import javax.transaction.Transactional

import com.ellactron.provissioning.configuration.TestApplicationConfiguration
import net.tinybrick.database.tx.configuration.TransactionManagerConfigure
import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
import net.tinybrick.security.configure.SecurityConfiguration
import net.tinybrick.security.social.configure.SecuritySocialConfigure
import net.tinybrick.test.web.unit.ControllerTestBase
import net.tinybrick.test.web.unit.ControllerTestBase.POST_DATA_POSITION
import net.tinybrick.utils.crypto.Codec
import net.tinybrick.web.configure.ApplicationCoreConfigure
import org.json.JSONObject
import org.junit.{Assert, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.EnableTransactionManagement

@TestPropertySource(locations = Array("classpath:config/config.properties"))
//@ComponentScan
@SpringApplicationConfiguration(classes = Array(
  classOf[ApplicationCoreConfigure],
  classOf[SecurityConfiguration],
  classOf[SecuritySocialConfigure],
  classOf[TransactionManagerConfigure],
  classOf[TestApplicationConfiguration]))
class UserControllersTestCases extends ControllerTestBase {
  //@Autowired(required = false) val encryptionManager: IEncryptionManager = null;
  @Autowired val applicationContext: ApplicationContext = null;

  override def getUsername = "username@domain.com"

  override def getPassword = "pa55w0rd"

  @Test
  @throws[Exception]
  @EnableTransactionManagement
  @Transactional
  def testUserRegisterController() {
    val resultActions = mockMvc.perform(post("/register")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
      .param("username", "test@domail.com")
      .param("password", getPassword)
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isCreated())
  }

  @Test
  @throws[Exception]
  def testUserRegisterControllerWithInvalidUsername() {
    val resultActions = mockMvc.perform(post("/register")
      .session(session)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .param("username", "username")
      .param("password", getPassword)
      .accept(MediaType.APPLICATION_JSON))
    resultActions.andDo(print()).andExpect(status().isInternalServerError)
  }

  @Test
  @throws[Exception]
  def testGetLoginToken(): Unit = {
    val encryptionManager: IEncryptionManager = applicationContext.getBean(classOf[IEncryptionManager])

    val form = new util.HashMap[String, Object]()
    form.put("username", getUsername)
    form.put("password", getPassword)

    val resultActions = POST("/login/token/",
      MediaType.APPLICATION_FORM_URLENCODED,
      MediaType.APPLICATION_JSON,
      form,
      POST_DATA_POSITION.HEADER)
    resultActions.andDo(print).andExpect(status.isOk)
    val tokenJson = new JSONObject(resultActions.andReturn.getResponse.getContentAsString)
    val token = tokenJson.getString("token")
    if (null != encryptionManager) Assert.assertEquals(getUsername + ":" + getPassword, encryptionManager.decrypt(token))
    else Assert.assertEquals(getUsername + ":" + getPassword, Codec.stringFromBas64(token))
  }

  @Test
  @throws[Exception]
  def testGetSiteTokenWithFacebookToken(): Unit = {
    val encryptionManager: IEncryptionManager = applicationContext.getBean(classOf[IEncryptionManager])

    val resultActions = PUT("/login/facebook/accesstoken/EAAbPHNutvmUBANKg7nZBuKPewQAi6ZCKR5Wk8xwbC3UmG1sExfW2uDPh1NWDUXbXjxicwZA54oFUZBpUH9T9wDpfHNCgkm5Jd6gSnSXgf3jDLKkAx83WiYinDlDR1ve5xWddn45ZBHU97LlrqyaZAZCNz94Ex94Y0gmAqRwkZAmHtgZDZD",
      MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    resultActions.andDo(print).andExpect(status.isOk)
    val tokenJson = new JSONObject(resultActions.andReturn.getResponse.getContentAsString)
    val token = tokenJson.getString("token")
    if (null != encryptionManager) Assert.assertTrue(encryptionManager.decrypt(token).startsWith("FACEBOOK\\"))
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
