package com.ellactron.provissioning.controllers

import java.util.{Date, HashMap, Map}
import javax.validation.Valid

import com.ellactron.common.forms.{CredentialForm, DeviceTGT}
import com.ellactron.provissioning.services.SecurityService
import net.tinybrick.security.authentication.filter.tools.IEncryptionManager
import net.tinybrick.utils.json.JsonMapper
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.web.bind.annotation._
import org.springframework.web.context.WebApplicationContext

/**
  * Created by ji.wang on 2017-05-15.
  */
//@EnableAutoConfiguration
//@RequestMapping(Array("/rest/v1"))
@RestController
class UserRegister {
  val logger = LogManager.getLogger(this.getClass())

  @Autowired private val appContext: WebApplicationContext = null
  @Autowired private val securityService: SecurityService = null

  @RequestMapping(
    value = Array("/register"),
    method = Array(RequestMethod.POST),
    consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE),
    produces = Array(MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.TEXT_HTML_VALUE))
  @ResponseBody
  def register(@Valid registerUserForm: CredentialForm): ResponseEntity[AnyRef] = {
    registerUserForm.setId(securityService.registerUser(registerUserForm).getId)
    registerUserForm.setPassword(null)
    val responseBody: Map[String, Object] = new HashMap()
    responseBody.put("account", registerUserForm)
    new ResponseEntity[AnyRef](responseBody, HttpStatus.CREATED)
  }

  @RequestMapping(
    value = Array("/register/device"),
    method = Array(RequestMethod.POST),
    consumes = Array(MediaType.ALL_VALUE),
    produces = Array(
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE,
      MediaType.TEXT_HTML_VALUE))
  @ResponseBody
  def provision(@RequestBody data: Map[String, Array[Byte]]): Map[String, Object] = {
    val deviceId = data.get("deviceId")

    val tgt = new DeviceTGT()
    tgt.setAccount("username")
    tgt.setDeviceId(deviceId)
    tgt.setTimeStamp(new Date())

    val tgtString = JsonMapper.buildNormalMapper().toJsonString(tgt)
    val encryptionManager = appContext.getBean(classOf[IEncryptionManager])

    val result: Map[String, Object] = new HashMap[String, Object]()
    result.put("tgt", encryptionManager.encrypt(tgtString))
    result
  }
}
