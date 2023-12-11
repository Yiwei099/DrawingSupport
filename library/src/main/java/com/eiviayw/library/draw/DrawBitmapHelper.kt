package com.eiviayw.library.draw

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.DataEntity4
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

    /**
     * 绘制总入口
     * 绘制前数据处理
     * @param bitmapOption 绘制标准参数
     * @param sourceData 图片数据源
     * @return Bitmap数组
     */
    fun convert(bitmapOption: BitmapOption, sourceData: List<BaseParam>): ByteArray {
        //获取当前图片样式模版
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
        //释放Bitmap
        bitmap.recycle()
        return resultArray
    }

    /**
     * 业务数据源转换成绘制元素
     * @param bitmapOption 当前图像的标准参数
     * @param sourceData 业务数据源
     * @param paint 画笔
     * @return 绘制元素块集合、最终绘制位置
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
                    //处理第一个元素
                    val firstItem = handleMultiParamItem(
                        sourceItem.param1,
                        paint,
                        maxWidth,
                        defaultStartX,
                        startYInCanvas,
                    )

                    //处理第二个元素
                    val secondItem = handleMultiParamItem(
                        sourceItem.param2,
                        paint,
                        maxWidth,
                        firstItem.data1,
                        startYInCanvas,
                    )
                    //处理第三个元素
                    val thirdItem = handleMultiParamItem(
                        sourceItem.param3,
                        paint,
                        maxWidth,
                        secondItem.data1,
                        startYInCanvas,
                    )

                    //更新最新的Y值(当前混排元素中取最高的元素)
                    startYInCanvas = getMaxFromMany(
                        firstItem.data2,
                        secondItem.data2,
                        thirdItem.data2
                    )

                    //获取当前混排元素中最高的Item
                    val maxItemHeight = getMaxFromMany(
                        firstItem.data3,
                        secondItem.data3,
                        thirdItem.data3
                    )

                    //处理垂直对齐方式
                    when (maxItemHeight) {
                        firstItem.data3 -> {
                            //第一个元素最高
                            handleElementGravity(
                                sourceItem.param2,
                                secondItem,
                                startYInCanvas,
                                firstItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                thirdItem,
                                startYInCanvas,
                                firstItem
                            )
                        }

                        secondItem.data3 -> {
                            //第二个元素最高
                            handleElementGravity(
                                sourceItem.param1,
                                firstItem,
                                startYInCanvas,
                                secondItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                thirdItem,
                                startYInCanvas,
                                secondItem
                            )
                        }

                        else -> {
                            //第三个元素最高
                            handleElementGravity(
                                sourceItem.param1,
                                firstItem,
                                startYInCanvas,
                                thirdItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                secondItem,
                                startYInCanvas,
                                thirdItem
                            )
                        }
                    }

                    result.addAll(firstItem.data4)
                    result.addAll(secondItem.data4)
                    result.addAll(thirdItem.data4)

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
                        if (sourceItem.align == Constant.Companion.Align.ALIGN_START) defaultStartX else 0f,
                        startYInCanvas
                    )
                    startYInCanvas = elementResult.first
                    result.addAll(elementResult.third)
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

    /**
     * 处理纵向对齐方式
     * @param sourceItem 业务源元素块
     * @param handleItem 需要处理对齐的元素块
     * @param startYInCanvas 最新的Y位置
     * @param targetItem 被对齐的元素块(参照元素块)
     */
    private fun handleElementGravity(
        sourceItem: BaseParam,
        handleItem: DataEntity4<Float, Float, Float, List<BaseElement>>,
        startYInCanvas: Float,
        targetItem: DataEntity4<Float, Float, Float, List<BaseElement>>
    ) {
        when (sourceItem.gravity) {
            Constant.Companion.Gravity.CENTER -> {
                handleItem.data4.forEach {
                    it.startY = startYInCanvas.minus((targetItem.data3.div(2)))
                        .plus(handleItem.data3.div(2))
                    it.endY = it.startY.plus(handleItem.data3)
                }
            }

            Constant.Companion.Gravity.BOTTOM -> {
                handleItem.data4.forEach {
                    it.endY = targetItem.data2
                    it.startY = it.endY.minus(handleItem.data3)
                }
            }

            else -> {
                //nothing to do
            }
        }
    }

    /**
     * 业务源文本元素块转换成绘制文本元素块
     * @param elementMaxWidth 元素快的最大宽度
     * @param sourceItem 业务源元素快
     * @param paint 画笔
     * @param text 文本内容
     * @param align 横向对齐方式
     * @param defaultStartX 默认起始X位置
     * @param startYInCanvas 最终绘制位置
     * @return 最终绘制位置、当前元素块高度、绘制元素块集合
     */
    private fun handleTextParamToElement(
        elementMaxWidth: Double,
        sourceItem: TextParam,
        paint: Paint,
        text: String,
        align: Int,
        defaultStartX: Float,
        startYInCanvas: Float,
    ): Triple<Float, Float, List<BaseElement>> {
        val result = mutableListOf<BaseElement>()
        var endYInCanvas = 0f
        var itemHeight = 0f
        val measure = measureText(paint, text)
        val width = measure.first

        if (width < elementMaxWidth) {
            //不需要换行处理
            itemHeight = measure.second.toFloat()
            endYInCanvas = startYInCanvas.plus(measure.second)
            val startX = when (align) {
                Constant.Companion.Align.ALIGN_END -> defaultStartX.plus(elementMaxWidth.minus(width))
                    .toFloat()

                Constant.Companion.Align.ALIGN_CENTER -> elementMaxWidth.div(2).minus(width.div(2))
                    .plus(defaultStartX).toFloat()

                else -> defaultStartX
            }
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
            itemHeight = resultY.second
            result.addAll(resultY.third)
        }

        return Triple(endYInCanvas, itemHeight, result)
    }

    /**
     * 文本元素自适应换行
     * @param text 文本内容
     * @param align 横向对齐方式
     * @param startYInCanvas 最新的Y位置
     * @param defaultStartX 默认起始X位置
     * @param elementMaxWidth 元素快的最大宽度
     * @param paint 画笔
     * @param sourceItem 业务源元素快
     */
    private fun convertEnterLineElement(
        text: String,
        align: Int,
        startYInCanvas: Float,
        defaultStartX: Float,
        elementMaxWidth: Double,
        paint: Paint,
        sourceItem: TextParam,
    ): Triple<Float, Float, List<BaseElement>> {
        val result = mutableListOf<BaseElement>()
        var tempStartYInCanvas = startYInCanvas
        val contentChar = text.split(" ")
        val isEnglishContent = contentChar.size != 1
        val char = if (isEnglishContent) contentChar else text.toCharArray().toList()
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
                val startX = when (align) {
                    Constant.Companion.Align.ALIGN_END -> defaultStartX.plus(
                        elementMaxWidth.minus(
                            width
                        )
                    ).toFloat()

                    Constant.Companion.Align.ALIGN_CENTER -> elementMaxWidth.div(2)
                        .minus(width.div(2)).toFloat()

                    else -> defaultStartX
                }
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
                    tempStartYInCanvas = tempStartYInCanvas.plus(10)
                    tempCharBuilder.append(value)
                    charBuilder.append(value)
                    if (isEnglishContent) {
                        tempCharBuilder.append(" ")
                        charBuilder.append(" ")
                    }
                }
            } else {
                charBuilder.append(value)
                if (isEnglishContent) {
                    charBuilder.append(" ")
                }
            }
        }

        if (tempCharBuilder.isNotEmpty()) {
            //一般只有一个
            val resultFinal = convertEnterLineElement(
                tempCharBuilder.toString(),
                align, tempStartYInCanvas, defaultStartX, elementMaxWidth, paint, sourceItem
            )
            tempStartYInCanvas += resultFinal.second
            sumItemY += resultFinal.second
            result.addAll(resultFinal.third)
        }
        return Triple(tempStartYInCanvas, sumItemY, result)
    }

    /**
     * 处理混排业务元素
     * @param item 元素
     * @param paint 画笔
     * @param maxWidth 画布最大宽度
     * @param defaultStartX X的起始位置
     * @param startYInCanvas Y的起始位置
     * @return 元素宽度，最新的Y值，元素高度，元素转换结果
     */
    private fun handleMultiParamItem(
        item: BaseParam,
        paint: Paint,
        maxWidth: Float,
        defaultStartX: Float,
        startYInCanvas: Float,
    ): DataEntity4<Float, Float, Float, List<BaseElement>> {
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
                return DataEntity4(
                    itemWidth.toFloat(),
                    itemHeight.first,
                    itemHeight.second,
                    itemHeight.third
                )
            }

            is GraphicsParam -> {

                return DataEntity4(
                    item.width.toFloat(),
                    startYInCanvas.plus(item.height.toFloat()),
                    item.height.toFloat(),
                    mutableListOf<BaseElement>().apply {
                        add(
                            GraphicsElement(item.bitmapData).apply {
                                setStartXValue(defaultStartX)
                                setEndXValue(defaultStartX.plus(item.width))
                                setStartYValue(startYInCanvas)
                                setEndYValue(startYInCanvas.plus(item.height))
                                setLineSpace(item.perLineSpace)
                            }
                        )
                    })
            }

            else -> {
                //暂未支持的类型
                return DataEntity4(defaultStartX, 0f, 0f, emptyList<BaseElement>())
            }
        }
    }

    /**
     * Bitmap转换成Bitmap数组
     * @param bitmap 图像
     * @return Bitmap数组
     */
    private fun compressBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val ops = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ops)
        return ops.toByteArray()
    }


    /**
     * 从一堆数中取最大值
     * @param  values 一堆数
     * @return 最大值
     */
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
     * @return 宽、高
     */
    private fun measureText(paint: Paint, text: String): Pair<Int, Int> {
        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)
        return Pair(rect.width(), rect.height())
    }

}