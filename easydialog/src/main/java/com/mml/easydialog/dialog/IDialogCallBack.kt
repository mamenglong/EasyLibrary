package com.mml.easydialog.dialog

import android.app.Activity


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-9 下午5:03
 * Description: This is IDialogCancelCallBack
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */

interface IDialogCancelCallBack<T> {
    fun onCancel(onCancelCallback: () -> Unit):T
}
interface IDialogDismissCallBack<T>  {
    fun onDismiss(onDismissCallback: () -> Unit):T
}
interface IDialogConfirmCallBack<T>  {
    fun onConfirm(onConfirmCallback: () -> Unit):T
}

interface IDialogCallBack<T>: IDialogCancelCallBack<T>, IDialogDismissCallBack<T>,
    IDialogConfirmCallBack<T>



interface IBaseSimpleDialogConfig<T>{
    fun isCancelable(isCan:Boolean):T
    fun setTAG(tag:String):T
    fun setActivity(activity: Activity):T
    fun setMessage(msg: String):T
    fun setOnCancelClickCallback(cancelCallback: () -> Unit):T
    fun setOnDismissCallback(dismissCallback: () -> Unit):T
    fun setTitle(title: String):T
    fun setCancelText(value:String):T
    fun setConfirmText(value:String):T
}
interface ISimpleDialogConfig<T>: IBaseSimpleDialogConfig<T> {
    fun setOnConfirmClickCallback(confirmCallback: () -> Unit):T
}
interface ISimpleEditTextDialogConfig<T>: IBaseSimpleDialogConfig<T> {
    fun setOnConfirmClickCallback(confirmCallback: (value:String) -> Unit):T

}
interface ISimpleListDialogConfig<T>: IBaseSimpleDialogConfig<T> {
    fun setOnConfirmClickCallback(confirmCallback: (value:String) -> Unit):T
    fun setItems(items:ArrayList<Any>):T
}
interface ISimpleSingleChoiceDialogConfig<T>: IBaseSimpleDialogConfig<T> {
    fun setOnConfirmClickCallback(confirmCallback: (value:Any,pos:Int) -> Unit):T
    fun setDefaultSelect(default:Int):T
    fun setItems(items:ArrayList<Any>):T
}
interface ISimpleMultiChoiceDialogConfig<T>: IBaseSimpleDialogConfig<T> {
    fun setOnConfirmClickCallback(confirmCallback: (values:ArrayList<Any>,poss:ArrayList<Int>) -> Unit):T
    fun setDefaultSelect(default:MutableList<Int>):T
    fun setItems(items:ArrayList<Any>):T
}
