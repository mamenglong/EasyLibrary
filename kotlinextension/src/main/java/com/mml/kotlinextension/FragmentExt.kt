package com.mml.kotlinextension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-10-11 下午4:23
 * Description: This is ActivityExt
 * Package: com.demo.kotlinextension
 * Project: EasyLibrary
 */

fun Fragment.showDebugToast(msg:String){
    if (BuildConfig.DEBUG)
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
}
fun Fragment.showToast(msg:String){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
}
inline fun <reified T : Activity> Fragment.startActivity() {
    val intent = Intent(this.context, T::class.java)
    startActivity(intent)
}
inline fun <reified T : Activity> Fragment.startActivity(bundle: Bundle) {
    val intent = Intent(this.activity, T::class.java)
    intent.putExtra("bundle",bundle)
    startActivity(intent)
}