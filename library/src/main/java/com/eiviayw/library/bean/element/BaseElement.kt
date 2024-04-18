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
    @Deprecated("请留意 BaseY")
    var startY: Float = 0f,
    @Deprecated("请留意 BaseY")
    var endY: Float = 0f,
    var typeface: Typeface = Typeface.DEFAULT,//内容样式：加粗、正常、斜体
    var size: Float = 26f,//内容字号
    private var perLineSpace: Int = 10,//行距
    var baseY:Float = 0f,
    var height:Int = 0
){

    fun setElementHeight(value:Int){
        height = value
    }

    fun setBaseLine(value:Float){
        baseY = value
    }
    fun setStartXValue(x: Float) {
        startX = x
    }

    fun setEndXValue(x: Float) {
        endX = x
    }

    @Deprecated("请留意 setBaseLine")
    fun setStartYValue(y: Float) {
        startY = y
    }

    @Deprecated("请留意 setBaseLine")
    fun setEndYValue(y: Float) {
        endY = y
    }
    fun setTextSize(value:Float){
        size = value
    }
    fun setFaceType(value:Typeface){
        typeface = value
    }

    fun setLineSpace(height:Int){
        perLineSpace = height
    }
}