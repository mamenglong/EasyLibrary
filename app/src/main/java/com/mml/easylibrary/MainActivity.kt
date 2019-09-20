package com.mml.easylibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mml.easydialog.dialog.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        easy_dialog.setOnClickListener {
            /*            val dialog= CustomDialog()
                            .setLayoutRes(R.layout.dialog_easy_sample)
                            .setOnDismissCallback {  showToast("onDismissCallback") }
                            .convert { it-> }
                            .show(fragmentManager = supportFragmentManager)*/


            val sss= EasyDialog.init<CustomDialog>(EasyDialog.DialogType.CUSTOM)
                .setLayoutRes(R.layout.dialog_easy_sample)
                .setOnDismissCallback {  showToast("onDismissCallback") }
                .convert { it-> }
                .show(fragmentManager = supportFragmentManager)
        }
        simple_dialog.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleEditTextDialog>(EasyDialog.DialogType.SIMPLEEDITTEXT).init(this)

                .show()
        }
        val item= arrayListOf<Any>(false,"2",3,"4","5")
        simple_dialog_list.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleListDialog>(EasyDialog.DialogType.LIST).init(this)
                .setItems(item)
                .setOnConfirmClickCallback {
                    showToast(it)
                }
                .show()
        }
        simple_dialog_single.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleSingleChoiceDialog>(EasyDialog.DialogType.SINGLECHOICE).init(this)
                .setItems(item)
                .setDefaultSelect(3)
                .setOnConfirmClickCallback {v,p->
                    showToast(v.toString())
                }
                .show()
        }
        simple_dialog_multi.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleMultiChoiceDialog>(EasyDialog.DialogType.MULTICHOICE).init(this)
                .setItems(item)
                .show()
        }
    }
}
