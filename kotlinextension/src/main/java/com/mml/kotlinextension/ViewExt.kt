package com.mml.kotlinextension

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.SystemClock
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Checkable
import android.widget.TextView
import androidx.annotation.Dimension

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

// 扩展点击事件属性(重复点击时长)
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0
// 重复点击事件绑定
inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

/**
 * 多次点击事件
 */
fun View.multiClickListener(times:Int,action:()->Unit){
    val mHints = LongArray(times) //初始全部为0
    setOnClickListener {
        //将mHints数组内的所有元素左移一个位置
        System.arraycopy(mHints, 1, mHints, 0, mHints.size - 1)
        //获得当前系统已经启动的时间
        mHints[mHints.size - 1] = SystemClock.uptimeMillis()
        if (mHints[0] >= (mHints[mHints.size - 1] - 800)) {
            action()
        }
    }
}



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

fun View.dpToPx(
    @Dimension(unit = Dimension.DP) dp: Float
): Float {
    val r = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        r.displayMetrics
    )
}
/**
 * 扩展属性 dp转px
 */
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
/**
 * 扩展属性 像素
 */
val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this,
        Resources.getSystem().displayMetrics
    )
/**
 * 扩展属性 sp转px
 */
val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )
/**
 * 手动测量摆放View
 * 对于手动 inflate 或者其他方式代码生成加载的View进行测量，避免该View无尺寸
 * PS: 在填充数据以后调用，否则数据无法正常显示
 */
fun layoutView(v: View, width: Int, height: Int) {
    // validate view.width and view.height
    v.layout(0, 0, width, height)
    val measuredWidth =
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
    val measuredHeight =
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

    // validate view.measurewidth and view.measureheight
    v.measure(measuredWidth, measuredHeight)
    v.layout(0, 0, v.measuredWidth, v.measuredHeight)
}