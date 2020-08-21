package com.mml.kotlinextension

import java.io.IOException
import java.lang.Exception

fun interface Success<S>{
    fun onSuccess(value:S)
}
fun interface Failure<F>{
    fun onFailure(value: F)
}
fun interface Triple<T>{
    fun onTriple(value: T)
}
fun main(){
   test(resultCallback{
       onFailure{
          println("onFailure")
       }
       onSuccess {
           println("onSuccess")
       }
   })
}
fun test(result: ResultCallback<IOException,Exception>){
     result.onSuccess(IOException())
     result.onFailure(Exception(""))
}
typealias  ResultCallbackType<T,R> = ResultCallback<T,R>.()->Unit
typealias  TripleResultCallbackType<S,F,T> = TripleResultCallback<S,F,T>.()->Unit
fun<T,R> resultCallback(block:ResultCallbackType<T,R>) = ResultCallback<T,R>().apply(block)
fun <S,F,T> tripleResultCallback(block:TripleResultCallbackType<S,F,T>) = TripleResultCallback<S,F,T>().apply(block)
open class ResultCallback<S,F>{
    private var success:Success<S> = Success {
        println("default onSuccess")
    }
    private var failure:Failure<F> = Failure{
        println("default onFailure")

    }
    fun onSuccess(onSuccess:Success<S>) {
        this.success = onSuccess
    }

    fun onFailure(onFailure: Failure<F>) {
        this.failure = onFailure
    }
    fun onSuccess(value: S) {
        success.onSuccess(value)
    }

    fun onFailure(e: F) {
        failure.onFailure(e)
    }
}

class TripleResultCallback<S,F,T>: ResultCallback<S,F>() {
     private var triple = Triple<T> {
         println("default onTriple")
     }
    fun onTriple(value:T){
        triple.onTriple(value)
    }
    fun onTriple(block:Triple<T>){
        triple = block
    }
}


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