package com.ml.custom.scopedstorage

import android.provider.MediaStore
import java.io.File

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 15:47
 * Description: This is FileBeab
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */

open class BaseRequest{
    lateinit var file:File

    @ContentValue(MediaStore.MediaColumns.MIME_TYPE)
    lateinit var type:String

}