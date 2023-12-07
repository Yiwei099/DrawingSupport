package com.eiviayw.library.draw

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.Half.toFloat
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.element.BaseElement
import com.eiviayw.library.bean.element.GraphicsElement
import com.eiviayw.library.bean.element.LineDashedElement
import com.eiviayw.library.bean.element.LineElement
import com.eiviayw.library.bean.element.TextElement
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.GraphicsParam
import com.eiviayw.library.bean.param.LineDashedParam
import com.eiviayw.library.bean.param.LineParam
import com.eiviayw.library.bean.param.MultiElementParam
import com.eiviayw.library.bean.param.TextParam
import java.io.ByteArrayOutputStream

/**
 * 指路：https://github.com/Yiwei099
 *
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
            mainPaint.pathEffect = null
            mainPaint.style = Paint.Style.FILL
            mainPaint.color = Color.BLACK
            when (it) {
                is TextElement -> {
                    mainPaint.textSize = it.size
                    mainPaint.typeface = it.typeface
                    Drawing.getInstance().drawText(it, bitmapOption, canvas, mainPaint)
                }

                is LineElement -> {
                    mainPaint.textSize = it.size
                    mainPaint.typeface = it.typeface
                    Drawing.getInstance().drawLine(it, canvas, mainPaint)
                }

                is LineDashedElement -> {
                    val effects = DashPathEffect(floatArrayOf(it.on, it.off), 0f)
                    mainPaint.pathEffect = effects
                    mainPaint.style = Paint.Style.STROKE

                    Drawing.getInstance().drawDashedLine(it, canvas, mainPaint)
                }

                is GraphicsElement -> {
                    mainPaint.textSize = it.size
                    mainPaint.typeface = it.typeface
                    Drawing.getInstance().drawGraphics(it, canvas, mainPaint)
                }

                else -> {

                }
            }
        }

        val resultArray = compressBitmapToByteArray(bitmap)
        bitmap.recycle()
        return resultArray
    }

    /**
     * 业务数据源转换成绘制元素
     * @param bitmapOption 当前图像的标准参数
     * @param sourceData 业务数据源
     * @param paint 画笔
     */
    private fun convertSourceDataToElement(
        bitmapOption: BitmapOption,
        sourceData: List<BaseParam>,
        paint: Paint
    ): Pair<List<BaseElement>, Float> {
        val result = mutableListOf<BaseElement>()

        var startYInCanvas = bitmapOption.topIndentation

        val maxWidth = bitmapOption.getEffectiveWidth()
        val defaultStartX = bitmapOption.startIndentation

        for (index in sourceData.indices) {
            when (val sourceItem = sourceData[index]) {
                is MultiElementParam -> {

                    val firstItem = handleMultiParamItem(
                        sourceItem.param1,
                        paint,
                        maxWidth,
                        defaultStartX,
                        startYInCanvas,
                    )
                    val secondItem = handleMultiParamItem(
                        sourceItem.param2,
                        paint,
                        maxWidth,
                        firstItem.first,
                        startYInCanvas,
                    )
                    val thirdItem = handleMultiParamItem(
                        sourceItem.param3,
                        paint,
                        maxWidth,
                        secondItem.first,
                        startYInCanvas,
                    )

                    val maxItemHeight = getMaxFromMany(
                        firstItem.second,
                        secondItem.second,
                        thirdItem.second
                    )

                    /*if (maxItemHeight == firstItem.second){
                        //第一个元素最高
                        if (sourceItem.param2.gravity == Constant.Companion.Gravity.CENTER){
                            secondItem.third.forEach {

                            }
                        }else if (sourceItem.param2.gravity == Constant.Companion.Gravity.BOTTOM){
                            secondItem.third.forEach {

                            }
                        }else{
                            //nothing to do
                        }

                        if (sourceItem.param3.gravity == Constant.Companion.Gravity.CENTER){
                            thirdItem.third.forEach {

                            }
                        }else if (sourceItem.param3.gravity == Constant.Companion.Gravity.BOTTOM){
                            thirdItem.third.forEach {

                            }
                        }else{
                            //nothing to do
                        }
                    }else if (maxItemHeight == secondItem.second){
                        //第二个元素最高

                    }else{
                        //第三个元素最高
                    }*/

                    startYInCanvas = maxItemHeight

                    startYInCanvas = startYInCanvas.plus(sourceItem.perLineSpace)
                }

                is TextParam -> {
                    //填充画笔
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface

                    val elementResult = handleTextParamToElement(
                        maxWidth.toDouble(),
                        sourceItem,
                        paint,
                        sourceItem.text,
                        sourceItem.align,
                        defaultStartX,
                        startYInCanvas
                    )
                    startYInCanvas = elementResult.first
                    result.addAll(elementResult.second)
                    startYInCanvas = startYInCanvas.plus(sourceItem.perLineSpace)
                }

                is LineParam -> {
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface
                    val width = maxWidth.times(sourceItem.weight)
                    val measure = measureText(paint, "-")
                    val height = measure.second
                    result.add(
                        LineElement().apply {
                            setStartXValue(defaultStartX)
                            setEndXValue(width.toFloat())
                            setStartYValue(startYInCanvas.minus(height))
                            setEndYValue(startYInCanvas.minus(height))
                            setTextSize(sourceItem.size)
                            setFaceType(sourceItem.typeface)
                            setLineSpace(sourceItem.perLineSpace)
                        }
                    )
                    startYInCanvas = startYInCanvas.plus(sourceItem.perLineSpace)

                }

                is LineDashedParam -> {
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface
                    val width = maxWidth.times(sourceItem.weight)
                    val measure = measureText(paint, "-")
                    val height = measure.second
                    result.add(
                        LineDashedElement(on = sourceItem.on, off = sourceItem.off).apply {
                            setStartXValue(defaultStartX)
                            setEndXValue(width.toFloat())
                            setStartYValue(startYInCanvas.minus(height))
                            setEndYValue(startYInCanvas.minus(height))
                            setTextSize(sourceItem.size)
                            setFaceType(sourceItem.typeface)
                            setLineSpace(sourceItem.perLineSpace)
                        }
                    )
                    startYInCanvas = startYInCanvas.plus(sourceItem.perLineSpace)
                }

                is GraphicsParam -> {
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface
                    result.add(
                        GraphicsElement(sourceItem.bitmapData).apply {
                            setStartXValue(bitmapOption.getCenterX().minus(sourceItem.width.div(2)))
                            setEndXValue(defaultStartX.plus(sourceItem.width))
                            setStartYValue(startYInCanvas)
                            setEndYValue(startYInCanvas.plus(sourceItem.height))
                            setTextSize(sourceItem.size)
                            setFaceType(sourceItem.typeface)
                            setLineSpace(sourceItem.perLineSpace)
                        }
                    )

                    startYInCanvas =
                        startYInCanvas.plus(sourceItem.height).plus(sourceItem.perLineSpace)
                }

                else -> {
                    //更多类型正在推出中
                }
            }
        }

        return Pair(result, startYInCanvas)
    }

    private fun handleTextParamToElement(
        elementMaxWidth: Double,
        sourceItem: TextParam,
        paint: Paint,
        text: String,
        align: Int,
        defaultStartX: Float,
        startYInCanvas: Float,
    ): Pair<Float,List<BaseElement>> {
        val result = mutableListOf<BaseElement>()
        var endYInCanvas = 0f
        val measure = measureText(paint, text)
        val width = measure.first

        if (width < elementMaxWidth) {
            //不需要换行处理
            endYInCanvas = startYInCanvas.plus(measure.second)
            val startX =
                if (align == Constant.Companion.Align.ALIGN_END) defaultStartX.plus(
                    elementMaxWidth.minus(
                        width
                    )
                )
                    .toFloat() else defaultStartX
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
            val resultY = convertEnterLineElement(
                text,
                align,
                startYInCanvas,
                defaultStartX,
                elementMaxWidth,
                paint,
                sourceItem,
            )
            endYInCanvas = resultY.first
            result.addAll(resultY.third)
        }

        return Pair(endYInCanvas,result)
    }

    private fun convertEnterLineElement(
        text: String,
        align: Int,
        startYInCanvas: Float,
        defaultStartX: Float,
        elementMaxWidth: Double,
        paint: Paint,
        sourceItem: TextParam,
    ): Triple<Float, Float,List<BaseElement>> {
        val result = mutableListOf<BaseElement>()
        var tempStartYInCanvas = startYInCanvas
        val char = text.toCharArray()
        val charBuilder = StringBuilder()
        val tempCharBuilder = StringBuilder()
        var sumItemY = 0f
        var textHeight = 0
        for (index in char.indices) {
            //迭代字符
            val value = char[index]

            val tempMeasure = measureText(paint, tempCharBuilder.append(value).toString())
            val tempWidth = tempMeasure.first
            if (textHeight == 0) {
                textHeight = tempMeasure.second
            }

            val lastChar = index == char.size.minus(1)
            val fullWidth = tempWidth > elementMaxWidth
            if (fullWidth || lastChar) {
                if (!fullWidth) {
                    //没达到最大宽度，但是已是最后一个字符，需要把最后的字符添加进来，否则会漏掉
                    charBuilder.append(value)
                }
                val width = measureText(paint, charBuilder.toString()).first
                val startX =
                    if (align == Constant.Companion.Align.ALIGN_END) defaultStartX.plus(
                        elementMaxWidth.minus(width)
                    )
                        .toFloat() else defaultStartX
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
                    tempStartYInCanvas = tempStartYInCanvas.plus(sourceItem.perLineSpace)
                    tempCharBuilder.append(value)
                    charBuilder.append(value)
                }
            } else {
                charBuilder.append(value)
            }

        }
        return Triple(tempStartYInCanvas, sumItemY,result)
    }

    /**
     * 处理混排业务元素
     * @param item 元素
     * @param paint 画笔
     * @param maxWidth 画布最大宽度
     * @param defaultStartX X的起始位置
     * @param startYInCanvas Y的起始位置
     * @return 当前元素的宽高数据
     */
    private fun handleMultiParamItem(
        item: BaseParam,
        paint: Paint,
        maxWidth: Float,
        defaultStartX: Float,
        startYInCanvas: Float,
    ): Triple<Float, Float,List<BaseElement>> {
        when (item) {
            is TextParam -> {
                //填充画笔
                paint.textSize = item.size
                paint.typeface = item.typeface

                val itemWidth = maxWidth.times(item.weight)
                val itemHeight = handleTextParamToElement(
                    itemWidth,
                    item,
                    paint,
                    item.text,
                    item.align,
                    defaultStartX,
                    startYInCanvas
                )
                return Triple(itemWidth.toFloat(), itemHeight.first,itemHeight.second)
            }

            else -> {
                //暂未支持的类型
                return Triple(defaultStartX, 0f, emptyList<BaseElement>())
            }
        }
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