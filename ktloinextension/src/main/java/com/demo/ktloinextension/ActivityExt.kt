package com.demo.ktloinextension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-10-11 下午4:23
 * Description: This is ActivityExt
 * Package: com.demo.ktloinextension
 * Project: EasyLibrary
 */

fun Activity.showDebugToast(msg:String){
    if (BuildConfig.DEBUG)
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}
fun Activity.showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}
inline fun <reified T : Activity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}
inline fun <reified T : Activity> Context.startActivity(bundle: Bundle) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("bundle",bundle)
    startActivity(intent)
}
fun Any.log(msg: String,tag:String="tag"){
    if (BuildConfig.DEBUG){
        Log.i(tag,msg)
    }
}