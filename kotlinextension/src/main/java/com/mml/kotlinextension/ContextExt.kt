package com.mml.kotlinextension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-11-20 上午10:18
 * Description: This is ContextExt
 * Package: com.mml.kotlinextension
 * Project: EasyLibrary
 */
fun Context.showDebugToast(msg:String){
    if (BuildConfig.DEBUG)
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}
fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
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