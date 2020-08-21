package com.ml.custom.scopedstorage

import android.net.Uri
import java.io.File

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 16:03
 * Description: This is FileRequest
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */

fun fileResponse(block:FileResponse.()->Unit) = FileResponse().also(block)
class FileResponse {
    private var onScopedSuccess:(Uri?)->Unit={_->}
    private var onLegacySuccess:(File?)->Unit={_->}

    private var onFailure:(Exception)->Unit={}
    fun onScopedSuccess(block:(Uri?)->Unit){
        onScopedSuccess = block
    }
    fun onLegacySuccess(block:(File?)->Unit){
        onLegacySuccess = block
    }
    fun onFailure(block: (Exception) -> Unit) {
        onFailure = block
    }
    fun onScopedSuccess(any: Uri?){
        onScopedSuccess.invoke(any)
    }
    fun onLegacySuccess(file: File?){
        onLegacySuccess.invoke(file)
    }
    fun onFailure(exception:Exception) {
        onFailure.invoke(exception)
    }
}