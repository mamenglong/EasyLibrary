package com.ml.custom.scopedstorage

import android.provider.MediaStore

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 2020/8/12 16:03
 * Description: This is FileRequest
 * Package: com.ml.custom.scopedstorage
 * Project: EasyLibrary
 */
class FileRequest: BaseRequest() {
    @ContentValue(MediaStore.MediaColumns.DISPLAY_NAME)
    lateinit var displayName:String
    @ContentValue(MediaStore.MediaColumns.RELATIVE_PATH)
    lateinit var path:String
}