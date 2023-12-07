package com.eiviayw.library.draw

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.element.BaseElement
import com.eiviayw.library.bean.element.BitmapElement
import com.eiviayw.library.bean.element.LineDashedElement
import com.eiviayw.library.bean.element.LineElement
import com.eiviayw.library.bean.element.TextElement
import com.eiviayw.library.bean.param.BaseParam
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

                is BitmapElement -> {

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

        val subPerLineSpace = bitmapOption.subPerLineSpace

        for (index in sourceData.indices) {
            when (val sourceItem = sourceData[index]) {
                is MultiElementParam -> {

                    val firstItem = handleMultiParamItem(
                        sourceItem.param1,
                        paint,
                        maxWidth,
                        defaultStartX,
                        startYInCanvas,
                        subPerLineSpace,
                        result
                    )
                    val secondItem = handleMultiParamItem(
                        sourceItem.param2,
                        paint,
                        maxWidth,
                        firstItem.first,
                        startYInCanvas,
                        subPerLineSpace,
                        result
                    )
                    val thirdItem = handleMultiParamItem(
                        sourceItem.param3,
                        paint,
                        maxWidth,
                        secondItem.first,
                        startYInCanvas,
                        subPerLineSpace,
                        result
                    )

                    startYInCanvas = getMaxFromMany(
                        firstItem.second,
                        secondItem.second,
                        thirdItem.second
                    )

                    startYInCanvas =
                        handlePerLineSpace(index, sourceData, startYInCanvas, bitmapOption, false)
                }

                is TextParam -> {
                    //填充画笔
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface

                    startYInCanvas = handleTextParamToElement(
                        maxWidth.toDouble(),
                        sourceItem,
                        paint,
                        sourceItem.text,
                        sourceItem.align,
                        result,
                        defaultStartX,
                        startYInCanvas, subPerLineSpace
                    )
                    startYInCanvas =
                        handlePerLineSpace(index, sourceData, startYInCanvas, bitmapOption, false)
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
                        }
                    )
                    startYInCanvas += height

                    startYInCanvas = handlePerLineSpace(index, sourceData, startYInCanvas, bitmapOption,true)
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
                        }
                    )
                    startYInCanvas += height
                    startYInCanvas = handlePerLineSpace(index, sourceData, startYInCanvas, bitmapOption,true)
                }

                else -> {

                }
            }
        }

        return Pair(result, startYInCanvas)
    }

    private fun handlePerLineSpace(
        index: Int,
        sourceData: List<BaseParam>,
        startYInCanvas: Float,
        bitmapOption: BitmapOption,
        isLineParam: Boolean
    ): Float {
        var startYInCanvas1 = startYInCanvas
        if (index < sourceData.size - 1) {
            val nextItem = sourceData[index + 1]
            if (nextItem is TextParam || nextItem is MultiElementParam) {
                startYInCanvas1 += bitmapOption.perLineSpace
                if (isLineParam) {
                    startYInCanvas1 += bitmapOption.perLineSpace
                }
            } else if (nextItem is LineParam) {
                startYInCanvas1 += bitmapOption.subPerLineSpace
            }
        }
        return startYInCanvas1
    }

    private fun handleTextParamToElement(
        elementMaxWidth: Double,
        sourceItem: TextParam,
        paint: Paint,
        text: String,
        align: Int,
        result: MutableList<BaseElement>,
        defaultStartX: Float,
        startYInCanvas: Float,
        subPerLineSpace: Int
    ): Float {
        var endYInCanvas = 0f
        var itemY = 0f
        val measure = measureText(paint, text)
        val width = measure.first

        if (width < elementMaxWidth) {
            //不需要换行处理
            endYInCanvas = startYInCanvas.plus(measure.second)
            itemY = measure.second.toFloat()
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
                result, subPerLineSpace
            )
            itemY += resultY.second
            endYInCanvas = resultY.first
        }

        return endYInCanvas
//        return itemY
    }

    private fun convertEnterLineElement(
        text: String,
        align: Int,
        startYInCanvas: Float,
        defaultStartX: Float,
        elementMaxWidth: Double,
        paint: Paint,
        sourceItem: TextParam,
        result: MutableList<BaseElement>,
        subPerLineSpace: Int
    ): Pair<Float, Float> {
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
                    tempStartYInCanvas = tempStartYInCanvas.plus(subPerLineSpace)
                    tempCharBuilder.append(value)
                    charBuilder.append(value)
                }
            } else {
                charBuilder.append(value)
            }

        }
        return Pair(tempStartYInCanvas, sumItemY)
    }

    /**
     * 处理混排业务元素
     * @param item 元素
     * @param paint 画笔
     * @param maxWidth 画布最大宽度
     * @param defaultStartX X的起始位置
     * @param startYInCanvas Y的起始位置
     * @param subPerLineSpace 小行距
     * @param result 绘制元素数组
     * @return 当前元素的宽高数据
     */
    private fun handleMultiParamItem(
        item: BaseParam,
        paint: Paint,
        maxWidth: Float,
        defaultStartX: Float,
        startYInCanvas: Float,
        subPerLineSpace: Int,
        result: MutableList<BaseElement>
    ): Pair<Float, Float> {
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
                    result,
                    defaultStartX,
                    startYInCanvas, subPerLineSpace
                )
                return Pair(itemWidth.toFloat(), itemHeight)
            }

            else -> {
                //暂未支持的类型
                return Pair(defaultStartX, 0f)
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