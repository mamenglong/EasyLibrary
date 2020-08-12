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
    private var onSuccess:(Uri?, File?)->Unit={_,_->}
    private var onFailure:(Exception)->Unit={}
    fun onSuccess(block:(Uri?, File?)->Unit){
        onSuccess = block
    }
    fun onFailure(block: (Exception) -> Unit) {
        onFailure = block
    }
    fun onSuccess(uri:Uri?,file: File?){
        onSuccess.invoke(uri,file)
    }
    fun onFailure(exception:Exception) {
        onFailure.invoke(exception)
    }
}