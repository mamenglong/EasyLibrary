package com.mml.kotlinextension

import org.junit.Test

import org.junit.Assert.*
import kotlin.contracts.ExperimentalContracts

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @ExperimentalContracts
    @Test
    fun  nullTest(){
        var ss:String? = null
        var sss="sss"
        ss?.let {

        }
        sss.apply {

        }
        ss?.apply {
            println(this)

        }.isNull {
            println("null")

        }
        sss.isNull {
            println("null")
        }.notNull {
            println(it)
        }
        ss.notNull {
            println(it)
        }
        ss.isNull {
            println("null")
        }.notNull {
            println(it)
        }
       val ssssss= ss?.notNull {
            println(it)
        }.isNull {
           println("null")

       }.notNull {

       }
    }

    @Test
    fun test(){

    }
}
