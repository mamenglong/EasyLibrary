package com.mml.retrofitcoroutinedsllibrary

import retrofit2.Call
import retrofit2.http.POST

interface Api {
    @POST("user/login")
    suspend fun login(): Call<Any>
}
interface Api1 {
    @POST("user/login")
    suspend fun login(): Any
}