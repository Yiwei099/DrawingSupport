package com.eiviayw.library.draw

import com.eiviayw.library.Constant

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
    val topIndentation: Float = 40f,//顶部方向边距
    val startIndentation: Float = 20f,//开始方向边距
    val endIndentation: Float = 20f,//结束方向边距
    val bottomBlankHeight: Int = 10,//底部留白
    val antiAlias: Boolean = false,//开启抗锯齿：true-开启，false-关闭
    val maxHeight:Int = 0,//画布高度
    val gravity:Int = Constant.Companion.Gravity.TOP,//内容对齐方式：顶部/居中/底部，默认顶部 （只适用于固定画布高度的场景下）
    val followEffectItem:Boolean = false,//使用于固定画布高度的场景下：true - 画布剩余高度不足时终止绘制；
) {
    /**
     * 图片内容有效宽度
     */
    fun getEffectiveWidth() = maxWidth - endIndentation

    fun getCenterX() = getEffectiveWidth().div(2)

    /**
     * 是否限制画布高度
     * @return true - 限制；false - 不限制
     */
    fun fixedHeight():Boolean = maxHeight > 0

    /**
     * 内容是否顶部对齐
     * @return true - 顶部对齐；false - 非顶部对齐
     */
    private fun isGravityTop():Boolean = gravity == Constant.Companion.Gravity.TOP

    /**
     * 内容是否溢出（内容高度是否超过画布最大高度限制）
     * @param contentY 内容高度
     * @return true - 溢出；false - 未溢出
     */
    private fun contentFull(contentY:Int):Boolean = contentY > maxHeight

    /**
     * 画布空白差值
     * @param contentY 内容高度
     * @return 没限制画布最大高度时 = 0；内容顶部对齐时 = 0；内容居中对齐时 = 差值/2；内容顶部对齐时 = 差值
     */
    fun diffContentY(contentY:Int):Int{
        return if (fixedHeight() && !isGravityTop() && !contentFull(contentY)){
            when(gravity){
                Constant.Companion.Gravity.BOTTOM -> maxHeight - contentY
                else -> (maxHeight - contentY) / 2
            }

        }else{
            0
        }
    }

}