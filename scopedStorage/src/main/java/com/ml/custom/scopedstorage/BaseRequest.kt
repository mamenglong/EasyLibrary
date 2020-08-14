package com.ml.custom.scopedstorage

import android.os.Environment
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

open class BaseRequest(val file:File = File("")){

    /**
     * must one of Standard directory
     * @see Environment
     */
    var dirType:String = Environment.DIRECTORY_DOWNLOADS
    var id:String = ""

}