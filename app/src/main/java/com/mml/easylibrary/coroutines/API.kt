package com.mml.easylibrary.coroutines

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
data class  SentenceOneDay(
    @SerializedName("content")
    var content:String?="",
    @SerializedName("note")
    var note:String?="" ,
    @SerializedName("picture")
    var picture:String?=""
)
interface API{
    companion object{
        val BASE_URL: String
            get() = "http://open.iciba.com/"
    }

    //http://open.iciba.com/
    @GET("dsapi/")
    suspend fun aSentenceOneDay():SentenceOneDay
}