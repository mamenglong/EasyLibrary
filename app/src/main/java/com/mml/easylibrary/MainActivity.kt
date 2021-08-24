package com.mml.easylibrary

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mml.easyconfig.AndroidConfig
import com.mml.easyconfig.config.SharedPreferenceDelegate
import com.mml.easydialog.dialog.*
import com.mml.easylibrary.coroutines.CoroutinesActivity
import com.mml.easylibrary.databinding.ActivityMainBinding
import java.security.Permission

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var aa by SharedPreferenceDelegate(spName = "test",default = 0)
    var ss by SharedPreferenceDelegate.json<User?>(null,"asad")

    private val map = mutableMapOf<String,Class<*>>(
        Pair("CoroutinesActivity", CoroutinesActivity::class.java),
        Pair("存储", StorageActivity::class.java)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidConfig.initialize(this)
        initView()
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),0)
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
            binding.linear.addView(button)
        }
        binding.easyDialog.setOnClickListener {
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
        binding.simpleDialog.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleEditTextDialog>(EasyDialog.DialogType.SIMPLEEDITTEXT).init(this)

                .show()
        }
        val item= arrayListOf<Any>(false,"2",3,"4","5")
        binding.simpleDialogList.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleListDialog>(EasyDialog.DialogType.LIST).init(this)
                .setItems(item)
                .setOnConfirmClickCallback {
                    showToast(it)
                }
                .show()
        }
        binding.simpleDialogSingle.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleSingleChoiceDialog>(EasyDialog.DialogType.SINGLECHOICE).init(this)
                .setItems(item)
                .setDefaultSelect(3)
                .setOnConfirmClickCallback {v,p->
                    showToast(v.toString())
                }
                .show()
        }
        binding.simpleDialogMulti.setOnClickListener {
            val siampleDialog=EasyDialog.init<SimpleMultiChoiceDialog>(EasyDialog.DialogType.MULTICHOICE).init(this)
                .setItems(item)
                .show()
        }
        Config.name="nihao"
        binding.test.text=Config.name
        Config.users=User("4444")
        aa=9
        binding.test.text=binding.test.text.toString()+Config.users.toString()
        ss=User("34rr")
        binding.test.text=binding.test.text.toString()+ss.toString()
    }
}
