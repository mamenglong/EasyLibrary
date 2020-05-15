package com.mml.kotlinextension
/**
 *Author:MenglongMa
 *Email:mml2015@126.com
 *Date:2020/4/1511:06
 *Description:This is MIME_TYPE
 */
enum class MimeType(val value: String) {
    _image("image/*"),
    _png("image/png"),
    _jpeg("image/jpeg"),
    _jpg("image/jpeg"),
    _webp("image/webp"),
    _gif("image/gif"),
    _bmp("image/bmp"),
    _3gp("video/3gpp"),
    _apk("application/vnd.android.package-archive"),
    _asf("video/x-ms-asf"),
    _avi("video/x-msvideo"),
    _bin("application/octet-stream"),
    _c("text/plain"),
    _class("application/octet-stream"),
    _conf("text/plain"),
    _cpp("text/plain"),
    _doc("application/msword"),
    _docx("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    _xls("application/vnd.ms-excel"),
    _xlsx("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    _exe("application/octet-stream"),
    _gtar("application/x-gtar"),
    _gz("application/x-gzip"),
    _h("text/plain"),
    _htm("text/html"),
    _html("text/html"),
    _jar("application/java-archive"),
    _java("text/plain"),
    _js("application/x-javascript"),
    _log("text/plain"),
    _m3u("audio/x-mpegurl"),
    _m4a("audio/mp4a-latm"),
    _m4b("audio/mp4a-latm"),
    _m4p("audio/mp4a-latm"),
    _m4u("video/vnd.mpegurl"),
    _m4v("video/x-m4v"),
    _mov("video/quicktime"),
    _mp2("audio/x-mpeg"),
    _mp3("audio/x-mpeg"),
    _mp4("video/mp4"),
    _mpc("application/vnd.mpohun.certificate"),
    _mpe("video/mpeg"),
    _mpeg("video/mpeg"),
    _mpg("video/mpeg"),
    _mpg4("video/mp4"),
    _mpga("audio/mpeg"),
    _msg("application/vnd.ms-outlook"),
    _ogg("audio/ogg"),
    _pdf("application/pdf"),
    _pps("application/vnd.ms-powerpoint"),
    _ppt("application/vnd.ms-powerpoint"),
    _pptx("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    _prop("text/plain"),
    _rc("text/plain"),
    _rmvb("audio/x-pn-realaudio"),
    _rtf("application/rtf"),
    _sh("text/plain"),
    _tar("application/x-tar"),
    _tgz("application/x-compressed"),
    _txt("text/plain"),
    _wav("audio/x-wav"),
    _wma("audio/x-ms-wma"),
    _wmv("audio/x-ms-wmv"),
    _wps("application/vnd.ms-works"),
    _xml("text/plain"),
    _z("application/x-compress"),
    _zip("application/x-zip-compressed"),
    _json("application/json"),
    _0("*/*"),
    ;

    companion object {
        fun isImage(mimeType: String?): Boolean {
            return mimeType?.let {
                _webp.value == mimeType ||
                        _png.value == mimeType ||
                        _jpeg.value == mimeType ||
                        _jpg.value == mimeType ||
                        _bmp.value == mimeType ||
                        _gif.value == mimeType
            } ?: false
        }

        fun isGif(mimeType: String?): Boolean {
            return mimeType?.let {
                _gif.value == mimeType
            } ?: false
        }

        fun isApk(mimeType: String?) = mimeType?.let {
            _apk.value == mimeType
        } ?: false

        fun isVideo(mimeType: String?) = mimeType?.let {
            _m3u.value == mimeType || _avi.value == mimeType
        } ?: false
    }
}

fun main(a: Array<String>) {
    val result = StringBuilder()
    type.forEach {
        result.append("_${it.first}(\"${it.second}\"),\n")
    }
}

val type = arrayOf(
    Pair("3gp", "video/3gpp"),
    Pair("apk", "application/vnd.android.package-archive"),
    Pair("asf", "video/x-ms-asf"),
    Pair("avi", "video/x-msvideo"),
    Pair("bin", "application/octet-stream"),
    Pair("bmp", "image/bmp"),
    Pair("c", "text/plain"),
    Pair("class", "application/octet-stream"),
    Pair("conf", "text/plain"),
    Pair("cpp", "text/plain"),
    Pair("doc", "application/msword"),
    Pair("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    Pair("xls", "application/vnd.ms-excel"),
    Pair("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    Pair("exe", "application/octet-stream"),
    Pair("gif", "image/gif"),
    Pair("gtar", "application/x-gtar"),
    Pair("gz", "application/x-gzip"),
    Pair("h", "text/plain"),
    Pair("htm", "text/html"),
    Pair("html", "text/html"),
    Pair("jar", "application/java-archive"),
    Pair("java", "text/plain"),
    Pair("jpeg", "image/jpeg"),
    Pair("jpg", "image/jpeg"),
    Pair("js", "application/x-javascript"),
    Pair("log", "text/plain"),
    Pair("m3u", "audio/x-mpegurl"),
    Pair("m4a", "audio/mp4a-latm"),
    Pair("m4b", "audio/mp4a-latm"),
    Pair("m4p", "audio/mp4a-latm"),
    Pair("m4u", "video/vnd.mpegurl"),
    Pair("m4v", "video/x-m4v"),
    Pair("mov", "video/quicktime"),
    Pair("mp2", "audio/x-mpeg"),
    Pair("mp3", "audio/x-mpeg"),
    Pair("mp4", "video/mp4"),
    Pair("mpc", "application/vnd.mpohun.certificate"),
    Pair("mpe", "video/mpeg"),
    Pair("mpeg", "video/mpeg"),
    Pair("mpg", "video/mpeg"),
    Pair("mpg4", "video/mp4"),
    Pair("mpga", "audio/mpeg"),
    Pair("msg", "application/vnd.ms-outlook"),
    Pair("ogg", "audio/ogg"),
    Pair("pdf", "application/pdf"),
    Pair("png", "image/png"),
    Pair("pps", "application/vnd.ms-powerpoint"),
    Pair("ppt", "application/vnd.ms-powerpoint"),
    Pair("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    Pair("prop", "text/plain"),
    Pair("rc", "text/plain"),
    Pair("rmvb", "audio/x-pn-realaudio"),
    Pair("rtf", "application/rtf"),
    Pair("sh", "text/plain"),
    Pair("tar", "application/x-tar"),
    Pair("tgz", "application/x-compressed"),
    Pair("txt", "text/plain"),
    Pair("wav", "audio/x-wav"),
    Pair("wma", "audio/x-ms-wma"),
    Pair("wmv", "audio/x-ms-wmv"),
    Pair("wps", "application/vnd.ms-works"),
    Pair("xml", "text/plain"),
    Pair("z", "application/x-compress"),
    Pair("zip", "application/x-zip-compressed"),
    Pair("", "*/*")
)


