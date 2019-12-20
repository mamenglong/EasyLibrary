package com.mml.retrofitcoroutinedsllibrary

import retrofit2.Call

 class CoroutineDSL<T> {
    var connectTimeOut=3000L

    var result: T? = null
    var onSuccess: ((T) -> Unit)? = null
        private set
    var onFail: ((msg: String) -> Unit)? = null
        private set
    var onComplete: (() -> Unit)? = null
        private set
     var onTimeOut: (() -> Unit)? = null
         private set
    /**
     * 获取数据成功
     * @param block (T) -> Unit
     */
       fun onSuccess(block: (T) -> Unit) {
        this.onSuccess = block
    }

    /**
     * 获取数据失败
     * @param block (msg: String, errorCode: Int) -> Unit
     */
      fun onFail(block: (msg: String) -> Unit) {
        this.onFail = block
    }

    /**
     * 访问完成
     * @param block () -> Unit
     */
      fun onComplete(block: () -> Unit) {
        this.onComplete = block
    }
     /**
      * 访问超时
      * @param block () -> Unit
      */
     fun onTimeOut(block: () -> Unit) {
         this.onTimeOut = block
     }

    internal fun clean() {
        onSuccess = null
        onComplete = null
        onFail = null
    }
}