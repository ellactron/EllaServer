package com.ellactron.provissioning.user.controllers.it

/**
  * Created by ji.wang on 2017-05-09.
  */

import java.util.{Arrays, List, Map}

import com.ellactron.provissioning.MainClass
import org.junit.{Assert, Test}
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.util.{LinkedMultiValueMap, MultiValueMap}

@SpringApplicationConfiguration(classes = Array(classOf[MainClass]))
class RestServiceTestCases extends ITTestBase{
  @Test
  @throws[Exception]
  def testUserRegisterRest() {
    val data = new LinkedMultiValueMap[String, String]();
    data.add("username", "valid@email.com");
    data.add("password", "BBB");

    val entity = post(
      "http://localhost" + (if(0 == port)  "" else (":" + port)) + "/rest/v1/user/register",
      data,
      Arrays.asList(MediaType.APPLICATION_XML),
      classOf[Map[String, Object]])

    Assert.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    Assert.assertTrue(entity.getBody().get("account").equals("Hello!"));
  }
}
