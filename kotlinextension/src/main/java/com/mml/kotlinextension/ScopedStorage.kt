package com.mml.kotlinextension

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import java.io.*
import kotlin.concurrent.thread

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 20-3-18 下午3:20
 * Description:  适配 android P 分区存储
 * Package: com.mml.kotlinextension
 * Project: EasyLibrary
 */

/**
 * 添加图片到图库,适配android p 分区存储
 * @param context
 * @param bitmap
 * @param mimeType
 * @param displayName
 * @param dir
 * @param compressFormat
 * @return Uri
 */
fun addBitmapToAlbum(
    context: Context,
    bitmap: Bitmap,
    mimeType: String = "image/*",
    displayName: String = "IMG${System.currentTimeMillis()}.jpg",
    dir: String? = null,
    compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG
):Uri? {
    val values = ContentValues()
    values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
    values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH,
            dir?.let { Environment.DIRECTORY_DCIM + "/$dir" } ?: Environment.DIRECTORY_DCIM
        )
    } else {
        values.put(
            MediaStore.MediaColumns.DATA,
            "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_DCIM}${dir?.let { "/$dir" } ?: ""}/$displayName"
        )
    }
    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
   return  uri?.let {
        val outputStream = context.contentResolver.openOutputStream(it)
        outputStream?.apply {
            bitmap.compress(compressFormat, 100, this)
            close()
           // Toast.makeText(context, "Add bitmap to album succeeded.", Toast.LENGTH_SHORT).show()
        }
       it
    }
}

/**
 * 打开文件管理器选择文件,需要读写权限
 * 在 [onActivityResult] 获取   uri = data.data
 * @param context
 * @param requestCode
 * @param type
 */
fun pickFile(context: FragmentActivity,requestCode:Int,type:String="*/*") {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.type = "*/*"
    context.startActivityForResult(intent, requestCode)
}
/**
 * 打开文件管理器选择文件夹
 * 在 [onActivityResult] 获取   uri = data.data
 * @param context
 * @param requestCode
 * @param type
 */
fun pickFileTree(context: FragmentActivity,requestCode:Int) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    context.startActivityForResult(intent, requestCode)
}

/**
 * @desc 通过url获取文件名
 * @param context
 * @param uri
 *
 */
fun getFileNameByUri(context: Context,uri: Uri): String {
    var fileName = System.currentTimeMillis().toString()
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    if (cursor != null && cursor.count > 0) {
        cursor.moveToFirst()
        fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
        cursor.close()
    }
    return fileName
}

fun uriToBitmap(context: Context,uri: Uri): Bitmap? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}

/**
 * 通过uri复制文件
 * @param context
 * @param uri
 * @param fileName
 */
fun copyUriToExternalFilesDir(context: Context,uri: Uri,dir:String, fileName: String,onSuccess:(String)->Unit = {}) {
    thread {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempDir = context.getExternalFilesDir(dir)
/*        inputStream?.let {inputStream->
            tempDir?.let {
                inputStream.saveAsFile(it,1024){msg, progress, uri ->

                }
            }
        }*/
        if (inputStream != null && tempDir != null) {
            val file = File("$tempDir/$fileName")
            val fos = FileOutputStream(file)
            val bis = BufferedInputStream(inputStream)
            val bos = BufferedOutputStream(fos)
            val byteArray = ByteArray(1024)
            var bytes = bis.read(byteArray)
            while (bytes > 0) {
                bos.write(byteArray, 0, bytes)
                bos.flush()
                bytes = bis.read(byteArray)
            }
            bos.close()
            fos.close()
            onSuccess.invoke(file.absolutePath)
           /* context.runOnUiThread {
                Toast.makeText(context, "Copy file into $tempDir succeeded.", Toast.LENGTH_LONG).show()
            }*/
        }
    }
}