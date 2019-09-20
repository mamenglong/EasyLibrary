package com.mml.easyconfig.config


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-7-29 下午12:19
 * Description: This is Config
 * Package: com.mml.easyconfig.config
 * Project: Test
 */
abstract class Config{
    /**
     * share preference名字
     */
   abstract val spName:String
    /**
     * 是否启用加密
     */
    abstract val isEncode:Boolean
    val delegate: SharedPreferenceDelegates
    init {
        println("spName:$spName")
        delegate= SharedPreferenceDelegates(spName,isEncode)
    }
    protected fun boolean(key: Int) = delegate.boolean(false)
    protected fun int(key: Int) = delegate.int(0)
    protected fun float(key: Int) = delegate.float(0f)
    protected fun double(key: Int) = delegate.double()
    protected fun long(key: Int) = delegate.long(0L)
    protected fun string(key: Int) = delegate.string()
    protected fun set(key: Int) = delegate.setString()
    protected fun any(key:Int)=delegate.Object<Any>()
    protected inline fun<reified T> json(default: T,key: String?=null)=delegate.json(default,key)
}