package com.ml.custom.scopedstorage

import android.os.Environment
import com.ml.custom.scopedstorage.impl.StorageLegacy
import com.ml.custom.scopedstorage.impl.StorageScoped

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 15:55
 * Description: This is FileAccessFactory
 * for android <=11(Q) or 10(P) api 30
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */
object FileAccessFactory {
    fun getFile():IFile{
        return if (Environment.isExternalStorageLegacy()) {
            StorageLegacy
        }else{
            StorageScoped
        }
    }
}

fun test(){
    FileAccessFactory.getFile()
}