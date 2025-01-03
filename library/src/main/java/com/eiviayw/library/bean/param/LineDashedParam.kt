package com.eiviayw.library.bean.param

import android.graphics.Paint

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:49
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 * 虚线
 */
class LineDashedParam(
    val weight:Double = 1.0,
    val on:Float = 5f,
    val off:Float = 10f
):BaseParam() {

    init {
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }
}