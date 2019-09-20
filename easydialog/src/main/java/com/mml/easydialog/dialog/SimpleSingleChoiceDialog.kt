package com.mml.easydialog.dialog

import android.app.Activity
import android.app.AlertDialog
import android.widget.ArrayAdapter
import com.mml.core.log
import com.mml.easydialog.R

import java.lang.NullPointerException



/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-9 上午10:08
 * Description: This is SimpleDialog
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */
class SimpleSingleChoiceDialog: ISimpleSingleChoiceDialogConfig<SimpleSingleChoiceDialog> {



    private val simpleDialogConfig= SimpleSingleChoiceDialogConfig()
    fun init(activity: Activity): SimpleSingleChoiceDialog {
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
        val adapter= ArrayAdapter<Any>(simpleDialogConfig.activity!!, R.layout.support_simple_spinner_dropdown_item,simpleDialogConfig.items!!)
        with(simpleDialogConfig) {
            val array= arrayOfNulls<CharSequence>(items.size)
          items.forEachIndexed { index, any ->
              array[index]=any.toString()
          }  
            activity?:throw NullPointerException("parameter activity is null ,please call init(activity: Activity) before show()")
           val dialog= AlertDialog.Builder(activity)
                .setTitle(title)
               .setSingleChoiceItems( array,defaultSelect){ _, pos->
                   mSelectItemPosition=pos
                   mSelectItemValue= items[pos]
                   log(tag="SimpleSingleChoiceDialog  setSingleChoiceItems",msg ="${items[pos]}")
               }
               .setPositiveButton(confirmText) { _, _ ->
                   log(tag="SimpleSingleChoiceDialog  onConfirmClickCallback",msg ="$mSelectItemValue $mSelectItemPosition")
                    onConfirmClickCallback.invoke(mSelectItemValue!!,mSelectItemPosition)
                }
                .setNegativeButton(cancelText) { _, _ ->
                    onCancelClickCallback.invoke()
                }
                .setCancelable(cancelable)
                .create()
            dialog.setOnDismissListener { onDismissCallback.invoke() }
            dialog.show()

            val m = activity!!.windowManager
            val d = m.defaultDisplay  //为获取屏幕宽、高
            val p = dialog.window!!.attributes  //获取对话框当前的参数值
            if (p.height>(d.height * 0.5).toInt()) {
                p.height = (d.height * 0.5).toInt()   //高度设置为屏幕的0.3
                p.width = (d.width * 0.8).toInt()    //宽度设置为屏幕的0.5
                dialog.window!!.attributes = p     //设置生效
            }
        }
    }
    override fun setDefaultSelect(default: Int): SimpleSingleChoiceDialog {
        simpleDialogConfig.defaultSelect=default
        return this
    }
    override fun isCancelable(isCan: Boolean): SimpleSingleChoiceDialog {
        simpleDialogConfig.cancelable=isCan
        return this
    }

    override fun setTAG(tag: String): SimpleSingleChoiceDialog {
        simpleDialogConfig.TAG=tag
        return this
    }

    override fun setActivity(activity: Activity): SimpleSingleChoiceDialog {
        simpleDialogConfig.activity=activity
        return this
    }

    override fun setMessage(msg: String): SimpleSingleChoiceDialog {
        simpleDialogConfig.message=msg
        return this
    }

    override fun setOnCancelClickCallback(cancelCallback: () -> Unit): SimpleSingleChoiceDialog {
        simpleDialogConfig.onCancelClickCallback=cancelCallback
        return this
    }
    override fun setOnConfirmClickCallback(confirmCallback: (value:Any,pos:Int) -> Unit): SimpleSingleChoiceDialog {
        simpleDialogConfig.onConfirmClickCallback=confirmCallback
        return this
    }
    override fun setOnDismissCallback(dismissCallback: () -> Unit): SimpleSingleChoiceDialog {
        simpleDialogConfig.onDismissCallback=dismissCallback
        return this
    }

    override fun setTitle(title: String): SimpleSingleChoiceDialog {
        simpleDialogConfig.title=title
        return this
    }

    override fun setCancelText(value: String): SimpleSingleChoiceDialog {
        simpleDialogConfig.cancelText=value
        return this
    }

    override fun setConfirmText(value: String): SimpleSingleChoiceDialog {
        simpleDialogConfig.confirmText=value
        return this
    }

    override fun setItems(items: ArrayList<Any>): SimpleSingleChoiceDialog {
         simpleDialogConfig.items=items
        return this
    }
}