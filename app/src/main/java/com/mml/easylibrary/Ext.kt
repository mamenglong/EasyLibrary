package com.mml.easylibrary

import android.os.Looper
import android.widget.Toast

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-20 上午10:01
 * Description: This is Ext
 * Package: com.mml.easylibrary
 * Project: EasyLibrary
 */
fun showToast(msg:String)= run {
    val looper= Looper.getMainLooper()
    if (looper== Looper.myLooper()){
        Toast.makeText(GContextProvider.getGlobalContext(),msg,Toast.LENGTH_SHORT).show()
    }
}