# EasyLogger
> 日志记录,打印控制台,输出文件
+ 文件路径默认为 android/data/包名/files/Logs 目录下

## 使用

```kotlin
//配置
       Logger.debug(BuildConfig.DEBUG)
            .enableLog(true)
            .logDir(getExternalFilesDir("Logs")!!.path)
            .logSize(1*1024*1024L)
            .saveToDisk(true,this)
//使用
        Logger.i("onCreate")
     
```
> debug 或者 enableLog一个为true则输出控制台,logDir会被saveToDisk默认路径覆盖(默认路径为 Android/data/包名/files/Logs)
logSize为单独一个文件的大小,文件名根据日期命名