package com.eiviayw.library.bean

import android.graphics.Typeface

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
data class Element(
    val id: String = "",//唯一标识(可选使用)
    val content: String = "",//内容
    var startX: Float,
    var endX: Float,
    var startY: Float,
    var endY: Float,
    val typeface: Typeface = Typeface.DEFAULT,//内容样式：加粗、正常、斜体
    val size: Float = 26f,//内容字号
)