package com.eiviayw.library.bean.element

import com.eiviayw.library.Constant

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 * 位图元素块
 */
class GraphicsElement(
    val bitmapData: ByteArray,
    val align: Int = Constant.Companion.Align.ALIGN_CENTER
): BaseElement()