package com.eiviayw.drawingsupport.activity

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.DialogFragment
import com.eiviayw.drawingsupport.OnRadioGroupChildCheckedCallBack
import com.eiviayw.drawingsupport.R
import com.eiviayw.drawingsupport.databinding.DialogSettingBinding
import com.eiviayw.library.Constant
import com.eiviayw.library.draw.BitmapOption

class SettingDialog(
    private val bitmapOption: BitmapOption,
    private val callBack: (BitmapOption) -> Unit
) : DialogFragment() {

    private lateinit var mContext: Context
    private val viewBinding by lazy { DialogSettingBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWindow()
        initEven()
        initData()
    }

    private fun initWindow() {
        val attributes = dialog?.window?.attributes
        attributes?.width = 1400
        attributes?.height = 200
        attributes?.gravity = Gravity.CENTER
        dialog?.window?.attributes = attributes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialog)
        mContext = activity as Context
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    private fun initData() {
        viewBinding.apply {
            cbFollowEffect.isChecked = bitmapOption.followEffectItem
            cbAntAlias.isChecked = bitmapOption.antiAlias
            when (bitmapOption.gravity) {
                Constant.Companion.Gravity.BOTTOM -> rbBottom.isChecked = true
                Constant.Companion.Gravity.CENTER -> rbCenter.isChecked = true
                else -> rbTop.isChecked = true
            }
            etWidth.setText(bitmapOption.maxWidth.toString())
            etHeight.setText(bitmapOption.maxHeight.toString())

            sbIndentation.progress = bitmapOption.startIndentation.toInt()
            sbTopIndentation.progress = bitmapOption.topIndentation.toInt()

            tvIndentationValue.text = bitmapOption.startIndentation.toString()
            tvTopIndentationValue.text = bitmapOption.topIndentation.toString()
        }
    }

    private fun initEven() {
        viewBinding.apply {
            btCancel.setOnClickListener { dismiss() }
            btConfirm.setOnClickListener {
                val newWidth = etWidth.text.toString().trim()
                val newHeight = etHeight.text.toString().trim()
                bitmapOption.maxWidth = if (!TextUtils.isEmpty(newWidth)) newWidth.toInt() else 576
                bitmapOption.maxHeight = if (!TextUtils.isEmpty(newHeight)) newHeight.toInt() else 0
                callBack.invoke(bitmapOption)
            }
            cbFollowEffect.setOnCheckedChangeListener { _, isChecked ->
                bitmapOption.followEffectItem = isChecked
            }
            //抗锯齿
            cbAntAlias.setOnCheckedChangeListener { _, isChecked ->
                bitmapOption.antiAlias = isChecked
            }
            //左右边距
            sbIndentation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    tvIndentationValue.text = progress.toString()
                    bitmapOption.startIndentation = progress.toFloat()
                    bitmapOption.endIndentation = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
            //顶部边距
            sbTopIndentation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    tvTopIndentationValue.text = progress.toString()
                    bitmapOption.topIndentation = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
            //内容对齐方式
            rgGravity.setOnCheckedChangeListener(object :
                OnRadioGroupChildCheckedCallBack(rgGravity) {
                override fun onCheckedCallBack(radioGroup: RadioGroup, viewID: Int) {
                    when (viewID) {
                        R.id.rbCenter -> bitmapOption.gravity = Constant.Companion.Gravity.CENTER
                        R.id.rbBottom -> bitmapOption.gravity = Constant.Companion.Gravity.BOTTOM
                        else -> bitmapOption.gravity = Constant.Companion.Gravity.TOP
                    }
                }
            })

        }
    }
}