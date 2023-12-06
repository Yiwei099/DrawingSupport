package com.eiviayw.library.bean.element

import android.graphics.Typeface


/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 * 元素块基类
 */
open class BaseElement(
    val id: String = "",//唯一标识(可选使用)
    var startX: Float = 0f,
    var endX: Float = 0f,
    var startY: Float = 0f,
    var endY: Float = 0f,
    var typeface: Typeface = Typeface.DEFAULT,//内容样式：加粗、正常、斜体
    var size: Float = 26f,//内容字号
){
    fun setStartXValue(x: Float) {
        startX = x
    }

    fun setEndXValue(x: Float) {
        endX = x
    }

    fun setStartYValue(y: Float) {
        startY = y
    }

    fun setEndYValue(y: Float) {
        endY = y
    }
    fun setTextSize(value:Float){
        size = value
    }
    fun setFaceType(value:Typeface){
        typeface = value
    }
}