package com.mml.kotlinextension

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-10-11 下午4:22
 * Description: This is ViewExt
 * Package: com.demo.kotlinextension
 * Project: EasyLibrary
 */
/**
 * @param visible 显示
 * @param gone 是否使用gone模式隐藏
 */
fun View.extSetVisibility(visible: Boolean,gone:Boolean = false) = if (visible) {
    this.visibility = View.VISIBLE
} else {
    this.visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.extGetVisibility() = when (visibility) {
    View.VISIBLE -> {
        true
    }
    else -> {
        false
    }
}

//扩展函数，view消失
fun View.gone() {
    visibility = View.GONE
}

//扩展函数，view显示
fun View.visible() {
    visibility = View.VISIBLE
}
//扩展函数，view隐藏
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * 设置颜色直接使用colors.xml中定义的颜色
 * @param resId
 */
fun TextView.setColor(resId: Int) {
    this.setTextColor(resources.getColor(resId,null))
}
/**
 * 图片传入id
 * @param resId
 */
fun View.setDrawableLeft(resId: Int) {
    when (this) {
        is Button -> {
            var drawable = this.context.resources.getDrawable(resId,null)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            this.setCompoundDrawables(drawable, null, null, null)
        }
        is TextView -> {
            var drawable = this.context.resources.getDrawable(resId,null)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            this.setCompoundDrawables(drawable, null, null, null)
        }
        else -> {
            throw Exception("this method does not support you .")
        }
    }
}
/**
 * 图片传入id
 * @param resId
 */
fun TextView.setDrawableRight(resId: Int) {
    when (this) {
        is Button -> {
            var drawable = this.context.resources.getDrawable(resId,null)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            this.setCompoundDrawables(null, null, drawable, null)
        }
        is TextView -> {
            var drawable = this.context.resources.getDrawable(resId,null)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            this.setCompoundDrawables(null, null, drawable, null)
        }
        else -> {
            throw Exception("this method does not support you .")
        }
    }

}
/**
 * 图片传入id
 * @param resId
 */
fun TextView.setDrawableTop(resId: Int) {
    var drawable = this.context.resources.getDrawable(resId,null)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, drawable, null, null)
}
/**
 * 图片传入id
 * @param resId
 */
fun TextView.setDrawableBottom(resId: Int) {
    var drawable = this.context.resources.getDrawable(resId,null)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, null, drawable)
}

/**
 * View 转 bitmap
 */
fun View.view2Bitmap(): Bitmap {
    var ret = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    var canvas = Canvas(ret)
    var bgDrawable = this.background
    bgDrawable?.draw(canvas) ?: canvas.drawColor(Color.WHITE)
    this.draw(canvas)
    return ret
}


//-----扩展属性-----

var View.bottomMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = value
    }


var View.topMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).topMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).topMargin = value
    }


var View.rightMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).rightMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).rightMargin = value
    }

var View.leftMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
    }
