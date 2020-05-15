package com.mml.kotlinextension

class Result<T,R> {
    private var onSuccess: ((T) -> Unit) = {}
    private var onFailure: ((R) -> Unit) = {}

    fun onSuccess(onSuccess: ((T) -> Unit)) {
        this.onSuccess = onSuccess
    }

    fun onFail(onFailure: ((R) -> Unit)) {
        this.onFailure = onFailure
    }

    fun doSuccess(value: T) {
        onSuccess.invoke(value)
    }

    fun doFailure(e: R) {
        onFailure(e)
    }
}