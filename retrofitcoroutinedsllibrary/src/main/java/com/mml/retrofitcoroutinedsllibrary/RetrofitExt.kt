package com.mml.retrofitcoroutinedsllibrary

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-12-20 下午5:46
 * Description: This is RetrofitExt
 * Package: com.mml.kotlinextension
 * Project: EasyLibrary
 */
abstract class ApiCreate {
    var CONNECT_TIMEOUT: Long = 20L
    var baseUrl=""
    private val client= OkHttpClient.Builder()
    val retrofitBuilder=  Retrofit.Builder().apply {
        client(client.build())
        addConverterFactory(GsonConverterFactory.create())
    }
    var interceptorLog:(msg:String)->Unit={
        //logDebug("ApiCreate","retrofitBack = $it")
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

    /**
     * rewite
     *
     */
    inline fun <reified T> create(): T = run {
   /*     val baseUrl=when(T::class){
            API::class ->{
                API.BASE_URL
            }
            else -> {
                ""
            }
        }*/
        return@run   retrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(T::class.java)

    }
}