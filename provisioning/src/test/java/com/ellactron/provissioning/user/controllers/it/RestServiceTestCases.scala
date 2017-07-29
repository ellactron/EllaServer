package com.ellactron.provissioning.user.controllers.it

/**
  * Created by ji.wang on 2017-05-09.
  */

import java.util.{Arrays, Date, Map}

import com.ellactron.provissioning.MainClass
import net.tinybrick.utils.rest.IRestClient.AUTHENTICATION_METHOD
import org.junit.{Assert, Test}
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.util.LinkedMultiValueMap

@SpringApplicationConfiguration(classes = Array(classOf[MainClass]))
class RestServiceTestCases extends ITTestBase {
  override def getBearer(): String = {
    encryptionManager.encrypt("FACEBOOK\\123456:" + new Date().getTime)
  }

  override def getAuthenticationMethod(): AUTHENTICATION_METHOD = {
    AUTHENTICATION_METHOD.Bearer
  }

  @Test
  @throws[Exception]
  def testUserRegisterRest() {
    val data = new LinkedMultiValueMap[String, String]();
    data.add("username", getUsername());
    data.add("password", getPassword());

    val entity = post(
      "https://localhost" + (if (0 == port) "" else (":" + port)) + "/register",
      data,
      Arrays.asList(MediaType.APPLICATION_XML),
      classOf[Map[String, Object]])

    Assert.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
  }

  @Test
  @throws[Exception]
  def testLoginBySiteToken(): Unit = {
    val entity = get(
      "https://localhost" + (if (0 == port) "" else (":" + port)) + "/rest/v1/user",
      Arrays.asList(MediaType.APPLICATION_JSON),
      classOf[String])

    Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
  }
}
