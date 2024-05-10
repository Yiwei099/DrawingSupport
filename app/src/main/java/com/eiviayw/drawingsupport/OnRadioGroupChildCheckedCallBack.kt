package com.eiviayw.drawingsupport


import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-05-20 22:09
 * @Description:由于底层的 OnCheckedChangeListener 回调两次的机制，会导致逻辑上有多余的，不必要的处理
 * 此类为解决此现象，只回调选中的逻辑
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
abstract class OnRadioGroupChildCheckedCallBack(
    private val context: View
) :OnCheckedChangeListener{
    override fun onCheckedChanged(p0: RadioGroup, p1: Int) {
        if(-1 == p1) {
            //此处是清除选中的回调。
            return
        }
        val checkedView = context.findViewById<RadioButton>(p1)
        if(!checkedView.isChecked) {
            //此处是某个选中按钮被取消的回调，在调用check方法修改选中的时候会触发
            return
        }
        //正常选中我们要进行的处理
        onCheckedCallBack(p0,p1)
    }

    abstract fun onCheckedCallBack(radioGroup: RadioGroup, viewID: Int)
}