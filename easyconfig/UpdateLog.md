# AndroidConfig
>使用kotlin基于委托封装SharePreference
 
 * 19-8-12 上午10:26
  + 新增加密算法,暂时仅支持对象加密,使用方式为:
    ```kotlin
    //继承 com.mml.easyconfig.config.Config ,实现属性spName的赋值.为空则为默认sharePreference对象
      object Config: Config() {
      //是否启用加密
       override val isEncode: Boolean
        get() = true
        override val spName: String
            get() = "NIHAO"
        //定义属性 
        var name by string(key = ConfigType.STRING)
        var users by json<User?>(null)
    }
     //使用
             Config.name="nihao"
            test.text=Config.name
    ```
   + 提供查看模块是否初始化
   
       ```kotlin
          AndroidConfig.isInitialized
       ```