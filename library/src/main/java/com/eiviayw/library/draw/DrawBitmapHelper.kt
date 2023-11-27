package com.eiviayw.library.draw

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.element.BaseElement
import com.eiviayw.library.bean.element.BitmapElement
import com.eiviayw.library.bean.element.LineElement
import com.eiviayw.library.bean.element.TextElement
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.LineParam
import com.eiviayw.library.bean.param.SourceParam
import java.io.ByteArrayOutputStream

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
    fun convert(bitmapType: Int, sourceData: List<BaseParam>): ByteArray {
        //获取当前图片样式模版
        val bitmapOption = optionMap[bitmapType] ?: BitmapOption()
        val mainPaint = Paint().apply {
            //是否开启抗锯齿
            isAntiAlias = bitmapOption.antiAlias
            isFilterBitmap = bitmapOption.antiAlias
        }
        val result = convertSourceDataToElement(bitmapOption, sourceData, mainPaint)

        val bitmap = Drawing.getInstance().createBimap(bitmapOption.maxWidth, result.second.toInt())
        val canvas = Drawing.getInstance().getNewCanvas(bitmap)

        result.first.forEach {
            when (it) {
                is TextElement -> {
                    mainPaint.textSize = it.size
                    mainPaint.typeface = it.typeface
                    Drawing.getInstance().drawText(it, bitmapOption, canvas, mainPaint)
                }

                is BitmapElement -> {

                }

                is LineElement -> {
                    mainPaint.textSize = it.size
                    mainPaint.typeface = it.typeface
                    Drawing.getInstance().drawLine(it, canvas, mainPaint)
                }

                else -> {

                }
            }
        }

        val resultArray = compressBitmapToByteArray(bitmap)
        bitmap.recycle()
        return resultArray
    }

    private fun convertSourceDataToElement(
        bitmapOption: BitmapOption,
        sourceData: List<BaseParam>,
        paint: Paint
    ): Pair<List<BaseElement>, Float> {
        val result = mutableListOf<BaseElement>()

        var startYInCanvas = bitmapOption.topIndentation

        val maxWidth = bitmapOption.getEffectiveWidth()
        val defaultStartX = bitmapOption.startIndentation

        val subPerLineSpace = bitmapOption.subPerLineSpace

        sourceData.forEach { sourceItem ->
            when(sourceItem){
                is SourceParam ->{
                    //填充画笔
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface

                    var firstTextHeight = 0f
                    var secondTextHeight = 0f
                    var thirdTextHeight = 0f

                    val firstMaxWidth = maxWidth.times(sourceItem.firstWeight)
                    val secondMaxWidth = maxWidth.times(sourceItem.secondWeight)
                    val thirdMaxWidth = maxWidth.times(sourceItem.thirdWeight)

                    val firstText = sourceItem.firstText
                    if (!isEmpty(firstText)) {
                        firstTextHeight = handleTextParamToElement(
                            firstMaxWidth,
                            sourceItem,
                            paint,
                            firstText,
                            sourceItem.firstTextAlign,
                            result,
                            defaultStartX,
                            startYInCanvas,subPerLineSpace
                        )
                    }

                    val secondText = sourceItem.secondText
                    if (!isEmpty(secondText)) {
                        secondTextHeight = handleTextParamToElement(
                            secondMaxWidth,
                            sourceItem,
                            paint,
                            secondText,
                            sourceItem.secondTextAlign,
                            result,
                            firstMaxWidth.toFloat(),
                            startYInCanvas,subPerLineSpace
                        )
                    }

                    val thirdText = sourceItem.thirdText
                    if (!isEmpty(thirdText)) {
                        thirdTextHeight = handleTextParamToElement(
                            thirdMaxWidth,
                            sourceItem,
                            paint,
                            thirdText,
                            sourceItem.thirdTextAlign,
                            result,
                            secondMaxWidth.toFloat(),
                            startYInCanvas,subPerLineSpace
                        )
                    }

                    startYInCanvas += getMaxFromMany(
                        firstTextHeight,
                        secondTextHeight,
                        thirdTextHeight
                    ).plus(bitmapOption.perLineSpace)
                }

                is LineParam ->{
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface
                    val width = maxWidth.times(sourceItem.weight)
                    val measure = measureText(paint, "-")
                    val height = measure.second
                    result.add(
                        LineElement().apply {
                            setStartXValue(startX)
                            setEndXValue(startX.plus(width).toFloat())
                            setStartYValue(startYInCanvas)
                            setEndYValue(startYInCanvas)
                            setTextSize(sourceItem.size)
                            setFaceType(sourceItem.typeface)
                        }
                    )
                    startYInCanvas += height.plus(bitmapOption.perLineSpace)
                }
                else ->{

                }
            }


        }
        return Pair(result, startYInCanvas)
    }

    private fun handleTextParamToElement(
        elementMaxWidth: Double,
        sourceItem: SourceParam,
        paint: Paint,
        text: String,
        align: Int,
        result: MutableList<BaseElement>,
        defaultStartX: Float,
        startYInCanvas: Float,
        subPerLineSpace:Int
    ): Float {
        var endYInCanvas = 0f
        var itemY = 0f
        val measure = measureText(paint, text)
        val width = measure.first
        val height = measure.second

        if (width < elementMaxWidth) {
            //不需要换行处理
            endYInCanvas = startYInCanvas.plus(height)
            itemY = height.toFloat()
            val startX = if (align == Constant.ALIGN_END) defaultStartX.plus(elementMaxWidth.minus(width)).toFloat() else defaultStartX
            result.add(
                TextElement(
                    text = text,
                    align = align,
                    textWidth = width,
                    maxWidth = elementMaxWidth
                ).apply {
                    setStartXValue(startX)
                    setEndXValue(startX.plus(width))
                    setStartYValue(startYInCanvas)
                    setEndYValue(endYInCanvas)
                    setTextSize(sourceItem.size)
                    setFaceType(sourceItem.typeface)
                }
            )
        } else {
            //需要换行处理
            val textCharArr = text.split(" ")
            if (textCharArr.size == 1) {
                val resultY = convertEnterLineElement(
                    text,
                    align,
                    height,
                    startYInCanvas,
                    defaultStartX,
                    elementMaxWidth,
                    paint,
                    sourceItem,
                    result,subPerLineSpace
                )
                itemY += resultY.second
                endYInCanvas = resultY.first
            } else {
                textCharArr.forEach { char ->
                    val resultY = convertEnterLineElement(
                        char,
                        align,
                        height,
                        startYInCanvas,
                        defaultStartX,
                        elementMaxWidth,
                        paint,
                        sourceItem,
                        result,subPerLineSpace
                    )
                    itemY += resultY.second
                    endYInCanvas = resultY.first
                }
            }
        }

        return itemY
    }

    private fun convertEnterLineElement(
        text: String,
        align: Int,
        textHeight: Int,
        startYInCanvas: Float,
        defaultStartX: Float,
        elementMaxWidth: Double,
        paint: Paint,
        sourceItem: SourceParam,
        result: MutableList<BaseElement>,
        subPerLineSpace:Int
    ): Pair<Float, Float> {
        var tempStartYInCanvas = startYInCanvas
        val char = text.toCharArray()
        val charBuilder = StringBuilder()
        val tempCharBuilder = StringBuilder()
        var sumItemY = 0f
        for (index in char.indices) {
            //迭代字符
            val value = char[index]

            val tempWidth = measureText(paint, tempCharBuilder.append(value).toString()).first

            val lastChar = index == char.size.minus(1)
            val fullWidth = tempWidth > elementMaxWidth
            if (fullWidth || lastChar) {
                if (!fullWidth) {
                    //没达到最大宽度，但是已是最后一个字符，需要把最后的字符添加进来，否则会漏掉
                    charBuilder.append(value)
                }
                val width = measureText(paint, charBuilder.toString()).first
                val startX = if (align == Constant.ALIGN_END) defaultStartX.plus(elementMaxWidth.minus(width)).toFloat() else defaultStartX
                result.add(
                    TextElement(
                        text = charBuilder.toString(),
                        align = align,
                        textWidth = width,
                        maxWidth = elementMaxWidth
                    ).apply {
                        setStartXValue(startX)
                        setEndXValue(startX.plus(width))
                        setStartYValue(tempStartYInCanvas)
                        setEndYValue(tempStartYInCanvas.plus(textHeight))
                        setTextSize(sourceItem.size)
                        setFaceType(sourceItem.typeface)
                    }
                )
                tempStartYInCanvas = tempStartYInCanvas.plus(textHeight)
                sumItemY = sumItemY.plus(textHeight)
                charBuilder.setLength(0)
                tempCharBuilder.setLength(0)
                if (fullWidth) {
                    tempStartYInCanvas = tempStartYInCanvas.plus(subPerLineSpace)
                    sumItemY = sumItemY.plus(subPerLineSpace)
                    charBuilder.append(value)
                }
            } else {
                charBuilder.append(value)
            }

        }
        return Pair(tempStartYInCanvas, sumItemY)
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

    private fun compressBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val ops = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ops)
        return ops.toByteArray()
    }

    private fun isEmpty(str: String?): Boolean = TextUtils.isEmpty(str)

    private fun getMaxFromMany(vararg values: Float): Float {
        var max = 0f
        values.forEach {
            max = if (it > max) {
                it
            } else {
                max
            }
        }
        return max
    }

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