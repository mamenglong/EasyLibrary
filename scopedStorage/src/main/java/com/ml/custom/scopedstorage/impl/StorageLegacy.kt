package com.ml.custom.scopedstorage.impl

import android.content.Context
import com.ml.custom.scopedstorage.BaseRequest
import com.ml.custom.scopedstorage.FileRequest
import com.ml.custom.scopedstorage.FileResponse
import com.ml.custom.scopedstorage.IFile

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 15:58
 * Description: This is StorageLegacy
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */
object StorageLegacy: IFile {
    override fun <T : BaseRequest> createFile(
        context: Context,
        baseRequest: T,
        block:FileResponse.()->Unit
    ) {
        baseRequest as FileRequest
        val file = baseRequest.file
        val path = baseRequest.path
    }

    override fun <T : BaseRequest> renameFileTo(
        context: Context,
        where: T,
        baseRequest: T,
        block:FileResponse.()->Unit
    ) {
        
    }

    override fun <T : BaseRequest> copyFile(
        context: Context,
        sourceRequest: T,
        targetRequest: T,
        block:FileResponse.()->Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> deleteFile(
        context: Context,
        baseRequest: T,
        block:FileResponse.()->Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> queryFile(
        context: Context,
        baseRequest: T,
        block:FileResponse.()->Unit
    ) {
        TODO("Not yet implemented")
    }

}