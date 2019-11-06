package com.mml.easyconfig

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
class ConfigApplication : Application() {
    companion object{
        var sContext: Context? = null
            get() {
                if (field != null)
                    return field
                else
                    throw Exception(ConstantString.RUNTIME_ERROR_APPLICATION_CONTEXT_IS_NULL)
            }
    }
    override fun onCreate() {
        super.onCreate()
        sContext = this
        AndroidConfig.isInitialized=true
    }

    override fun onTerminate() {
        super.onTerminate()
        AndroidConfig.isInitialized=false
    }
}