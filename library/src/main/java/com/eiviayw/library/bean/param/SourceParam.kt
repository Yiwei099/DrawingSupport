package com.eiviayw.library.bean.param

import android.graphics.Typeface
import com.eiviayw.library.Constant

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:49
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
data class SourceParam(
    val firstText:String = "",//第一项内容
    val secondText:String = "",//第二项内容
    val thirdText:String = "",//第三项内容
    val firstWeight:Double = 0.35,//第一项内容宽度所占的比重（用于限制最大宽度）
    val secondWeight:Double = 0.30,//第二项内容宽度所占的比重
    val thirdWeight:Double = 0.35,//第三项内容宽度所占的比重
    val firstTextAlign:Int = Constant.ALIGN_START,//第一项内容对齐方式，默认开始对齐
    val secondTextAlign:Int = Constant.ALIGN_CENTER,//第二项内容对齐方式 默认居中对齐
    val thirdTextAlign:Int = Constant.ALIGN_END,//第三项内容对齐方式 默认结束对齐
    val mayBeWrap: Boolean = false,//是否有换行的可能，默认不需要换行
):BaseParam(){
    fun setTextSize(size:Float){
        this.size = size
    }

    fun setFaceType(type:Typeface){
        typeface = type
    }
}