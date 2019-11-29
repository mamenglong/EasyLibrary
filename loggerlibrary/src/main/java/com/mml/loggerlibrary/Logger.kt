package com.mml.loggerlibrary

/**
 * 项目名称：EasyUtils
 * Created by Long on 2019/3/18.
 * 修改时间：2019/3/18 15:45
 */

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Logger {

    private val TOP_BORDER =
        "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════"
    private val LEFT_BORDER = "║ "
    private val BOTTOM_BORDER =
        "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════"
    private var debug: Boolean = false//是否打印log
    private var saveToDisk: Boolean = false//是否存log到sd卡
    private const val CHUNK_SIZE = 106 //设置字节数
    private var logDir = ""//设置文件存储目录
    private var logSize = 1 * 1024 * 1024L//设置log文件大小 k
    private val execu: ExecutorService = Executors.newFixedThreadPool(1)

    private var enableLog = false


    fun v(msg: String,tag: String = "Logger") = log(tag, msg, Log.VERBOSE)
    fun d(msg: String,tag: String = "Logger") = log(tag, msg, Log.DEBUG)
    fun i(msg: String,tag: String = "Logger") = log(tag, msg, Log.INFO)
    fun w(msg: String,tag: String = "Logger") = log(tag, msg, Log.WARN)
    fun e(msg: String,tag: String = "Logger") = log(tag, msg, Log.ERROR)


    private fun targetStackTraceMSg(): String {
        val targetStackTraceElement = getTargetStackTraceElement()
        return if (targetStackTraceElement != null) {
            "at ${targetStackTraceElement.className}.${targetStackTraceElement.methodName}(${targetStackTraceElement.fileName}:${targetStackTraceElement.lineNumber})"
        } else {
            ""
        }
    }

    private fun getTargetStackTraceElement(): StackTraceElement? {
        var targetStackTrace: StackTraceElement? = null
        var shouldTrace = false
        val stackTrace = Thread.currentThread().stackTrace
        for (stackTraceElement in stackTrace) {
            val isLogMethod = stackTraceElement.className == Logger::class.java.name
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement
                break
            }
            shouldTrace = isLogMethod
        }
        return targetStackTrace
    }

    private fun log(tag: String, msg: String, type: Int) {
   /*     if (!this) {//无视约定  错误直接输出
            if (type == Log.ERROR) {

                true.saveToSd(
                    "$type",
                    tag,
                    msg,
                    targetStackTraceMSg()
                )
                Log.e(
                    tag,
                    msgFormat(
                        msg,
                        targetStackTraceMSg()
                    )
                )
            }
            return
        }*/
        val targetStackTraceMSg = targetStackTraceMSg()
        val newMsg = msgFormat(msg, targetStackTraceMSg)
        fun printLog(){
            when (type) {
                Log.VERBOSE -> Log.v(tag, newMsg)
                Log.DEBUG -> Log.d(tag, newMsg)
                Log.INFO -> Log.i(tag, newMsg)
                Log.WARN -> Log.w(tag, newMsg)
                Log.ERROR -> Log.e(tag, newMsg)
            }
        }
        fun logDisk(){
            saveToDisk.saveToSd(
                "$type",
                tag,
                msg,
                targetStackTraceMSg
            )
        }
        when{
            debug or enableLog ->{
                printLog()
            }
        }
        logDisk()


    }

    private fun msgFormat(msg: String, targetStackTraceMSg: String): String {
        val bytes: ByteArray = msg.toByteArray()
        val length = bytes.size
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        var newMsg = " \n$TOP_BORDER\n$LEFT_BORDER\t${sdf.format(Date())}\n$LEFT_BORDER\t$targetStackTraceMSg"
        if (length > CHUNK_SIZE) {
            var i = 0
            while (i < length) {
                val count = Math.min(length - i, CHUNK_SIZE)
                val tempStr = String(bytes, i, count)
                newMsg += "\n$LEFT_BORDER\t$tempStr"
                i += CHUNK_SIZE
            }
        } else {
            newMsg += "\n$LEFT_BORDER\tContent -->$msg"
        }
        newMsg += "\n$BOTTOM_BORDER"
        return newMsg
    }
    private fun Boolean.saveToSd(level: String, TAG: String, msg: String, targetStackTraceMSg: String) {
        if (!this) {
            return
        }
        FileUtils.mkDirs(logDir)
        execu.submit {
            val tag = "time=${SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.US
            ).format(Date())}\nfilter=$TAG\nlocation=$targetStackTraceMSg"
            val data = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()) + "_$level"
            val files = FileUtils.sortByTime(File(logDir))
                ?.filter { it.name.contains(data) }
            val filepath: String
            if (files != null && files.isNotEmpty()) {
                val length: Long = FileUtils.getLeng(files[0])
                if (length > logSize) {
                    val id = files[0].name.replace("${data}_", "").replace(".log", "").toInt() + 1
                    filepath = "$logDir/${data}_$id.log"
                    FileUtils.createFile(filepath)
                } else {
                    filepath = files[0].absolutePath
                }
            } else {
                filepath = "$logDir/${data}_1.log"
                FileUtils.createFile(filepath)
            }
            val split = "-------------------------------------------"
            FileUtils.appendText(File(filepath), "\r\n$tag\nmsg=$msg\n$split")
        }
    }
    /**
     * debug模式输出日志到控制台 
     * @param debug
     */
    fun debug(debug: Boolean): Logger {
        this.debug = debug
        return this
    }
    /**
     * 是否保存文件
     * @param save
     */
    fun saveToDisk(save: Boolean,application: Context): Logger {
        this.saveToDisk = save
        logDir = application.getExternalFilesDir("Logs")!!.path
        return this
    }
    /**
     * 设置每个log的文件大小
     * @param logSize 文件大小 byte
     */
    fun logSize(logSize: Long): Logger {
        this.logSize = logSize
        return this
    }
    /**
     * 设置log文件目录
     * @param logDir 文件目录
     */
    fun logDir(logDir: String): Logger {
        this.logDir = logDir
        return this
    }

    /**
     * 允许打印日志到控制台
     * @param enable
     */
    fun enableLog(enable:Boolean):Logger{
        this.enableLog=enable
        return this
    }
}