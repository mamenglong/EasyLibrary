package com.mml.easylibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.ml.custom.scopedstorage.FileAccessFactory
import com.ml.custom.scopedstorage.FileRequest
import com.mml.kotlinextension.saveFileToPublicDir
import java.io.File
import java.io.FileWriter
import java.io.OutputStreamWriter

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
            onSuccess { uri, file ->
                val text = "hahahhahahha"
         /*       contentResolver.acquireContentProviderClient(uri!!)?.use { providerClient->
                        providerClient.openFile(uri,"w")?.use {
                            FileWriter(it.fileDescriptor).write(text)
                    }
                }*/
               /*  contentResolver.openFileDescriptor(uri!!,"w")?.use {
                     it.fileDescriptor.sync()
                }*/
                contentResolver.openOutputStream(uri!!)?.use {outputStream->
                    text.byteInputStream().use {
                        it.copyTo(outputStream)
                    }
                   // it.write(text.toByteArray())
                }
                com.mml.core.showToast("创建成功： $uri")
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
        val dstFileRequest= FileRequest(File("haha.txt"))
        dstFileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val fileAccessFactory  = FileAccessFactory.getFile()
        fileAccessFactory.renameFileTo(this,sourceFileRequest,dstFileRequest){
            onSuccess{uri, file ->
                com.mml.core.showToast("成功$uri")
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
            onSuccess { uri, file ->
                com.mml.core.showToast("成功$uri")
            }
            onFailure {
                com.mml.core.showToast("失败$it")
            }
        }
    }
    fun copy(view: View) {

    }
}