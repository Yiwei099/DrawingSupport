package com.eiviayw.library.bean.param

import com.eiviayw.library.Constant

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:49
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 * 文本
 */
data class TextParam(
    val text:String = "",//内容
    val weight:Double = 1.0,//第一项内容宽度所占的比重（用于限制最大宽度）
    var align:Int = Constant.Companion.Align.ALIGN_START,//文本内容对齐方式，默认开始对齐
):BaseParam()