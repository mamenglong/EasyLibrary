package com.mml.easylibrary.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mml.easylibrary.R
import com.mml.easylibrary.showToast
import com.mml.retrofitcoroutinedsllibrary.*
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CoroutinesActivity : AppCompatActivity()  {
    val TAG="CoroutinesActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
        initASentenceOneDay()
    }
    private fun initASentenceOneDay() {
       /*  CoroutineScope(Dispatchers.Main).launch{
             retrofit<Any> {
                 launch {
                     api = ApiCreate.create(Api::class.java).login()
                 }
                 onSuccess {
                     Log.e(TAG, "result = ${it}")
                 }
                 onFail { msg, _ ->
                     Log.e(TAG, "onFailed = $msg")
                 }
             }
         }*/
        retrofit2<SentenceOneDay>(this){
            ioScope {
                result = ApiCreate.create<API>().aSentenceOneDay()
                onSuccess {
                    tv_content.text = it.content
                    tv_note.text = it.note
                    Log.i(TAG, "result = ${it}")
                }
                onTimeOut{
                    Log.i(TAG, "onTimeOut ")
                    showToast("超时连接")
                }
                onFail { msg ->
                    Log.i(TAG, "onFailed = $msg")
                }
                onComplete {
                    Log.i(TAG, "onComplete ")
                }
            }

        }
//        uiScope(this) {
//            val result = ApiCreate.create<SentenceOneDay>()
//            result.let {
//                uiScope {
//                    tv_content.text = it.content
//                    tv_note.text = it.note
//                    //  GlideUtil.loadDefult(it.picture,iv_picture)
//                }
//            }
//        }
    }

}
