package com.mml.easylibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mml.easyconfig.AndroidConfig
import com.mml.easyconfig.config.SharedPreferenceDelegate
import com.mml.easydialog.dialog.*
import com.mml.easylibrary.coroutines.CoroutinesActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var aa by SharedPreferenceDelegate(spName = "test",default = 0)
    var ss by SharedPreferenceDelegate.json<User?>(null,"asad")

    private val map = mutableMapOf<String,Class<*>>(
        Pair("CoroutinesActivity", CoroutinesActivity::class.java)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidConfig.initialize(this)
        initView()
    }

    private fun initView() {
        map.forEach {
            val button: Button = Button(this)
            button.text=it.key
            val cls=it.value
            button.setOnClickListener {
                val intent = Intent(this@MainActivity,cls)
                startActivity(intent)
            }
            linear.addView(button)
        }
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
        Config.name="nihao"
        test.text=Config.name
        Config.users=User("4444")
        aa=9
        test.text=test.text.toString()+Config.users.toString()
        ss=User("34rr")
        test.text=test.text.toString()+ss.toString()
    }
}
