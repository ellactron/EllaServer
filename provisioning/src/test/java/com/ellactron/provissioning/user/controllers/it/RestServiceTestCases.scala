package com.ellactron.provissioning.user.controllers.it

/**
  * Created by ji.wang on 2017-05-09.
  */

import java.util.{Arrays, List, Map}

import com.ellactron.provissioning.MainClass
import org.junit.{Assert, Test}
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}

@SpringApplicationConfiguration(classes = Array(classOf[MainClass]))
class RestServiceTestCases extends ITTestBase{
  @Test
  @throws[Exception]
  def testUserRegisterRest() {
    val entity = get(
      "http://localhost" + (if(0 == port)  "" else (":" + port)) + "/rest/v1/user/register",
      Arrays.asList(MediaType.APPLICATION_XML),
      classOf[Map[String, Object]])

    Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
    Assert.assertTrue(entity.getBody().get("Greeting").equals("Hello!"));
  }
}
