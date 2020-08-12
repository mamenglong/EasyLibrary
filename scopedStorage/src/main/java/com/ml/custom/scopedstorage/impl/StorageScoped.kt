package com.ml.custom.scopedstorage.impl

import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.ml.custom.scopedstorage.BaseRequest
import com.ml.custom.scopedstorage.ContentValue
import com.ml.custom.scopedstorage.FileResponse
import com.ml.custom.scopedstorage.IFile

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 15:58
 * Description: This is StorageScoped
 * for android 11 api 30
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */
object StorageScoped: IFile {

    private val uriExternalMap = hashMapOf<String,Uri>()
    private val uriInternalMap = hashMapOf<String,Uri>()

    init {
        uriExternalMap.put(Environment.DIRECTORY_DOWNLOADS,MediaStore.Downloads.EXTERNAL_CONTENT_URI)
        uriExternalMap.put(Environment.DIRECTORY_DCIM,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        uriExternalMap.put(Environment.DIRECTORY_PICTURES,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        uriExternalMap.put(Environment.DIRECTORY_PODCASTS,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        uriExternalMap.put(Environment.DIRECTORY_MOVIES,MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        uriExternalMap.put(Environment.DIRECTORY_MUSIC,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
       
        uriExternalMap.put(Environment.DIRECTORY_DOWNLOADS,MediaStore.Downloads.INTERNAL_CONTENT_URI)
        uriInternalMap.put(Environment.DIRECTORY_DCIM,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        uriInternalMap.put(Environment.DIRECTORY_PICTURES,MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        uriInternalMap.put(Environment.DIRECTORY_PODCASTS,MediaStore.Audio.Media.INTERNAL_CONTENT_URI)
        uriInternalMap.put(Environment.DIRECTORY_MOVIES,MediaStore.Video.Media.INTERNAL_CONTENT_URI)
        uriInternalMap.put(Environment.DIRECTORY_MUSIC,MediaStore.Audio.Media.INTERNAL_CONTENT_URI)
    }

    override fun <T : BaseRequest> createFile(
        context: Context,
        baseRequest: T,
        fileResponse: FileResponse
    ) {
        val uri = uriExternalMap[baseRequest.type]
        val contentValues = fileRequestConvertContentValues(baseRequest)
        val resultUri= context.contentResolver.insert(uri!!,contentValues)
        resultUri?.let {
            fileResponse.onSuccess(it,null)
        }?: kotlin.run {
            fileResponse.onFailure(Exception("onFailure"))
        }
    }

    override fun <T : BaseRequest> renameFileTo(
        context: Context,
        where: T,
        baseRequest: T,
        fileResponse: FileResponse
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> copyFile(
        context: Context,
        baseRequest: T,
        fileResponse: FileResponse
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> deleteFile(
        context: Context,
        baseRequest: T,
        fileResponse: FileResponse
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> queryFile(
        context: Context,
        baseRequest: T,
        fileResponse: FileResponse
    ) {
        TODO("Not yet implemented")
    }

    private fun<T:BaseRequest> fileRequestConvertContentValues(baseRequest: T):ContentValues{
        val contentValues = ContentValues()
        val fields = baseRequest.javaClass.declaredFields
        fields.forEach {field->
            val contentValue = field.getAnnotation(ContentValue::class.java)
            contentValue?.let {
                val fieldName = field.name
                val methodName = getMethodName("set",fieldName)
                methodName?.let {
                    kotlin.runCatching {
                        val method = baseRequest.javaClass.getMethod(methodName)
                        val value = method.invoke(baseRequest) as String?
                        value?.let {
                            contentValues.put(contentValue.value,value)
                        }
                    }
                }
            }
        }
        return contentValues
    }
    private fun getMethodName(prefix: String, propertyName: String?): String? {
        if (propertyName == null || propertyName.isEmpty()) {
            // shouldn't get here
            return prefix
        }
        val firstLetter = Character.toUpperCase(propertyName[0])
        val theRest = propertyName.substring(1)
        return prefix + firstLetter + theRest
    }
}