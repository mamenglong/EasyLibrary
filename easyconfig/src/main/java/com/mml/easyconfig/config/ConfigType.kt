package com.mml.easyconfig.config

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-7-31 上午11:28
 * Description: This is ConfigType
 * Package: com.mml.easyconfig.config
 * Project: AndroidConfig
 */
interface ConfigType {
   companion object {

       val OFFSET: Int get() = 16
       val MASK: Int get() = 0xF0000
       val ENCODE: Int get() = 1 shl 20
       val BOOLEAN: Int get() = 1 shl OFFSET
       val INT: Int get() = 2 shl OFFSET
       val FLOAT: Int get() = 3 shl OFFSET
       val LONG: Int get() = 4 shl OFFSET
       val DOUBLE: Int get() = 5 shl OFFSET
       val STRING: Int get() = 6 shl OFFSET
       val ARRAY: Int get() = 7 shl OFFSET
   }
}