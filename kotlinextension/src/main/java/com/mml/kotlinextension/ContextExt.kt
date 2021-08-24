package com.mml.kotlinextension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-11-20 上午10:18
 * Description: This is ContextExt
 * Package: com.mml.kotlinextension
 * Project: EasyLibrary
 */
fun Context.showDebugToast(msg:String,debug:Boolean =true){
    if (debug)
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
//----------尺寸转换----------

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (pxValue / scale + 0.5f).toInt()
}

//----------屏幕尺寸----------

fun Context.getScreenWidth(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        ?: return resources.displayMetrics.widthPixels
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.x
}

fun Context.getScreenHeight(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        ?: return resources.displayMetrics.heightPixels
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.y
}


//----------NetWork----------

/**
 * 打开网络设置
 */
fun Context.openWirelessSettings() {
    startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

/**
 * 网络是否连接
 */
fun Context.isConnected(): Boolean {
    var info = this.getActiveNetworkInfo()
    return info?.isConnected?:false
}

/**
 * 判断网络是否是移动数据
 */
fun Context.isMobileData(): Boolean {
    var info = this.getActiveNetworkInfo()
    return (null != info
            && info.isAvailable
            && info.type == ConnectivityManager.TYPE_MOBILE)
}

/**
 * 退回到桌面
 */
fun Context.startHomeActivity() {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    startActivity(homeIntent)
}


@SuppressLint("MissingPermission")
private fun Context.getActiveNetworkInfo(): NetworkInfo? {
    var manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return manager.activeNetworkInfo
}
