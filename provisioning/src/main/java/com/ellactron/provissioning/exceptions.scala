package com.ellactron.provissioning

/**
  * Created by ji.wang on 2017-07-02.
  */
package exceptions {
  class InvalidInputException(message:String) extends Exception(message){}
  class UserIsExistingException(message:String) extends Exception(message){}
  class RecordVarifyException(message:String) extends Exception(message){}
  class TokenExpiryException(message:String) extends Exception(message){}
}
