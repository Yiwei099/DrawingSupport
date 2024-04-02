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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initData()
        initEven()
    }

    private fun initEven() {
        viewBinding.btRefresh.setOnClickListener {
            val width =  viewBinding.etWidth.text.toString().trim()
            val height =  viewBinding.etHeight.text.toString().trim()
            bitmapOption = BitmapOption(
                maxWidth = if (TextUtils.isEmpty(width)) 576 else width.toInt(),
                maxHeight = if (TextUtils.isEmpty(width)) 0 else height.toInt(),
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
    }

    private fun initData() {
        viewBinding.apply {
            etWidth.setText(bitmapOption.maxWidth.toString())
            etHeight.setText(bitmapOption.maxHeight.toString())
            cbFollow.isChecked = bitmapOption.followEffectItem
            when (bitmapOption.gravity) {
                Constant.Companion.Gravity.BOTTOM -> rbBottom.isChecked = true
                Constant.Companion.Gravity.CENTER -> rbCenter.isChecked = true
                else -> rbTop.isChecked = true
            }
        }
    }


}