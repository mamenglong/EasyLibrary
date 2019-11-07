package com.mml.kotlinextension

import android.os.Handler
import android.os.Looper

@JvmOverloads
fun delayRun(delayMillis: Long, looper: Looper? = Looper.myLooper(), block: Runnable) {
    delayRun(delayMillis, looper) { block.run() }
}

fun delayRun(delayMillis: Long, looper: Looper? = Looper.myLooper(), block: Function0<Unit>) {
    var clooper = looper
    if (clooper == null) {
        Looper.prepare()
        clooper = Looper.myLooper() ?: Looper.getMainLooper()
    }
    Handler(clooper).postDelayed(block, delayMillis)
}