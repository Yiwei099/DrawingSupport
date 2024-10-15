package com.eiviayw.drawingsupport.activity

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eiviayw.drawingsupport.R
import com.eiviayw.drawingsupport.bean.ParamItem
import com.eiviayw.drawingsupport.databinding.DialogParamTypeBinding

class ParamTypeChoseDialog: DialogFragment(){
    private lateinit var mContext: Context
    private val viewBinding by lazy { DialogParamTypeBinding.inflate(layoutInflater) }

    private val contentAdapter by lazy { ParamTypeAdapter(mutableListOf<ParamItem>().apply {
        add(ParamItem(ParamItem.TEXT,"文本"))
        add(ParamItem(ParamItem.GRAPHICS,"图像"))
        add(ParamItem(ParamItem.LINE,"实线"))
        add(ParamItem(ParamItem.DASHED_LINE,"虚线"))
        add(ParamItem(ParamItem.MULTI,"混排"))
    }){

    } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return viewBinding.root
    }

    private fun initWindow(){
        val attributes = dialog?.window?.attributes
        attributes?.width = 300
        attributes?.height = 500
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWindow()
        initEven()
        initData()
    }

    private fun initData(){
        viewBinding.rvContent.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = contentAdapter
        }
    }

    private fun initEven(){
        viewBinding.tvCancel.setOnClickListener { dismiss() }
    }
}