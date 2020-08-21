package com.mml.easylibrary

import android.os.Environment
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.ml.custom.scopedstorage.FileAccessFactory
import com.ml.custom.scopedstorage.FileRequest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mml.easylibrary", appContext.packageName)
    }

    @Test
    fun fileSave(){
        val fileRequest= FileRequest(File("hahha"))
        fileRequest.displayName="nihao"
        fileRequest.dirType = Environment.DIRECTORY_DOWNLOADS
        val fileAccessFactory  = FileAccessFactory.getFile()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        fileAccessFactory.createFile(appContext,fileRequest){
            onScopedSuccess { uri, file ->
                val text = "hahahhahahha"
                appContext.contentResolver.openOutputStream(uri!!)?.use {
                    it.bufferedWriter().write(text)
                }
            }
            onFailure { 
               println(it.toString())
            }
        }
    }
}
