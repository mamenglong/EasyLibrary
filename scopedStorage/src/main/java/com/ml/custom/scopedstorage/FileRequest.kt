package com.ml.custom.scopedstorage

import android.os.Environment
import android.provider.MediaStore
import java.io.File

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 16:03
 * Description: This is FileRequest
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */
class FileRequest(file: File=File("")) : BaseRequest(file) {
    @ContentValue(MediaStore.MediaColumns.MIME_TYPE)
    var fileType: String = file.extension
    init {
        fileType = when (file.extension){
            "png","jpg","jpeg","gif"->{
                "image/*"
            }
            "wav","mp3"->{
                "audio/*"
            }
            "mp4"->{
                "video/*"
            }
            "txt"->{
                "text/plain"
            }
            else->{
                "*/*"
            }
        }
    }
    @ContentValue(MediaStore.MediaColumns.DISPLAY_NAME)
    var displayName: String = file.name

    @ContentValue(MediaStore.MediaColumns.RELATIVE_PATH)
    var path: String = file.path.substringBeforeLast(".")
    get()= "$dirType/$field"
    @ContentValue(MediaStore.MediaColumns.TITLE)
    var title :String = file.nameWithoutExtension
    @ContentValue(MediaStore.MediaColumns.DATE_ADDED)
    var date_added:String = "${System.currentTimeMillis()/1000}"
}