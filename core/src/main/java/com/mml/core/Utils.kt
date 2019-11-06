package com.mml.core

import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-20 下午4:30
 * Description: This is Utils
 * Package: com.mml.core
 * Project: EasyLibrary
 */
object Utils {
    @JvmStatic
    fun checkApkExist(context: Context, packageName: String): Boolean {
        if (TextUtils.isEmpty(packageName))
            return false
        return try {
            val info = context.packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_UNINSTALLED_PACKAGES
            )

            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
    @JvmStatic
    fun isAccessibilitySettingsOn(context: Context, className: String): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices =
            activityManager.getRunningServices(100)// 获取正在运行的服务列表
        if (runningServices.size < 0) {
            return false
        }
        runningServices.forEach {
            if (it.service.className.equals(className))
                return true
        }
        return false
    }


    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    @JvmStatic
    fun installApk(context: Context, file: File, authority: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        var uriData: Uri? = null
        val type = "application/vnd.android.package-archive"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            uriData = FileProvider.getUriForFile(context, authority, file)
        } else {
            uriData = Uri.fromFile(file)
        }
        intent.setDataAndType(uriData, type)
        context.startActivity(intent)
    }

    /**
     * 通过包名打开应用
     *
     */
    @JvmStatic
    fun openApp(context: Context, packageName: String){
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(packageName)!!
        context.startActivity(intent)
    }
    /**
     * 实现文本复制功能
     *
     * @param content 复制的文本
     */
    @JvmStatic
    fun copy2Clipboard(context: Context, content: String) {
        if (!TextUtils.isEmpty(content)) {
            // 得到剪贴板管理器
            val cmb =context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cmb.text = content.trim { it <= ' ' }
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            val clipData = ClipData.newPlainText(null, content)
            // 把数据集设置（复制）到剪贴板
            cmb.setPrimaryClip(clipData)
        }
    }
    /**
     * 调用第三方浏览器打开
     * @param context
     * @param url 要浏览的资源地址
     */
    @JvmStatic
    fun openBrowser(context: Context, url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.packageManager) != null) {
            val componentName = intent.resolveActivity(context.packageManager)
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"))
        } else {
            Toast.makeText(context.applicationContext, "请下载浏览器", Toast.LENGTH_SHORT).show()
        }
    }
}