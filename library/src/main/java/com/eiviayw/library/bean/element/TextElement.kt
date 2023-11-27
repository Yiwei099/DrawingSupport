package com.eiviayw.library.bean.element

import android.graphics.Typeface

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 * 文字元素块
 */
data class TextElement(
    val text: String = "",//内容
    val align: Int,
    val textWidth:Int,
    val maxWidth:Double = 0.0
) : BaseElement()