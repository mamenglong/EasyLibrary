package com.mml.kotlinextension

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@UseExperimental(ExperimentalContracts::class)
inline fun<T> T.isNull(block:()->Unit):T{
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (this == null){
        block()
    }
    return this
}
@UseExperimental(ExperimentalContracts::class)
inline fun<T> T.notNull(block:(T)->Unit){
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    this?.let {
        block(it)
    }
}