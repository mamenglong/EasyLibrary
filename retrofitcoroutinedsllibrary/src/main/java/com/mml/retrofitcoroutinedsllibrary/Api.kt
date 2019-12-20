package com.mml.retrofitcoroutinedsllibrary

import retrofit2.Call
import retrofit2.http.POST

interface Api {
    @POST("dsapi/")
    suspend fun login(): Call<Result<Any>>
}
interface Api1 {
    @POST("user/login")
    suspend fun login(): Any
}