package com.ellactron.provissioning.utils

/**
  * Created by ji.wang on 2017-07-04.
  */

import net.tinybrick.utils.crypto.DATA_FORMAT._
import net.tinybrick.utils.crypto.SHA1

object MySQL {
  def password(pwd: String): AnyRef = {
    "*" + SHA1.hash(SHA1.hash(pwd, RAW).asInstanceOf[Array[Byte]], HEX)
  }
}
