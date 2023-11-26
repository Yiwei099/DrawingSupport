package com.eiviayw.library.bean

import android.graphics.Typeface

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-21 21:02
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
open class BaseSourceData(
    val id: String,//唯一标识
    val textSize: Int = 0,//内容字号
    val textTypeface: Typeface = Typeface.DEFAULT,//内容样式：加粗、正常、斜体
    val mayBeWrap: Boolean = false,//是否有换行的可能，默认不需要换行
)