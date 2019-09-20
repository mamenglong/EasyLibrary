# EasyDialog
>封装的dialog
[![](https://jitpack.io/v/mamenglong/EasyLibrary.svg)](https://jitpack.io/#mamenglong/EasyLibrary)- [README](README.md)
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
                     implementation 'com.github.mamenglong.EasyLibrary:easydialog:Tag'
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
              <artifactId>easydialog</artifactId>
              <version>Tag</version>
          </dependency>
          ```
- 介绍 
    
* 使用方式
    ```kotlin
    EasyDialog.init<CustomDialog>(EasyDialog.DialogType.CUSTOM)
              .setLayoutRes(R.layout.dialog_easy_sample)
              .setOnDismissCallback {  showToast("onDismissCallback") }
              .convert { it-> }
              .show(fragmentManager = supportFragmentManager)
    ```