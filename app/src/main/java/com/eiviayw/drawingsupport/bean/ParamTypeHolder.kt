package com.eiviayw.drawingsupport.bean

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eiviayw.drawingsupport.databinding.ItemParamTypeBinding

class ParamTypeHolder(private val view: ItemParamTypeBinding): RecyclerView.ViewHolder(view.root){

    fun setText(text:String){
        view.tvItem.text = text
    }

    fun setOnItemClickListener(listener: View.OnClickListener){
        view.tvItem.setOnClickListener(listener)
    }
}