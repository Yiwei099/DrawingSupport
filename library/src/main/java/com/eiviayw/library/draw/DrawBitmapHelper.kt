package com.eiviayw.library.draw

import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import com.eiviayw.library.bean.Element
import com.eiviayw.library.bean.SourceParam
import com.eiviayw.library.util.SerializationUtils

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-21 21:43
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
object DrawBitmapHelper {
    private val optionMap: MutableMap<Int, BitmapOption> = mutableMapOf()

    /**
     * 绘制总入口
     * 绘制前数据处理
     * @param bitmapType 图片类型
     * @param sourceData 图片数据源
     */
    fun convert(bitmapType: Int, sourceData: List<SourceParam>) {
        //获取当前图片样式模版
        val bitmapOption = optionMap[bitmapType] ?: BitmapOption()
        val mainPaint = Paint().apply {
            //是否开启抗锯齿
            isAntiAlias = bitmapOption.antiAlias
            isFilterBitmap = bitmapOption.antiAlias
        }
        convertSourceDataToElement(bitmapOption, sourceData, mainPaint)
    }

    private fun convertSourceDataToElement(
        bitmapOption: BitmapOption,
        sourceData: List<SourceParam>,
        paint: Paint
    ): List<Element> {
        val result = mutableListOf<Element>()

        var y = bitmapOption.topIndentation

        val maxWidth = bitmapOption.maxWidth
        val defaultStartX = bitmapOption.startIndentation

        val perLineSpace = bitmapOption.perLineSpace

        sourceData.forEach { sourceItem ->
            //填充画笔
            paint.textSize = sourceItem.size
            paint.typeface = sourceItem.typeface

            val startContent = sourceItem.startContent
            if (!isEmpty(startContent)) {
                val elementMaxWidth = maxWidth.minus(sourceItem.startWeight)
                val measure = measureText(paint, startContent)
                val width = measure.first
                val height = measure.second

                if (width < elementMaxWidth) {
                    //不需要换行处理
                    result.add(
                        Element(startX = defaultStartX, endX = defaultStartX.plus(width),
                        startY = y,endY = y.plus(height), size = sourceItem.size, typeface = sourceItem.typeface)
                    )
                }else{
                    //需要换行处理
                }
            }
        }
        return result
    }

    //<editor-fold desc="图片模版参数管理">

    /**
     * 增加图片模版
     * @param key 索引
     * @param value 图片模版
     */
    fun addOption(key: Int, value: BitmapOption): DrawBitmapHelper {
        optionMap[key] = value
        return this
    }

    /**
     * 增加图片模版
     * @param options 多种图片模版
     */
    fun addOptions(options: Map<Int, BitmapOption>): DrawBitmapHelper {
        optionMap.putAll(options)
        return this
    }

    fun onDestroy() {
        optionMap.clear()
    }
    //</editor-fold desc="图片模版参数管理">

    private fun isEmpty(str: String?): Boolean = TextUtils.isEmpty(str)

    /**
     * 获取当前内容宽度与高度
     * @param paint 画笔
     * @param text 内容
     */
    private fun measureText(paint: Paint, text: String): Pair<Int, Int> {
        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)
        return Pair(rect.width(), rect.height())
    }

}