package com.mml.easyconfig

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-7-31 上午10:36
 * Description: This is AndroidConfig
 * Package: com.mml.easyconfig
 * Project: AndroidConfig
 */
@SuppressLint("StaticFieldLeak")
object AndroidConfig {

    /**
     * Initialize to make Config ready to work. If you didn't configure ConfigApplication
     * in the AndroidManifest.xml, make sure you call this method as soon as possible. In
     * Application's onCreate() method will be fine.
     *
     * @param context
     * Application context.
     */
    @JvmStatic
    fun initialize(context: Context) {
        ConfigApplication.sContext = context
        isInitialized=true
    }
    @JvmField
    var isInitialized =false
}