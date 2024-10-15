package com.eiviayw.drawingsupport.activity

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.eiviayw.drawingsupport.R
import com.eiviayw.drawingsupport.databinding.DialogBuildParamBinding
import com.eiviayw.drawingsupport.databinding.DialogSettingBinding
import com.eiviayw.library.bean.param.BaseParam

class ParamBuildDialog(
    private val param:BaseParam,
    private val callBack:(BaseParam) -> Unit
):DialogFragment() {

    private lateinit var mContext: Context
    private val viewBinding by lazy { DialogBuildParamBinding.inflate(layoutInflater) }

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

    private fun initWindow(){
        val attributes = dialog?.window?.attributes
        attributes?.width = 400
        attributes?.height = 200
        attributes?.gravity = Gravity.CENTER
        dialog?.window?.attributes = attributes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialog)
        mContext = activity as Context
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(true)
    }

    private fun initEven(){

    }

    private fun initData(){

    }
}