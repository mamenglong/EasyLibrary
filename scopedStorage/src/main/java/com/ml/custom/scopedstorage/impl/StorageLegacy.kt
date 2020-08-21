package com.ml.custom.scopedstorage.impl

import android.content.Context
import android.os.Environment
import com.ml.custom.scopedstorage.*
import java.io.File
import java.io.FileNotFoundException

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
        val (response,target,dir) = init(baseRequest, block)
        dir?: kotlin.run {
            return
        }
        target!!
        val result=target.createNewFile()
        if (result) {
            response.onLegacySuccess(target)
        }else{
            response.onFailure(FileAlreadyExistsException(target))
        }
    }
   data class Result(
       val response: FileResponse,
       val file: File?,
       val dir:File?,
   )
    private fun init(baseRequest: BaseRequest, block:FileResponse.()->Unit):Result{
        baseRequest as FileRequest
        val response = fileResponse(block)
        val file = baseRequest.file
        var target:File? = null
        val dir=Environment.getExternalStoragePublicDirectory(baseRequest.dirType)
        dir?.let {
            if (!it.exists()){
                it.mkdirs()
            }
            target = File(dir,file.absolutePath)
            target!!.parentFile.mkdirs()
        } ?: kotlin.run {
            response.onFailure(FileNotFoundException("${baseRequest.dirType} dir does not exist."))
        }
        return Result(response,target,dir)
    }
    override fun <T : BaseRequest> renameFileTo(
        context: Context,
        sourceRequest: T,
        targetRequest: T,
        block:FileResponse.()->Unit
    ) {
        val (response,source,dir) = init(sourceRequest, block)
        dir?: kotlin.run {
            return
        }
        source!!
        if (!source.exists()){
            response.onFailure(FileNotFoundException("${source.absolutePath} does not exist."))
            return
        }
        val target = File(dir,targetRequest.file.absolutePath)
        val result= source.renameTo(target)
        if (result) {
            response.onLegacySuccess(target)
        }else{
            response.onFailure(Exception("fail to rename ${target.absolutePath}."))
        }
    }

    override fun <T : BaseRequest> copyFile(
        context: Context,
        sourceRequest: T,
        targetRequest: T,
        block:FileResponse.()->Unit
    ) {
        val (response,source,dir) = init(sourceRequest, block)
        dir?: kotlin.run {
            return
        }
        source!!
        if (!source.exists()){
            response.onFailure(FileNotFoundException("${source.absolutePath} does not exist."))
            return
        }
        val target = File(dir,targetRequest.file.absolutePath)
        target.parentFile.mkdirs()
        if (target.exists()){
            response.onFailure(FileAlreadyExistsException(target))
            return
        } else{
            target.createNewFile()
        }
        source.inputStream().use {input->
            target.outputStream().use {
                input.copyTo(it)
            }
        }
        response.onLegacySuccess(target)
    }

    override fun <T : BaseRequest> deleteFile(
        context: Context,
        baseRequest: T,
        block:FileResponse.()->Unit
    ) {
        val (response,target,dir) = init(baseRequest, block)
        dir?: kotlin.run {
            return
        }
        target!!
        if (target.exists()){
            if (target.delete()){
                response.onLegacySuccess(target)
            }else{
                response.onFailure(Exception("fail to delete ${target.absolutePath}."))
            }
        }else{
            response.onFailure(FileNotFoundException("${target.absolutePath} does not exist."))
        }
    }

    override fun <T : BaseRequest> queryFile(
        context: Context,
        baseRequest: T,
        block:FileResponse.()->Unit
    ) {
        TODO("Not yet implemented")
    }

}