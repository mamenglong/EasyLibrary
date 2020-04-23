package com.mml.kotlinextension

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import java.io.*
import kotlin.concurrent.thread
import kotlin.math.E

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 20-3-18 下午3:20
 * Description:  适配 android P 分区存储
 * Package: com.mml.kotlinextension
 * Project: EasyLibrary
 */

//https://developer.android.com/guide/topics/providers/document-provider?hl=zh-cn#search
// Unique request code.
private const val WRITE_REQUEST_CODE: Int = 0x01
private const val READ_REQUEST_CODE: Int = 0x02
private const val READ_TREE_REQUEST_CODE: Int = 0x03
private const val READ_MULTIPLE_REQUEST_CODE: Int = 0x03
private const val EDIT_REQUEST_CODE: Int = 0x04
/**
 * 打开文件管理器选择文件,需要读写权限
 * 在 [onActivityResult] 获取   uri = data.data
 * @param context
 * @param requestCode
 * @param type
 */
fun pickFile(
    context: FragmentActivity,
    requestCode: Int = READ_REQUEST_CODE,
    type: String = "*/*",
    isMultiple: Boolean = false
) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    // Filter to only show results that can be "opened", such as a
    // file (as opposed to a list of contacts or timezones)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    if (isMultiple) {
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }
    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
    /**
     *    获取永久权限。onActivityResult需调用如下进行确认
     *    参考 https://developer.android.com/guide/topics/providers/document-provider?hl=zh-cn#kotlin
     *  val takeFlags: Int = intent.flags and
    (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    // Check for the freshest data.
    contentResolver.takePersistableUriPermission(uri, takeFlags)
     */
    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "*/*"
    context.startActivityForResult(intent, requestCode)
}

/**
 * 选择多文件
 */
fun pickMultipleFiles(context: FragmentActivity, requestCode: Int=READ_MULTIPLE_REQUEST_CODE, type: String = "*/*") {
    pickFile(context, requestCode, type, true)
}

/**
 * 从图库选择图片 使用  ACTION_PICK
 */
fun pickImageFromGallery(context: FragmentActivity, requestCode: Int=READ_MULTIPLE_REQUEST_CODE,isMultiple: Boolean = false){
    val intent = Intent(
        Intent.ACTION_PICK
    )
    intent.data= MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    if (isMultiple) {
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivityForResult(intent, requestCode)
}
/**
 * 打开文件管理器选择文件夹
 * 在 [onActivityResult] 获取   uri = data.data
 * @param context
 * @param requestCode
 * @param type
 */
fun pickFileTree(context: FragmentActivity, requestCode: Int= READ_TREE_REQUEST_CODE) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivityForResult(intent, requestCode)
}
/**
// Here are some examples of how you might call this method.
// The first parameter is the MIME type, and the second parameter is the name
// of the file you are creating:
//
// createFile("text/plain", "foobar.txt");
// createFile("image/png", "mypicture.png");
*/
private fun createFile(context: FragmentActivity,mimeType: String, fileName: String,requestCode: Int= WRITE_REQUEST_CODE) {
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        addCategory(Intent.CATEGORY_OPENABLE)

        // Create a file with the requested MIME type.
        type = mimeType
        putExtra(Intent.EXTRA_TITLE, fileName)
    }
    context.startActivityForResult(intent, requestCode)
}
/**
 * Open a file for writing and append some text to it.
 * https://developer.android.com/guide/topics/providers/document-provider?hl=zh-cn#edit
 */
private fun editDocumentFile(context: FragmentActivity,requestCode: Int= EDIT_REQUEST_CODE) {
    // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's
    // file browser.
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones).
        addCategory(Intent.CATEGORY_OPENABLE)

        // Filter to show only text files.
        type = "text/plain"
    }

    context.startActivityForResult(intent, requestCode)
}
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
): Uri? {
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
    return uri?.let {
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
 * @desc 通过url获取文件名
 * @param context
 * @param uri
 *
 */
fun getFileNameByUri(context: Context, uri: Uri): String {
    var fileName = System.currentTimeMillis().toString()
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    if (cursor != null && cursor.count > 0) {
        cursor.moveToFirst()
        fileName =
            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
        cursor.close()
    }
    return fileName
}

/**
 * URI 转 bitmap
 */
fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}

/**
 * URI 转 bitmap
 * 请注意，您不应在界面线程上执行此操作。请使用 AsyncTask 在后台执行此操作。打开位图后，您可以在 ImageView 中显示该位图。
 */
@Throws(IOException::class)
private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    val parcelFileDescriptor: ParcelFileDescriptor? =
        context.contentResolver.openFileDescriptor(uri, "r")
    val fileDescriptor: FileDescriptor? = parcelFileDescriptor?.fileDescriptor
    val image: Bitmap? = BitmapFactory.decodeFileDescriptor(fileDescriptor)
    parcelFileDescriptor?.close()
    return image
}

/**
 * 从 uri 读取数据
 */
@Throws(IOException::class)
private fun readTextFromUri(context: Context, uri: Uri): String {
    val stringBuilder = StringBuilder()
    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = reader.readLine()
            }
        }
    }
    return stringBuilder.toString()
}

/**
 * 通过uri复制文件
 * @param context
 * @param uri
 * @param fileName
 */
fun copyUriToExternalFilesDir(
    context: Context,
    uri: Uri,
    dir: String,
    fileName: String,
    onSuccess: (String) -> Unit = {}
) {
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