package com.mml.kotlinextension

import android.os.Handler
import android.os.Looper

@JvmOverloads
fun delayRun(delayMillis: Long, looper: Looper? = Looper.myLooper(), block: Runnable) {
    delayRun(delayMillis, looper) { block.run() }
}

/**
 * 延时执行
 * @param delayMillis 延时时间
 * @param looper 默认值  [Looper.myLooper()]
 * @param block 延时逻辑代码
 * @return [Handler]  可在适当的`地方调用 handler.removeCallbacksAndMessages(null)清空循环延时操作
 */
fun delayRun(delayMillis: Long, looper: Looper? = Looper.myLooper(), block: Function0<Unit>):Handler{
    var clooper = looper
    if (clooper == null) {
        Looper.prepare()
        clooper = Looper.myLooper() ?: Looper.getMainLooper()
    }
    val handler=Handler(clooper)
    handler.postDelayed(block, delayMillis)
    return handler
}
@JvmOverloads
fun loppRun(interval: Long, looper: Looper? = Looper.myLooper(), block: Runnable){
    loopRun(interval,looper){block.run()}
}

/**
 * 延时循环执行
 * @param interval 间隔时间
 * @param looper 默认值  [Looper.myLooper()]
 * @param block 循环延时逻辑代码
 * @return [Handler]  可在适当的`地方调用 handler.removeCallbacksAndMessages(null)清空循环延时操作
 */
fun loopRun(interval:Long,looper: Looper?= Looper.myLooper(),block:Function0<Unit>):Handler{
    var clooper = looper
    if (clooper == null) {
        Looper.prepare()
        clooper = Looper.myLooper() ?: Looper.getMainLooper()
    }
    val handler=Handler(clooper)
    val runnable=object :Runnable{
        override fun run() {
            block.invoke()
            handler.postDelayed(this,interval)
        }
    }
    handler.postDelayed(runnable,interval)
    return handler
}