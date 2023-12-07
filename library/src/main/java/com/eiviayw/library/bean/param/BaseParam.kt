package com.eiviayw.library.bean.param

import android.graphics.Typeface

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:49
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 */
open class BaseParam(
    var size: Float = 26f,//内容字号
    var typeface: Typeface = Typeface.DEFAULT,//内容样式：加粗、正常、斜体
    var perLineSpace: Int = 10,//行距
)