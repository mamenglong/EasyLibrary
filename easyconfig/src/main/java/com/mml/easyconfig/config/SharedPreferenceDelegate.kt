package com.mml.easyconfig.config

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mml.easyconfig.ConfigApplication
import java.io.*
import java.lang.Exception
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-7-30 下午6:26
 * Description: This is Test
 * Package: com.mml.androidconfig.config
 * Project: AndroidConfig
 */
class SharedPreferenceDelegate<T>(var spName: String? = "",var default:T) : ReadWriteProperty<Any?, T> {
     val prefs: SharedPreferences  by lazy {
        if (spName!!.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(ConfigApplication.sContext)
        } else {
            ConfigApplication.sContext!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
        }
    }
    companion object {
        inline fun <reified T> json(defaultValue: T, spName: String? = "",key: String? = null) =
            object : ReadWriteProperty<Any, T> {
                val prefs: SharedPreferences  by lazy {
                    if (spName!!.isEmpty()) {
                        PreferenceManager.getDefaultSharedPreferences(ConfigApplication.sContext)
                    } else {
                        ConfigApplication.sContext!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
                    }
                }
                private val gson = Gson()

                override fun getValue(thisRef: Any, property: KProperty<*>): T {

                    val s = prefs.getString(key ?: property.name, "")

                    return if (s!!.isBlank()) defaultValue else gson.fromJson(s, T::class.java)
                }

                override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
                    prefs.edit().putString(key ?: property.name, gson.toJson(value)).apply()
            }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return with(prefs) {
            val res: Any = when (default) {
                is Int -> getInt(property.name, default as Int)
                is Boolean -> getBoolean(property.name, default as Boolean)
                is Float ->getFloat(property.name,default as Float)
                is String -> getString(property.name,default as String)!!
                is Double->getString(property.name,default as String)!!.toDouble()
                is Long->getLong(property.name,default as Long)
                is Set<*>->getStringSet(property.name, emptySet()) as Set<*>
                else -> throw Exception("")
            }
            res as T
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Int -> putInt(property.name, value)
                is String -> putString(property.name, value)
                is Boolean -> putBoolean(property.name, value)
                is Float -> putFloat(property.name, value)
                is Long->putLong(property.name,value)
                is Double ->putString(property.name,value.toString())
                is Set<*>->putStringSet(property.name,value as Set<String>)
                else -> throw Exception()
            }?.apply()
        }
    }

    /**
     * 删除全部数据
     */
    fun clearPreference() {
        prefs.edit().clear().apply()
    }

    /**
     * 根据key删除存储数据
     */
    fun clearPreference(key: String) {
        prefs.edit().remove(key).apply()
    }


}