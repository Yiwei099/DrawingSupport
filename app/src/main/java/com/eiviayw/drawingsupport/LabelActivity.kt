package com.eiviayw.drawingsupport

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.eiviayw.drawingsupport.databinding.ActivityLabelBinding
import com.eiviayw.drawingsupport.label.LabelProvide
import com.eiviayw.library.Constant
import com.eiviayw.library.draw.BitmapOption

class LabelActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityLabelBinding.inflate(layoutInflater) }

    private var bitmapOption = BitmapOption()

    private var width:Int = 320
    private var height:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initData()
        initEven()
        viewBinding.rb0.isChecked = true
    }

    private fun initEven() {
        viewBinding.rgLabelSize.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb4030 ->{
                    width = 40*8
                    height = 30*8
                }
                R.id.rb4060 ->{
                    width = 40*8
                    height = 60*8
                }
                R.id.rb4080 ->{
                    width = 40*8
                    height = 80*8
                }
                R.id.rb3020 ->{
                    width = 30*8
                    height = 20*8
                }
                R.id.rb6040 ->{
                    width = 60*8
                    height = 40*8
                }
                R.id.rb0 ->{
                    width = 320
                    height = 0
                }
            }
        }

        viewBinding.btRefresh.setOnClickListener {

            bitmapOption = BitmapOption(
                maxWidth = width,
                maxHeight = height,
                followEffectItem = viewBinding.cbFollow.isChecked,
                gravity = if (viewBinding.rbTop.isChecked){
                    Constant.Companion.Gravity.TOP
                } else if (viewBinding.rbCenter.isChecked) {
                    Constant.Companion.Gravity.CENTER
                }else{
                    Constant.Companion.Gravity.BOTTOM
                }
            )
            val start = LabelProvide(bitmapOption).start()
            val bitmap = BitmapFactory.decodeByteArray(
                start,
                0,
                start.size
            )
            viewBinding.ivPreview.setImageBitmap(bitmap)
        }

        viewBinding.rbGravity.setOnCheckedChangeListener { group, checkedId ->
            bitmapOption.gravity = when(checkedId){
                R.id.rbCenter -> Constant.Companion.Gravity.CENTER
                R.id.rbDistributed -> Constant.Companion.Gravity.DISTRIBUTED
                R.id.rbBottom -> Constant.Companion.Gravity.BOTTOM
                else -> Constant.Companion.Gravity.TOP
            }
        }
    }

    private fun initData() {
        viewBinding.apply {
            cbFollow.isChecked = bitmapOption.followEffectItem
            when (bitmapOption.gravity) {
                Constant.Companion.Gravity.BOTTOM -> rbBottom.isChecked = true
                Constant.Companion.Gravity.CENTER -> rbCenter.isChecked = true
                Constant.Companion.Gravity.DISTRIBUTED -> rbDistributed.isChecked = true
                else -> rbTop.isChecked = true
            }
        }
    }


}