package com.mml.easyconfig.config

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @projectName：AndroidConfig
 * @author: Created by Menglong Ma
 * @email：mml2015@126.com
 * @date: 2019/8/4 19:16.
 * @description:
 */
inline fun <reified T> SharedPreferences.json(defaultValue: T, key: String? = null) =
    object : ReadWriteProperty<Any, T> {
        private val gson = Gson()

        override fun getValue(thisRef: Any, property: KProperty<*>): T {

            val s = getString(key ?: property.name, "")

            return if (s!!.isBlank()) defaultValue else gson.fromJson(s, T::class.java)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T)  =
            edit().putString(key ?: property.name, gson.toJson(value)).apply()
    }
inline fun <reified T> SharedPreferences.gsonList(key: String? = null) =
    object : ReadWriteProperty<Any, List<T>> {

        private val gson = Gson()

        override fun getValue(thisRef: Any, property: KProperty<*>): List<T> {

            val s = getString(key ?: property.name, "")

            return if (s!!.isBlank()) emptyList() else gson.fromJson<List<T>>(s, object : TypeToken<List<T>>() {}.type)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: List<T>) =
            edit().putString(key ?: property.name, gson.toJson(value)).apply()
    }