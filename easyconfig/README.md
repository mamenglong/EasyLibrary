# AndroidConfig
>使用kotlin基于委托封装SharePreference
[![](https://jitpack.io/v/mamenglong/EasyLibrary.svg)](https://jitpack.io/#mamenglong/EasyLibrary)
- [README](README.md)
- [更新日志](UPDATE_LOG.md)
- 使用
  - To get a Git project into your build:
    - Gradle
      -   Step 1. Add the JitPack repository to your build file Add it
          in your root build.gradle at the end of repositories:
           ```
             allprojects {
                 repositories {
                     ...
                     maven { url 'https://jitpack.io' }
                 }
             }
           ``` 
        - Step 2. Add the dependency
            ```
                dependencies {
                   implementation 'com.github.mamenglong.EasyLibrary:easyconfig:Tag'
                }
            ```    
    - maven
      - Step 1. Add the JitPack repository to your build file 
          ```
          <repositories>
               <repository>
                   <id>jitpack.io</id>
                   <url>https://jitpack.io</url>
               </repository>
           </repositories>
          ```
      -  Step 2. Add the dependency 
          ``` 
          <dependency>
              <groupId>com.github.mamenglong.EasyLibrary</groupId>
              <artifactId>easyconfig</artifactId>
              <version>Tag</version>
          </dependency>
          ```

- 初始化 
  - Configure UtilsApplication 
    - You don't want to pass the Context param all the time. To makes
      the APIs simple, just configure the ConfigApplication in
      AndroidManifest.xml as below:
        ```
        <manifest>
            <application
                android:name="com.mml.android.ConfigApplication"
                ...
            >
                ...
            </application>
        </manifest>
        ```
    - Of course you may have your own Application and has already
      configured here, like:
        ``` 
            <manifest>
             <application
            android:name="com.example.MyOwnApplication" ... > 
            ... 
            </application>
            </manifest> 
        ``` 
>That's OK. Utils can still live with that. Just call
EasyUtils.initialize(context) in your own Application: 
    
  ```java
     public class MyOwnApplication extends Application {
             
                 @Override
                 public void onCreate() {
                     super.onCreate();
                     AndroidConfig.initialize(this);
                 }
             }
  ```
  Make sure to call this method as early as you can. In the
 onCreate() method of Application will be fine. And always remember to
 use the application context as parameter. Do not use any instance of
 activity or service as parameter, or memory leaks might happen.

- 介绍 
  基于kotlin使用委托方式使用sharePreference  
* 使用方式
 + 第一种，继承方式
    ```kotlin
    //继承 com.mml.easyconfig.config.Config ,实现属性spName的赋值.为空则为默认sharePreference对象
      object Config: Config() {
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
 + 第二种，直接使用
     ```kotlin
     //定义
      var aa by SharedPreferenceDelegate(spName = "test",default = 0)
      var ss by SharedPreferenceDelegate.json<User?>(null,"asad")
     //使用
     aa="sss"
     ss=User("name")
     ```