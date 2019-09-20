package com.mml.easydialog.dialog

import android.app.Activity
import android.app.AlertDialog
import java.lang.NullPointerException

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-9 上午10:08
 * Description: This is SimpleDialog
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */
class SimpleDialog: ISimpleDialogConfig<SimpleDialog> {
    private val simpleDialogConfig= SimpleDialogConfig()
    fun init(activity: Activity): SimpleDialog {
        simpleDialogConfig.activity=activity
        return this
    }
    /**
     * @Author: Menglong Ma
     * @Email: mml2015@126.com
     * @Date: 19-9-9 下午6:05
     * @Description: 所以参数设置以后调用
     */
    fun show() {
        with(simpleDialogConfig) {
            activity?:throw NullPointerException("parameter activity is null ,please call init(activity: Activity) before show()")
           val dialog= AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirmText) { _, _ ->
                    onConfirmClickCallback.invoke()
                }
                .setNegativeButton(cancelText) { _, _ ->
                    onCancelClickCallback.invoke()
                }
                .setCancelable(cancelable)
                .create()
            dialog.setOnDismissListener { onDismissCallback.invoke() }
            dialog.show()
        }
    }
    override fun isCancelable(isCan: Boolean): SimpleDialog {
        simpleDialogConfig.cancelable=isCan
        return this
    }

    override fun setTAG(tag: String): SimpleDialog {
        simpleDialogConfig.TAG=tag
        return this
    }

    override fun setActivity(activity: Activity): SimpleDialog {
        simpleDialogConfig.activity=activity
        return this
    }

    override fun setMessage(msg: String): SimpleDialog {
        simpleDialogConfig.message=msg
        return this
    }

    override fun setOnCancelClickCallback(cancelCallback: () -> Unit): SimpleDialog {
        simpleDialogConfig.onCancelClickCallback=cancelCallback
        return this
    }

    override fun setOnConfirmClickCallback(confirmCallback: () -> Unit): SimpleDialog {
        simpleDialogConfig.onConfirmClickCallback=confirmCallback
        return this
    }

    override fun setOnDismissCallback(dismissCallback: () -> Unit): SimpleDialog {
        simpleDialogConfig.onDismissCallback=dismissCallback
        return this
    }

    override fun setTitle(title: String): SimpleDialog {
        simpleDialogConfig.title=title
        return this
    }

    override fun setCancelText(value: String): SimpleDialog {
        simpleDialogConfig.cancelText=value
        return this
    }

    override fun setConfirmText(value: String): SimpleDialog {
        simpleDialogConfig.confirmText=value
        return this
    }
}