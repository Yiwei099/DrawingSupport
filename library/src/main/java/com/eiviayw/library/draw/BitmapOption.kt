package com.eiviayw.library.draw

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-25 22:00
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 * 图片绘制标准参数 同一类型图片使用同一套标准参数
 * 封装成独立的对象，方便应用不同的图片
 */
class BitmapOption(
    val maxWidth: Int = 576,//画布宽度
    val topIndentation: Float = 20f,//顶部方向边距
    val startIndentation: Float = 20f,//开始方向边距
    val endIndentation: Float = 20f,//结束方向边距
    val bottomBlankHeight: Int = 10,//底部留白
    val antiAlias: Boolean = false,//开启抗锯齿：true-开启，false-关闭
){
    /**
     * 图片内容有效宽度
     */
    fun getEffectiveWidth() = maxWidth - endIndentation

    fun getCenterX() = getEffectiveWidth().div(2)
}