package com.mml.kotlinextension

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
