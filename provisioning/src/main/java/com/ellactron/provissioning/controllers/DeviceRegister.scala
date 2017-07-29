package com.ellactron.provissioning.controllers

import java.util.{HashMap, Map}

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}

/**
  * Created by ji.wang on 2017-05-15.
  */
@RestController
@EnableAutoConfiguration
@RequestMapping(Array("/rest/v1"))
class DeviceRegister {
  @RequestMapping(
    value = Array("/device/announce"),
    method = Array(RequestMethod.PUT),
    consumes = Array(MediaType.ALL_VALUE),
    produces = Array(MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.TEXT_HTML_VALUE))
  @ResponseBody
  def register(name: String): Map[String, Object] = {
    val responseBody: Map[String, Object] = new HashMap()
    responseBody.put("Greeting", "Hello!")
    responseBody
  }
}
