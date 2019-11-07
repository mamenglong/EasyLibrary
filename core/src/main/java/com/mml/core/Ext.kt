package com.mml.core

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-20 上午9:51
 * Description: This is Ext
 * Package: com.mml.easydialog
 * Project: EasyLibrary
 */
fun Any.log(msg: String,tag:String="tag"){
    if (BuildConfig.DEBUG){
        Log.i(tag,msg)
    }
}
fun showDebugToast(msg:String)= run {
    if (BuildConfig.DEBUG) {
        Toast.makeText(GlobalContextProvider.getGlobalContext(), msg, Toast.LENGTH_SHORT).show()
    }
}

