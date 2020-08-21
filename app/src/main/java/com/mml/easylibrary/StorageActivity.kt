package com.mml.easylibrary

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.ml.custom.scopedstorage.FileAccessFactory
import com.ml.custom.scopedstorage.FileRequest
import java.io.File

class StorageActivity : AppCompatActivity() {
    lateinit var file:File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        file =File(packageName,"ii.txt")
    }

    fun create(view: View) {
        val fileRequest= FileRequest(file)
        //fileRequest.displayName="nihao"
        fileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val fileAccessFactory  = FileAccessFactory.getFile()
        fileAccessFactory.createFile(this,fileRequest){
            onScopedSuccess { uri ->
                uri?.let {
                    val text = "hahahhahahha"
                    contentResolver.openOutputStream(uri as Uri)?.use { outputStream->
                        text.byteInputStream().use {
                            it.copyTo(outputStream)
                        }
                    }
                    com.mml.core.showToast("创建成功： $uri")
                }
            }
            onLegacySuccess {file->
                file?.let {
                    "sssss".byteInputStream().use {input->
                        file.outputStream().use {output->
                            input.copyTo(output)
                        }
                    }
                    com.mml.core.showToast("创建成功： ${file.absolutePath}")

                }
            }
            onFailure {
                println(it.toString())
                com.mml.core.showToast("创建失败$it")
            }
        }
        //saveFileToPublicDir(this,File("hahha","ii1.txt"))
    }
    fun rename(view: View) {
        val sourceFileRequest= FileRequest(file)
        sourceFileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val dstFileRequest= FileRequest(File("hahha.txt"))
        dstFileRequest.displayName = "hahha.txt"
        dstFileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val fileAccessFactory  = FileAccessFactory.getFile()
        fileAccessFactory.renameFileTo(this,sourceFileRequest,dstFileRequest){
            onScopedSuccess{ uri ->
                com.mml.core.showToast("成功$uri")
            }
            onLegacySuccess {
                com.mml.core.showToast("成功${it?.absolutePath}")
            }
            onFailure {
                com.mml.core.showToast("失败$it")
            }
        }
    }
    fun delete(view: View) {
        val fileRequest= FileRequest(file)
        fileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val fileAccessFactory  = FileAccessFactory.getFile()
        fileAccessFactory.deleteFile(this,fileRequest){
            onScopedSuccess { uri ->
                com.mml.core.showToast("成功$uri")
            }
            onLegacySuccess {
                com.mml.core.showToast("成功${it?.absolutePath}")
            }
            onFailure {
                com.mml.core.showToast("失败$it")
            }
        }
    }
    fun copy(view: View) {
        val fileRequest= FileRequest(file)
        fileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val dstFileRequest= FileRequest(File("${System.currentTimeMillis()}.txt"))
        dstFileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val fileAccessFactory  = FileAccessFactory.getFile()
        fileAccessFactory.copyFile(this,fileRequest,dstFileRequest){
            onScopedSuccess { uri ->
                com.mml.core.showToast("成功：$uri")
            }
            onLegacySuccess {
                com.mml.core.showToast("成功${it?.absolutePath}")
            }
            onFailure {
                com.mml.core.showToast("失败$it")
            }
        }
    }
}