package com.mml.retrofitcoroutinedsllibrary

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-27 上午11:18
 * Description: This is Ext
 * Package: com.mml.retrofitcoroutinedsllibrary
 * Project: EasyLibrary
 */

/**
 * 扩展函数,协程实现网路请求
 * @param dsl [是带接收者的函数字面量]
 * @see [RetrofitCoroutineDSL]
 */
fun <T> CoroutineScope.retrofit(dsl: RetrofitCoroutineDSL<T>.() -> Unit) {
    //在主线程中开启协程
    this.launch(Dispatchers.Main) {
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
                            coroutine.onFail?.invoke(response.body()?.msg ?: "返回数据为空", response.code())
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