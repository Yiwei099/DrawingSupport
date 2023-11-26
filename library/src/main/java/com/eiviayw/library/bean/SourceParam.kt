package com.eiviayw.library.bean

import android.graphics.Typeface

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:49
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
data class SourceParam(
    val startContent:String = "",//第一项内容
    val centerContent:String = "",//第二项内容
    val endContent:String = "",//第三项内容
    val startWeight:Double = 0.35,//第一项内容宽度所占的比重（用于限制最大宽度）
    val centerWeight:Double = 0.30,//第二项内容宽度所占的比重
    val endWeight:Double = 0.35,//第三项内容宽度所占的比重
    val startContentAlign:Int = ALIGN_START,//第一项内容对齐方式，默认开始对齐
    val centerContentAlign:Int = ALIGN_CENTER,//第二项内容对齐方式 默认居中对齐
    val endContentAlign:Int = ALIGN_END,//第三项内容对齐方式 默认结束对齐
    val size:Float = 26f,//内容字号
    val typeface: Typeface = Typeface.DEFAULT,//内容样式：加粗、正常、斜体
    val mayBeWrap: Boolean = false,//是否有换行的可能，默认不需要换行
){
    companion object{
        /**
         * 开始对齐
         */
        const val ALIGN_START = 0
        /**
         * 居中对齐
         */
        const val ALIGN_CENTER = 1
        /**
         * 结束对齐
         */
        const val ALIGN_END = 2
    }
}