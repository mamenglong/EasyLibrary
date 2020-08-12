package com.ml.custom.scopedstorage

import android.content.Context

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 11:27
 * Description: This is IFile
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */
interface IFile {
    fun<T:BaseRequest> createFile(context: Context,baseRequest: T,fileResponse: FileResponse)
    fun<T:BaseRequest> renameFileTo(context: Context,where: T,baseRequest: T,fileResponse: FileResponse)
    fun<T:BaseRequest> copyFile(context: Context,baseRequest: T,fileResponse: FileResponse)
    fun<T:BaseRequest> deleteFile(context: Context,baseRequest: T,fileResponse: FileResponse)
    fun<T:BaseRequest> queryFile(context: Context,baseRequest: T,fileResponse: FileResponse)
}