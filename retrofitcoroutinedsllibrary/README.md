# EasyDialog
>封装的dialog
[![](https://jitpack.io/v/mamenglong/EasyLibrary.svg)](https://jitpack.io/#mamenglong/EasyLibrary)- [README](README.md)
    
* 使用方式

+ 新版,带生命周期观察者
     + activity中使用
        ```kotlin
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

        ```
<!--旧版-->
+ 旧版
    + 编写BaseActivity,实现接口CoroutineScope，这样在页面中的上下文就是协程下上文
        ```kotlin
          open class BaseActivity : AppCompatActivity(), CoroutineScope {
        
            private lateinit var job: Job
        
            override val coroutineContext: CoroutineContext
                get() = Dispatchers.Main + job
        
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                job = Job()
            }
        
            override fun onDestroy() {
                super.onDestroy()
                // 关闭页面后，结束所有协程任务
                job.cancel() 
            }
        }
        ```
    + 然后便可在activity中使用
        ```kotlin
            retrofit<Any> {
                lanch{
                api = RetrofitCreater.create(Api::class.java).login()
               }
                onSuccess {
                    Log.e(TAG, "result = ${it?.avatar}")
                }
                onFail { msg, _ ->
                    Log.e(TAG, "onFailed = $msg")
                }
            }
        ```
    
 
