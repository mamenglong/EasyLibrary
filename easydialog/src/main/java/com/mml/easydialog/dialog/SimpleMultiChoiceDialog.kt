package com.mml.easydialog.dialog

import android.app.Activity
import android.app.AlertDialog
import android.widget.ArrayAdapter
import com.mml.core.log
import com.mml.easydialog.R


import kotlin.collections.ArrayList


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-9 上午10:08
 * Description: This is SimpleDialog
 * Package: com.mml.onceapplication.dialog
 * Project: OnceApplication
 */
class SimpleMultiChoiceDialog: ISimpleMultiChoiceDialogConfig<SimpleMultiChoiceDialog> {


    /**
     * 该 int 的每一位标识菜单的每一项是否被选中 (1为选中,0位不选中)
     */
    private var mCheckedItems: Int = 0
    private val simpleDialogConfig= SimpleMultiChoiceDialogConfig()
    fun init(activity: Activity): SimpleMultiChoiceDialog {
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
        val adapter= ArrayAdapter<Any>(simpleDialogConfig.activity!!,
            R.layout.support_simple_spinner_dropdown_item,simpleDialogConfig.items!!)
        with(simpleDialogConfig) {
            val array= arrayOfNulls<CharSequence>(items.size)
            items.forEachIndexed { index, any ->
                array[index]=any.toString()
            }
            val booleanArray=BooleanArray(items.size)
            defaultSelects.forEachIndexed { index, i ->
                booleanArray[i]=true
            }
            activity?:throw NullPointerException("parameter activity is null ,please call init(activity: Activity) before show()")
           val dialog= AlertDialog.Builder(activity)
                .setTitle(title)
               .setMultiChoiceItems(array, booleanArray) { _, pos, choose->
                   mCheckedItems = if (choose){
                       mCheckedItems+ (2 shl pos)
                   }else{
                       mCheckedItems- (2 shl pos)
                   }
                   log(tag="SimpleMultiChoiceDialog  setMultiChoiceItems",msg ="$mCheckedItems")
               }
               .setPositiveButton(confirmText) { _, _ ->
                    booleanArray.forEachIndexed { index, b ->
                        if (b) {
                            mSelectItemPositions.add(index)
                            mSelectItemValues.add(items[index])
                        } else{
                            mSelectItemPositions.remove(index)
                            mSelectItemValues.remove(items[index])
                        }
                    }
                   log(tag="SimpleMultiChoiceDialog  setMultiChoiceItems",msg ="$mSelectItemValues")
                    onConfirmClickCallback.invoke(mSelectItemValues,mSelectItemPositions)
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

    /**
     * @return 被选中的菜单项的下标数组。如果选中的是1，3项(以0开始)，则返回[1,3]
     */
    /*
    *      for (int i = 0; i < mMenuItemViews.size(); i++) {
                QMUIDialogMenuItemView itemView = mMenuItemViews.get(i);
                int v = 2 << i;
                itemView.setChecked((v & mCheckedItems) == v);
            }*/

    override fun setDefaultSelect(default: MutableList<Int>): SimpleMultiChoiceDialog {
        simpleDialogConfig.defaultSelects=default
        return this
    }
    override fun isCancelable(isCan: Boolean): SimpleMultiChoiceDialog {
        simpleDialogConfig.cancelable=isCan
        return this
    }

    override fun setTAG(tag: String): SimpleMultiChoiceDialog {
        simpleDialogConfig.TAG=tag
        return this
    }

    override fun setActivity(activity: Activity): SimpleMultiChoiceDialog {
        simpleDialogConfig.activity=activity
        return this
    }

    override fun setMessage(msg: String): SimpleMultiChoiceDialog {
        simpleDialogConfig.message=msg
        return this
    }

    override fun setOnCancelClickCallback(cancelCallback: () -> Unit): SimpleMultiChoiceDialog {
        simpleDialogConfig.onCancelClickCallback=cancelCallback
        return this
    }

    override fun setOnConfirmClickCallback(confirmCallback: (values: ArrayList<Any>, poss: ArrayList<Int>) -> Unit): SimpleMultiChoiceDialog {
        simpleDialogConfig.onConfirmClickCallback=confirmCallback
        return this
    }

    override fun setOnDismissCallback(dismissCallback: () -> Unit): SimpleMultiChoiceDialog {
        simpleDialogConfig.onDismissCallback=dismissCallback
        return this
    }

    override fun setTitle(title: String): SimpleMultiChoiceDialog {
        simpleDialogConfig.title=title
        return this
    }

    override fun setCancelText(value: String): SimpleMultiChoiceDialog {
        simpleDialogConfig.cancelText=value
        return this
    }

    override fun setConfirmText(value: String): SimpleMultiChoiceDialog {
        simpleDialogConfig.confirmText=value
        return this
    }

    override fun setItems(items: ArrayList<Any>): SimpleMultiChoiceDialog {
         simpleDialogConfig.items=items
        return this
    }
}