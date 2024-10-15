package com.eiviayw.drawingsupport.activity

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eiviayw.drawingsupport.bean.ParamItem
import com.eiviayw.drawingsupport.bean.ParamTypeHolder
import com.eiviayw.drawingsupport.databinding.ItemParamTypeBinding

class ParamTypeAdapter(private val data:List<ParamItem>,
    private val listener: OnClickListener):RecyclerView.Adapter<ParamTypeHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ParamTypeHolder {
        return ParamTypeHolder(ItemParamTypeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun getItemCount(): Int = 0

    override fun onBindViewHolder(p0: ParamTypeHolder, p1: Int) {
        p0.setText(data[p1].name)
        p0.setOnItemClickListener(listener)
    }

}