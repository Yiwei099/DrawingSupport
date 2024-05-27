package com.eiviayw.library.bean.param

import com.eiviayw.library.Constant

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
class GraphicsParam(
    val bitmapData: ByteArray,
    val width:Int,
    val height: Int,
    val align:Int = Constant.Companion.Align.ALIGN_CENTER
):BaseParam()