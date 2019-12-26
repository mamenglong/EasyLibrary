package com.mml.kotlinextension

import android.content.Context
import android.content.IntentFilter
import android.content.res.AssetManager
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-12-24 上午9:45
 * Description: This is FileExt
 * Package: com.mml.kotlinextension
 * Project: EasyLibrary
 */

internal typealias OnProgress = ((msg:String, progress:Int, uri: Uri?)->Unit)?
 //默认参数调用需要具名   saveFile(origin=origin, newFile=newFile, onProgress=onProgress)


/**
 * @desc AssetManager扩展函数,此函数执行如果[file]存在会替换
 * @param assertName  assert文件路径名
 * @param file 新的文件
 * @param onProgress 进度
 */
@JvmOverloads
fun AssetManager.copyAssetsFile(assertName:String, file: File,bufferSize:Int=1024, onProgress:OnProgress=null){
    file.exists().yes {
        file.delete()
    }.no {
        file.parentFile?.mkdirs()
    }
    file.createNewFile()
    val assetInputStream = open(assertName)
    val mFileOutputStream = FileOutputStream(file)
    try {
        var read: Int = -1
        var local=0
        val all=assetInputStream.available()
        assetInputStream.use { input ->
            val byteArray= ByteArray(bufferSize)
            mFileOutputStream.use {out->
                while (input.read(byteArray).also { read = it;local+=it} != -1) {
                    out.write(byteArray,0,read)
                    onProgress?.invoke("写入中...", ((local*1.0f/all)*100).toInt(),null)
                }
            }
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        onProgress?.invoke("${t.cause}",-1,null)
    }
}

/**
 * @desc 此函数执行如果[newFile]存在会替换
 * @param newFile 新的文件
 * @param onProgress 进度
 */
@JvmOverloads
fun InputStream.saveAsFile(newFile: File,bufferSize: Int=10240,onProgress: OnProgress=null){
    saveFile(this,newFile,bufferSize,onProgress)
}
/**
 * @desc 此函数执行如果[newFile]存在会替换,默认缓冲区10240
 * @param newFile 新的文件
 * @param onProgress 进度
 */

@JvmOverloads
fun File.copyAndRename(newFile: File ,bufferSize: Int=10240,onProgress:OnProgress=null){
    saveFile(this, newFile,bufferSize, onProgress)
}

/**
 * @desc 此函数执行如果[file]存在会替换
 * @param context 上下文
 * @param assertName  assert文件路径名
 * @param file 新的文件
 * @param onProgress 进度
 */
@JvmOverloads
fun copyAssetsFile(context: Context, assertName:String, file: File,bufferSize: Int=1024, onProgress:OnProgress=null){
    file.exists().yes {
        file.delete()
    }.no {
        file.parentFile?.mkdirs()
    }
    file.createNewFile()
    val assetInputStream = context.assets.open(assertName)
    val mFileOutputStream = FileOutputStream(file)
    try {
        var read: Int = -1
        var local=0
        val all=assetInputStream.available()
        assetInputStream.use { input ->
            val byteArray= ByteArray(bufferSize)
            mFileOutputStream.use {out->
                while (input.read(byteArray).also { read = it;local+=it} != -1) {
                    out.write(byteArray,0,read)
                    onProgress?.invoke("写入中...", ((local*1.0f/all)*100).toInt(),null)
                }
            }
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        onProgress?.invoke("${t.cause}",-1,null)
    }
}


/**
 * @desc 此函数执行如果[newFile]存在会替换
 * @param origin 源file文件
 * @param newFile 新的文件
 * @param onProgress 进度
 */
@JvmOverloads
fun saveFile(origin: InputStream, newFile: File,bufferSize: Int=10240, onProgress:OnProgress=null){
    if (newFile.exists()){
        newFile.delete()
    }
    newFile.parentFile?.mkdirs()
    newFile.createNewFile()
    val mFileOutputStream = FileOutputStream(newFile)
    try {
        var read: Int = -1
        var local=0
        val all= origin.available()
        origin.use { input ->
            val byteArray= ByteArray(bufferSize)
            mFileOutputStream.use {out->
                while (input.read(byteArray).also { read = it;local+=it} != -1) {
                    out.write(byteArray,0,read)
                    onProgress?.invoke("写入中...", ((local*1.0f/all)*100).toInt(),null)
                }
            }
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        onProgress?.invoke("${t.cause}",-1,null)
    }
}

/**
 * @desc 此函数执行如果[newFile]存在会替换
 * @param origin 源file文件
 * @param newFile 新的文件
 * @param onProgress 进度
 */
@JvmOverloads
fun saveFile(origin: File, newFile: File,bufferSize: Int=10240, onProgress:OnProgress=null){
    if (newFile.exists()){
        newFile.delete()
    }
    newFile.parentFile?.mkdirs()
    newFile.createNewFile()
    val assetInputStream = origin.inputStream()
    val mFileOutputStream = FileOutputStream(newFile)
    try {
        var read: Int = -1
        var local=0
        val all=assetInputStream.available()
        assetInputStream.use { input ->
            val byteArray= ByteArray(bufferSize)
            mFileOutputStream.use {out->
                while (input.read(byteArray).also { read = it;local+=it} != -1) {
                    out.write(byteArray,0,read)
                    onProgress?.invoke("写入中...", ((local*1.0f/all)*100).toInt(),null)
                }
            }
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        onProgress?.invoke("${t.cause}",-1,null)
    }
}
/**
 * @desc 此函数执行如果[newFile]存在会替换
 * @param origin 源file文件
 * @param newFile 新的文件
 * @param onProgress 进度
 */
/*fun copyAndRename(origin: File, newFile: File, onProgress:((msg:String, progress:Int, uri: Uri?)->Unit)?=null){
    saveFile(origin, newFile, onProgress)
}*/
/**
 * @desc 此函数执行如果[newFile]存在会替换
 * @param origin 源file文件
 * @param newFile 新的文件
 * @param bufferSize 缓冲区大小 不带此参数默认10240
 * @param onProgress 进度
 */
@JvmOverloads
fun copyAndRename(origin: InputStream, newFile: File,bufferSize: Int=10240, onProgress: OnProgress=null){
    saveFile(origin, newFile,bufferSize, onProgress)
//    saveFile(origin=origin, newFile=newFile, onProgress=onProgress)
}