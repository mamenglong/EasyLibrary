package com.ml.custom.scopedstorage.impl

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.ml.custom.scopedstorage.*

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 15:58
 * Description: This is StorageScoped
 * for android 11 api 30
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */
object StorageScoped : IFile {

    private val uriExternalMap = hashMapOf<String, Uri>()
    private val uriInternalMap = hashMapOf<String, Uri>()

    init {
        uriExternalMap.put(
            Environment.DIRECTORY_DOWNLOADS,
            MediaStore.Downloads.EXTERNAL_CONTENT_URI
        )
        uriExternalMap.put(Environment.DIRECTORY_DCIM, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        uriExternalMap.put(
            Environment.DIRECTORY_PICTURES,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        uriExternalMap.put(
            Environment.DIRECTORY_PODCASTS,
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        )
        uriExternalMap.put(
            Environment.DIRECTORY_MOVIES,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        )
        uriExternalMap.put(Environment.DIRECTORY_MUSIC, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)

        uriInternalMap.put(
            Environment.DIRECTORY_DOWNLOADS,
            MediaStore.Downloads.INTERNAL_CONTENT_URI
        )
        uriInternalMap.put(Environment.DIRECTORY_DCIM, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        uriInternalMap.put(
            Environment.DIRECTORY_PICTURES,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )

        uriInternalMap.put(
            Environment.DIRECTORY_PODCASTS,
            MediaStore.Audio.Media.INTERNAL_CONTENT_URI
        )
        uriInternalMap.put(
            Environment.DIRECTORY_MOVIES,
            MediaStore.Video.Media.INTERNAL_CONTENT_URI
        )
        uriInternalMap.put(Environment.DIRECTORY_MUSIC, MediaStore.Audio.Media.INTERNAL_CONTENT_URI)
    }

    override fun <T : BaseRequest> createFile(
        context: Context,
        baseRequest: T,
        block: FileResponse.() -> Unit
    ) {
        val fileResponse: FileResponse = fileResponse { block() }
        val uri = uriExternalMap[baseRequest.dirType]
        log("uri:$uri")
        val contentValues = fileRequestConvertContentValues(baseRequest)
        kotlin.runCatching {
            context.contentResolver.insert(uri!!, contentValues)
        }.fold(
            onSuccess = {
                log("${it.toString()}")
                it?.let {
                    fileResponse.onSuccess(it, null)
                } ?: kotlin.run {
                    fileResponse.onFailure(Exception("unknow exception"))
                }
            }, onFailure = {
                fileResponse.onFailure(Exception(it))
            }
        )
    }

    override fun <T : BaseRequest> renameFileTo(
        context: Context,
        sourceRequest: T,
        targetRequest: T,
        block: FileResponse.() -> Unit
    ) {
        val fileResponse = fileResponse(block)
        val uri = query(context,sourceRequest)
        uri?: kotlin.run {
            fileResponse.onFailure(java.lang.Exception("sourceRequest uri is null"))
            return
        }
        val contentValues = fileRequestConvertContentValues(targetRequest)
        val result=context.contentResolver.update(uri,contentValues,null,null)
        if (result>0){
            fileResponse.onSuccess(uri,null)
        }else{
            fileResponse.onFailure(java.lang.Exception("fail"))
        }
    }

    override fun <T : BaseRequest> copyFile(
        context: Context,
        baseRequest: T,
        block: FileResponse.() -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> deleteFile(
        context: Context,
        baseRequest: T,
        block: FileResponse.() -> Unit
    ) {
        val fileResponse = fileResponse(block)
        val uri = query(context,baseRequest)
        uri?: kotlin.run {
            fileResponse.onFailure(java.lang.Exception("file uri is null"))
            return
        }
        val result= context.contentResolver.delete(uri,null,null)
        if (result>0){
            fileResponse.onSuccess(uri,null)
        }else{
            fileResponse.onFailure(Exception("fail to delete"))
        }
    }

    override fun <T : BaseRequest> queryFile(
        context: Context,
        baseRequest: T,
        block: FileResponse.() -> Unit
    ) {
        TODO("Not yet implemented")
    }

    private fun <T : BaseRequest> fileRequestConvertContentValues(baseRequest: T): ContentValues {
        val contentValues = ContentValues()
        val fields = baseRequest.javaClass.declaredFields
        fields.forEach { field ->
            val contentValue = field.getAnnotation(ContentValue::class.java)
            contentValue?.let {
                val fieldName = field.name
                val methodName = getMethodName("get", fieldName)
                methodName?.let {
                    kotlin.runCatching {
                        val method = baseRequest.javaClass.getMethod(methodName)
                         method.invoke(baseRequest) as String?
                    }.onSuccess {
                        it?.let {
                            contentValues.put(contentValue.value, it)
                        }
                    }.onFailure {
                        it.message
                    }
                }
            }
        }
        log("$contentValues")
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
    private fun <T : BaseRequest> query(context: Context, where: T): Uri? {
        val dirUri = uriExternalMap[where.dirType]
        dirUri?:return null
        val projection =
            arrayOf<String>(
                MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.RELATIVE_PATH)
        val selection:String? = "${MediaStore.MediaColumns.DISPLAY_NAME} = ${where.file.name}"
        val selectionArgs = arrayOf<String>()

        val cursor =
            context.contentResolver.query(dirUri, projection, null, null, null)
        return cursor?.let {
            it.moveToFirst()
            if (it.moveToNext()) {
                val id = it.getString(it.getColumnIndex(MediaStore.MediaColumns._ID))
                val name = it.getString(1)
                val path = it.getString(2)
                where.id = "$id"
                log("name:$name id:$id path:$path")
                return Uri.parse("$dirUri/$id")
            }
            null
        } ?: kotlin.run {
            null
        }

    }
    private fun log(msg:String){
        Log.i("Storage",msg)
    }
}