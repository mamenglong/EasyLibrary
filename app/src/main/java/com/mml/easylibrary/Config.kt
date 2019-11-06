package com.mml.easylibrary

import com.mml.easyconfig.config.Config
import com.mml.easyconfig.config.ConfigType
import com.mml.easyconfig.config.SharedPreferenceDelegates

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-7-31 上午10:11
 * Description: This is Config
 * Package: com.mml.androidconfig
 * Project: AndroidConfig
 */
object Config: Config() {
    override val isEncode: Boolean
        get() = true
    override val spName: String
        get() = "NIHAO"


    init {
        println("spName:$spName")
    }
    var name by string(key = ConfigType.STRING)
    var user by any(ConfigType.BOOLEAN)
    var users by json<User?>(null)
}
data class User(var name: String)