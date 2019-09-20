package com.mml.easydialog.dialog

import android.app.Activity
import com.mml.core.showDebugToast


/**
 * @Author: Menglong Ma
 * @Email: mml2015@126.com
 * @Date: 19-8-29 下午4:19
 * @Description: This is CustomDialogConfig
 * @Package: com.mml.onceapplication.dialog
 * @see [百分比宽高和宽高只有一个生效,如果同时设置则使用宽高,默认为百分比宽高,宽高优先级较高,设置宽高时请使用px像素而不是dp]
 * @Project: OnceApplication
 */

open class DialogConfig{
        var activity: Activity?=null
        var message: String=""
        var onCancelClickCallback: () -> Unit = { showDebugToast("onCancelClickCallback") }
        var onDismissCallback:()->Unit={ showDebugToast("onDismissCallback") }
        var cancelable: Boolean = true
        var title: String = "标题"
        var cancelText: String = "取消"
        var confirmText: String = "确认"
}
data class SimpleDialogConfig(
         var TAG: String = "SimpleDialogConfig",
         var onConfirmClickCallback: () -> Unit = { showDebugToast("onConfirmClickCallback") }
) : DialogConfig()
data class SimpleEditTextDialogConfig(
         var TAG: String = "SimpleEditTextDialogConfig",
         var onConfirmClickCallback: (value:String) -> Unit = { _-> showDebugToast("onConfirmClickCallback") }
        ) : DialogConfig()

data class SimpleListDialogConfig(
        var TAG: String = "SimpleListDialogConfig",
        var items:ArrayList<Any>?=null,
        var onConfirmClickCallback: (value:String) -> Unit = { _-> showDebugToast("onConfirmClickCallback") }
) : DialogConfig()
data class SimpleSingleChoiceDialogConfig(
        var TAG: String = "SimpleSingleChoiceDialogConfig",
        var items:ArrayList<Any> = ArrayList(),
        var defaultSelect:Int=-1,
        var mSelectItemValue:Any? = null,
        var mSelectItemPosition: Int=-1,
        var onConfirmClickCallback: (value:Any,pos:Int) -> Unit = { _,_-> showDebugToast("onConfirmClickCallback") }
) : DialogConfig()
data class SimpleMultiChoiceDialogConfig(
        var TAG: String = "SimpleMultiChoiceDialogConfig",
        var items:MutableList<Any> = mutableListOf(),
        var defaultSelects:MutableList<Int> = mutableListOf(),
        var mSelectItemValues:ArrayList<Any> = ArrayList(),
        var mSelectItemPositions: ArrayList<Int> = ArrayList(),
        var onConfirmClickCallback: (values:ArrayList<Any>,poss:ArrayList<Int>) -> Unit = { _,_-> showDebugToast("onConfirmClickCallback") }
) : DialogConfig()