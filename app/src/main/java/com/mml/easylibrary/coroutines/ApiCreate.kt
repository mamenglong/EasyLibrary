package com.mml.easylibrary.coroutines

import com.mml.core.logDebug
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.util.concurrent.TimeUnit

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-12-19 上午11:33
 * Description: This is ApiCreate
 * Package: com.m.l.tran.avatar.api
 * Project: TranAvatar
 */
object ApiCreate {
    var CONNECT_TIMEOUT: Long = 20L
    private val client=OkHttpClient.Builder()
    val retrofitBuilder=  Retrofit.Builder().apply {
        client(client.build())
        addConverterFactory(GsonConverterFactory.create())
    }
    var interceptorLog:(msg:String)->Unit={
        logDebug("ApiCreate","retrofitBack = $it")
    }
    private var interceptor: HttpLoggingInterceptor.Logger =object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                //打印retrofit日志
                interceptorLog.invoke(message)
            }
        }

    init {
        client.apply {
            addInterceptor(HttpLoggingInterceptor(interceptor))
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        }
    }
      fun < T> create(clazz:Class<T>): T = run {
        return@run   retrofitBuilder
            .baseUrl("http://open.iciba.com/")
            .build()
            .create(clazz)

    }
    inline fun <reified T> create(): T = run {
     val baseUrl=when(T::class){
         API::class ->{
             API.BASE_URL
         }
         else -> {
               ""
         }
     }
     return@run   retrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(T::class.java)

    }
}