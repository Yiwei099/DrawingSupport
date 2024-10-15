package com.eiviayw.drawingsupport.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eiviayw.drawingsupport.databinding.ActivityStartBinding
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.draw.BitmapOption

class StartActivity:AppCompatActivity() {

    private val viewBinding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private val bitmapOption by lazy { BitmapOption() }
    private val params by lazy { mutableListOf<BaseParam>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.apply {
            btOption.setOnClickListener{
                val dialog = SettingDialog(bitmapOption){

                }
                dialog.showNow(supportFragmentManager,null)
            }
            btDraw.setOnClickListener{

            }
            btAdd.setOnClickListener{
                val dialog = ParamTypeChoseDialog()
                dialog.showNow(supportFragmentManager,null)
            }
        }
    }
}