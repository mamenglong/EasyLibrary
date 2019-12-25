package com.mml.retrofitcoroutinedsllibrary

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import java.io.IOException
import java.net.ConnectException

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-12-19 下午3:11
 * Description: This is CoroutineExt 支持生命周期自动管理取消协程
 * Project:
 */
typealias CoroutineException= ((java.lang.Exception)->Unit)?
fun uiScope(handlerException:CoroutineException=null,block: suspend CoroutineScope.() -> Unit) = CoroutineScope(Dispatchers.Main).launch {
    try {
        block()
    } catch (e: Exception) {
        handlerException?.invoke(e)
    }
}

fun ioScope(handlerException:CoroutineException=null,block: suspend CoroutineScope.() -> Unit) = CoroutineScope(Dispatchers.IO).launch {
    try {
        block()
    } catch (e: Exception) {
        handlerException?.invoke(e)
    }
}

fun asyncScopeOnUI(handlerException:CoroutineException=null,block: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.IO).async {
        try {
            block()
        } catch (e: Exception) {
            handlerException?.invoke(e)
        }
    }

fun uiScope(lifecycleOwner: LifecycleOwner,handlerException:CoroutineException=null, block: suspend CoroutineScope.() -> Unit) =
    try {
        val deferred = uiScope(handlerException,block)
        lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))
    } catch (e: Exception) {
        handlerException?.invoke(e)
    }

fun ioScope(lifecycleOwner: LifecycleOwner,handlerException:CoroutineException=null, block: suspend CoroutineScope.() -> Unit) =
    try {
        val deferred = ioScope(handlerException,block)
        lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))
    } catch (e: Exception) {
        handlerException?.invoke(e)
    }

fun asyncScopeOnUI(lifecycleOwner: LifecycleOwner,handlerException:CoroutineException=null, block: suspend CoroutineScope.() -> Unit) =
    try {
        val deferred = asyncScopeOnUI(handlerException,block)
        lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))
    } catch (e: Exception) {
        handlerException?.invoke(e)
    }

/**
 * 扩展函数,协程实现网路请求
 * @param dsl [是带接收者的函数字面量]
 * @see [RetrofitCoroutineDSL]
 */
fun <T> CoroutineScope.retrofit(dsl: RetrofitCoroutineDSL<T>.() -> Unit): Job {
    //在主线程中开启协程
    return this.launch(Dispatchers.Main) {
        val coroutine = RetrofitCoroutineDSL<T>().apply(dsl)
        coroutine.api?.let { call ->
            //async 并发执行 在IO线程中
            val deferred = async(Dispatchers.IO) {
                try {
                    call.execute() //已经在io线程中了，所以调用Retrofit的同步方法
                } catch (e: ConnectException) {
                    coroutine.onFail?.invoke("网络连接出错", -1)
                    null
                } catch (e: IOException) {
                    coroutine.onFail?.invoke("未知网络错误", -1)
                    null
                }
            }
            //当协程取消的时候，取消网络请求
            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                    coroutine.clean()
                }
            }
            //await 等待异步执行的结果
            val response = deferred.await()
            if (response == null) {
                coroutine.onFail?.invoke("返回为空", -1)
            } else {
                response.let {
                    if (response.isSuccessful) {
                        //访问接口成功
                        if (response.body()?.code == 1) {
                            //判断status 为1 表示获取数据成功
                            coroutine.onSuccess?.invoke(response.body()!!.data)
                        } else {
                            coroutine.onFail?.invoke(
                                response.body()?.msg ?: "返回数据为空",
                                response.code()
                            )
                        }
                    } else {
                        coroutine.onFail?.invoke(response.errorBody().toString(), response.code())
                    }
                }
            }
            coroutine.onComplete?.invoke()
        }
    }
}

fun <T> CoroutineScope.retrofit(
    lifecycleOwner: LifecycleOwner,
    dsl: RetrofitCoroutineDSL<T>.() -> Unit
) {
    //在主线程中开启协程
    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(retrofit(dsl)))
}

fun <T> retrofit2(lifecycleOwner: LifecycleOwner, dsl: CoroutineDSL<T>.() -> Unit) {
    //在主线程中开启协程
    uiScope(lifecycleOwner) {
        val coroutine = CoroutineDSL<T>().apply(dsl)
        try {
            val begin = System.currentTimeMillis()
            var isBreak: Boolean = false
            out@ while (System.currentTimeMillis() - begin <= coroutine.connectTimeOut) {
                Log.i("retrofit2", "while")
                coroutine.result?.let {
                    coroutine.onSuccess?.invoke(it)
                    isBreak = true
                }
                if (isBreak) {
                    break@out
                }
            }
            Log.i("retrofit2", "end while")
            if (!isBreak) {
                if (coroutine.result != null) {
                    coroutine.onSuccess?.invoke(coroutine.result!!)
                } else {
                    coroutine.onTimeOut?.invoke()
                }
            }
        } catch (e: Exception) {
            coroutine.onFail?.invoke(e.toString())
        } finally {
            coroutine.onComplete?.invoke()
        }
    }
}