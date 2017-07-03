package com.ellactron.provissioning.controllers

import java.util
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context

import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseBody}

/**
  * Created by ji.wang on 2017-07-02.
  */
@ControllerAdvice
class ValidationExceptionAdvice {
  @ExceptionHandler(Array(classOf[BindException]))
  @ResponseBody def validationError(ex: BindException,
                                    @Context servletRequest: HttpServletRequest) = {
    val result = ex.getBindingResult
    val fieldErrors = result.getFieldErrors

    val responseBody = new util.HashMap[String, AnyRef]
    responseBody.put("path", servletRequest.getRequestURI)
    responseBody.put("error", fieldErrors)

    new ResponseEntity[AnyRef](responseBody, HttpStatus.BAD_REQUEST)
  }
}
