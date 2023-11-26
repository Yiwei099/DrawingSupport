package com.eiviayw.library.draw

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-25 22:00
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 * 图片绘制参数
 * 封装成独立的对象，方便应用不同的图片
 */
class BitmapOption(
    val maxWidth: Int = 576,//画布宽度
    val topIndentation: Int = 20,//顶部方向边距
    val bottomIndentation: Int = 20,//底部方向边距
    val startIndentation: Int = 20,//结束方向边距
    val endIndentation: Int = 20,//结束方向边距
    val perLineSpace: Int = 14,//标准行距
    val subPerLineSpace: Int = 8,//副标题行距
    val bottomBlankHeight: Int = 100,//底部留白
    val antiAlias: Boolean = false,//开启抗锯齿：true-开启，false-关闭
){
    /**
     * 图片内容有效宽度
     */
    fun getEffectiveWidth() = maxWidth - startIndentation - endIndentation
}