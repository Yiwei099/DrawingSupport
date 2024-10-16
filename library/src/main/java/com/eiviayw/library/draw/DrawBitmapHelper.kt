package com.eiviayw.library.draw

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
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
import com.eiviayw.library.util.BitmapUtils
import kotlin.math.ceil

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

        val bitmap = Drawing.getInstance().createBimap(
            bitmapOption.maxWidth,
            if (bitmapOption.fixedHeight()) bitmapOption.maxHeight else result.second.plus(20),
            bitmapOption.config
        )
        val canvas = Drawing.getInstance().getNewCanvas(bitmap)

        var diffY = bitmapOption.diffContentY(result.second)

        if (bitmapOption.isGravityDistributed()){
            handleElementBaseY(bitmapOption,result.first)
            diffY = 0
        }

        result.first.forEachIndexed loop@{index, it ->
            it.baseY += diffY
            mainPaint.reset()
            if (!effectItem(bitmapOption, ceil(it.baseY).toInt())) {
                return@loop
            }
            mainPaint.flags = it.flags
            mainPaint.pathEffect = null
            mainPaint.style = it.style
            mainPaint.strokeWidth = it.strokeWidth
            mainPaint.color = Color.BLACK
            mainPaint.typeface = it.typeface
            mainPaint.textSize = it.size
            when (it) {
                is TextElement -> Drawing.getInstance()
                    .drawText(it, bitmapOption, canvas, mainPaint)

                is LineElement -> Drawing.getInstance().drawLine(it, canvas, mainPaint)
                is LineDashedElement -> {
                    val effects = DashPathEffect(floatArrayOf(it.on, it.off), 0f)
                    mainPaint.pathEffect = effects

                    Drawing.getInstance().drawDashedLine(it, canvas, mainPaint)
                }

                is GraphicsElement -> Drawing.getInstance().drawGraphics(it, canvas, mainPaint)
                else -> {
                    //未支持的类型
                }
            }
        }

        val resultArray = BitmapUtils.getInstance().compressBitmapToByteArray(bitmap)
        //释放Bitmap
        bitmap.recycle()
        return resultArray
    }

    /**
     * 业务数据源转换成绘制元素
     * @param bitmapOption 当前图像的标准参数
     * @param sourceData 业务数据源
     * @param paint 画笔
     * @return 绘制元素块集合、最终绘制位置(往上取整)
     */
    private fun convertSourceDataToElement(
        bitmapOption: BitmapOption,
        sourceData: List<BaseParam>,
        paint: Paint
    ): Pair<List<BaseElement>, Int> {
        val result = mutableListOf<BaseElement>()

        var startYInCanvas = bitmapOption.topIndentation

        val maxWidth = bitmapOption.getEffectiveWidth()
        val defaultStartX = bitmapOption.startIndentation

        var baseY = startYInCanvas

        sourceData.forEach { sourceItem ->
            paint.reset()

            paint.textSize = sourceItem.size
            paint.typeface = sourceItem.typeface
            paint.flags = sourceItem.flags
            paint.style = sourceItem.style
            paint.strokeWidth = sourceItem.strokeWidth

            when (sourceItem) {
                is MultiElementParam -> {
                    val paddingEnd = sourceItem.paddingEnd
                    var startXPosition = defaultStartX
                    //处理第一个元素
                    val firstItem = handleMultiParamItem(
                        sourceItem.param1,
                        paint,
                        maxWidth.minus(bitmapOption.startIndentation),
                        startXPosition,
                        startYInCanvas,
                        paddingEnd,
                        baseY
                    )

                    //处理第二个元素
                    startXPosition += firstItem.data1
                    val secondItem = handleMultiParamItem(
                        sourceItem.param2,
                        paint,
                        maxWidth.minus(bitmapOption.startIndentation),
                        startXPosition,
                        startYInCanvas,
                        paddingEnd,
                        baseY
                    )
                    //处理第三个元素
                    startXPosition += secondItem.data1
                    val thirdItem = handleMultiParamItem(
                        sourceItem.param3,
                        paint,
                        maxWidth.minus(bitmapOption.startIndentation),
                        startXPosition,
                        startYInCanvas,
                        paddingEnd,
                        baseY
                    )

                    //处理第四个元素
                    startXPosition += thirdItem.data1
                    val fourItem = handleMultiParamItem(
                        sourceItem.param4,
                        paint,
                        maxWidth.minus(bitmapOption.startIndentation),
                        startXPosition,
                        startYInCanvas,
                        paddingEnd,
                        baseY
                    )

                    //处理第五个元素
                    startXPosition += fourItem.data1
                    val fiveItem = handleMultiParamItem(
                        sourceItem.param5,
                        paint,
                        maxWidth.minus(bitmapOption.startIndentation),
                        startXPosition,
                        startYInCanvas,
                        paddingEnd,
                        baseY
                    )

                    //更新最新的Y值(当前混排元素中取最高的元素)
                    startYInCanvas = getMaxFromMany(
                        firstItem.data2,
                        secondItem.data2,
                        thirdItem.data2,
                        fourItem.data2,
                        fiveItem.data2
                    )

                    //获取当前混排元素中最高的Item
                    val maxItemHeight = getMaxFromMany(
                        firstItem.data3,
                        secondItem.data3,
                        thirdItem.data3,
                        fourItem.data3,
                        fiveItem.data3
                    )

                    //处理垂直对齐方式
                    when (maxItemHeight) {
                        firstItem.data3 -> {
                            //第一个元素最高
                            handleElementGravity(
                                sourceItem.param2,
                                secondItem,
                                firstItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                thirdItem,
                                firstItem
                            )

                            handleElementGravity(
                                sourceItem.param4,
                                fourItem,
                                firstItem
                            )

                            handleElementGravity(
                                sourceItem.param5,
                                fiveItem,
                                firstItem
                            )
                        }

                        secondItem.data3 -> {

                            //第二个元素最高
                            handleElementGravity(
                                sourceItem.param1,
                                firstItem,
                                secondItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                thirdItem,
                                secondItem
                            )

                            handleElementGravity(
                                sourceItem.param4,
                                fourItem,
                                secondItem
                            )

                            handleElementGravity(
                                sourceItem.param5,
                                fiveItem,
                                secondItem
                            )
                        }

                        thirdItem.data3 -> {
                            //第三个元素最高
                            handleElementGravity(
                                sourceItem.param1,
                                firstItem,
                                thirdItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                secondItem,
                                thirdItem
                            )
                            handleElementGravity(
                                sourceItem.param4,
                                fourItem,
                                thirdItem
                            )

                            handleElementGravity(
                                sourceItem.param5,
                                fiveItem,
                                thirdItem
                            )
                        }

                        fourItem.data3 -> {
                            //第三个元素最高
                            handleElementGravity(
                                sourceItem.param1,
                                firstItem,
                                fourItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                secondItem,
                                fourItem
                            )
                            handleElementGravity(
                                sourceItem.param4,
                                thirdItem,
                                fourItem
                            )

                            handleElementGravity(
                                sourceItem.param5,
                                fiveItem,
                                fourItem
                            )
                        }

                        fiveItem.data3 -> {
                            //第三个元素最高
                            handleElementGravity(
                                sourceItem.param1,
                                firstItem,
                                fiveItem
                            )
                            handleElementGravity(
                                sourceItem.param3,
                                secondItem,
                                fiveItem
                            )
                            handleElementGravity(
                                sourceItem.param4,
                                fourItem,
                                fiveItem
                            )

                            handleElementGravity(
                                sourceItem.param5,
                                thirdItem,
                                fiveItem
                            )
                        }
                    }

                    baseY = baseY.plus(maxItemHeight).plus(sourceItem.perLineSpace)
                    result.addAll(firstItem.data4)
                    result.addAll(secondItem.data4)
                    result.addAll(thirdItem.data4)
                    result.addAll(fourItem.data4)
                    result.addAll(fiveItem.data4)

                }

                is TextParam -> {
                    //填充画笔
                    val elementResult = handleTextParamToElement(
                        maxWidth.toDouble() - defaultStartX,
                        sourceItem,
                        paint,
                        sourceItem.text,
                        sourceItem.align,
                        if (sourceItem.align == Constant.Companion.Align.ALIGN_START) defaultStartX else 0f,
                        startYInCanvas, baseY
                    )
                    baseY = baseY.plus(elementResult.second).plus(sourceItem.perLineSpace)
                    startYInCanvas = elementResult.first
                    result.addAll(elementResult.third)
                }

                is LineParam -> {
                    val width = maxWidth.times(sourceItem.weight)
                    val measure = measureText(paint, "-")
                    val height = measure.second
                    baseY = baseY.plus(height)
                    result.add(
                        LineElement().apply {
                            setStartXValue(defaultStartX)
                            setEndXValue(width.toFloat())
                            setTextSize(sourceItem.size)
                            setFaceType(sourceItem.typeface)
                            setLineSpace(sourceItem.perLineSpace)
                            setBaseLine(baseY)
                            flags = sourceItem.flags
                            strokeWidth = sourceItem.strokeWidth
                            style = sourceItem.style
                        }
                    )
                    baseY = baseY.plus(sourceItem.perLineSpace)

                }

                is LineDashedParam -> {
                    paint.textSize = sourceItem.size
                    paint.typeface = sourceItem.typeface
                    val width = maxWidth.times(sourceItem.weight)
                    val measure = measureText(paint, "-")
                    val height = measure.second
                    baseY = baseY.plus(height)
                    result.add(
                        LineDashedElement(on = sourceItem.on, off = sourceItem.off).apply {
                            setStartXValue(defaultStartX)
                            setEndXValue(width.toFloat())
                            setTextSize(sourceItem.size)
                            setFaceType(sourceItem.typeface)
                            setLineSpace(sourceItem.perLineSpace)
                            setBaseLine(baseY)
                            strokeWidth = sourceItem.strokeWidth
                            style = sourceItem.style
                            flags = sourceItem.flags
                        }
                    )
                    baseY = baseY.plus(sourceItem.perLineSpace)
                }

                is GraphicsParam -> {
                    result.add(
                        GraphicsElement(sourceItem.bitmapData, align = sourceItem.align).apply {
                            setStartXValue(
                                when (align) {
                                    Constant.Companion.Align.ALIGN_START -> defaultStartX
                                    Constant.Companion.Align.ALIGN_END -> bitmapOption.maxWidth - bitmapOption.endIndentation - sourceItem.width
                                    else -> bitmapOption.getCenterX().minus(sourceItem.width.div(2))
                                }
                            )
                            setEndXValue(defaultStartX.plus(sourceItem.width))
                            setTextSize(sourceItem.size)
                            setFaceType(sourceItem.typeface)
                            setLineSpace(sourceItem.perLineSpace)
                            setBaseLine(baseY.plus(sourceItem.height))

                            strokeWidth = sourceItem.strokeWidth
                            style = sourceItem.style
                            flags = sourceItem.flags
                        }
                    )
                    baseY = baseY.plus(sourceItem.height).plus(sourceItem.perLineSpace)
                    startYInCanvas = startYInCanvas.plus(sourceItem.height)
                }

                else -> {
                    //更多类型正在推出中
                }
            }
        }

        return Pair(result, ceil(baseY).toInt())
    }

    /**
     * 处理纵向对齐方式
     * @param sourceItem 业务源元素块
     * @param handleItem 需要处理对齐的元素块
     * @param targetItem 被对齐的元素块(参照元素块)
     */
    private fun handleElementGravity(
        sourceItem: BaseParam,
        handleItem: DataEntity4<Float, Float, Float, List<BaseElement>>,
        targetItem: DataEntity4<Float, Float, Float, List<BaseElement>>
    ) {
        when (sourceItem.gravity) {
            Constant.Companion.Gravity.CENTER -> {
                //半高
                val handleHalfHeight = (handleItem.data3 - (handleItem.data4.size - 1) * 10).div(2)
                //居中基线
                val targetCenterLine = (targetItem.data4.lastOrNull()?.baseY
                    ?: 0f) - (targetItem.data3 - (targetItem.data4.size - 1) * 10).div(2)

                var handleMinBaseY = targetCenterLine - handleHalfHeight + sourceItem.perLineSpace

                for (element in handleItem.data4) {
                    element.baseY = handleMinBaseY
                    handleMinBaseY += sourceItem.perLineSpace + element.height
                }
            }

            Constant.Companion.Gravity.BOTTOM -> {
                //handleItem的最后一行对齐targetItem的最后一行，但是targetItem行数必然比handleItem行数大
                //相差行数(必然大于1)
                val diffSize = targetItem.data4.size - handleItem.data4.size
                for (index in targetItem.data4.size - 1 downTo 0) {
                    val handleIndex = index - diffSize

                    val handleItemElement = handleItem.data4[handleIndex]
                    val targetItemElement = targetItem.data4[index]
                    handleItemElement.baseY = targetItemElement.baseY
                    if (handleIndex == 0) {
                        //倒序遍历防止数组越界
                        break
                    }
                }
            }

            else -> {
                //顶部对齐时，顺序一行一行对应即可
                for (index in 0 until handleItem.data4.size) {
                    val handleItemElement = handleItem.data4[index]
                    val targetItemElement = targetItem.data4[index]
                    handleItemElement.baseY = targetItemElement.baseY
                }
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
        baseY: Float,
        paddingEnd: Int = 0
    ): Triple<Float, Float, List<BaseElement>> {
        val result = mutableListOf<BaseElement>()
        val endYInCanvas: Float
        val itemHeight: Float
        var itemBaseY = baseY
        val measure = measureText(paint, text)
        val width = measure.first
        val overWidth = width > elementMaxWidth
        if (!sourceItem.autoWrap || !overWidth) {
            //不需要换行处理
            itemHeight = measure.second.toFloat()
            endYInCanvas = startYInCanvas.plus(measure.second)

            itemBaseY = itemBaseY.plus(measure.second)

            val tempText = if (overWidth) {
                val charBuilder = StringBuilder()
                for (c in text.toCharArray()) {
                    charBuilder.append(c)
                    if (measureText(paint, charBuilder.toString()).first > elementMaxWidth) {
                        break
                    }
                }
                charBuilder.deleteAt(charBuilder.lastIndex).toString()
            } else {
                text
            }

            val tempWidth = if (overWidth) {
                measureText(paint, tempText).first.plus(paddingEnd)
            } else {
                width.plus(paddingEnd)
            }

            val startX = when (align) {
                Constant.Companion.Align.ALIGN_END -> defaultStartX.plus(
                    elementMaxWidth.minus(
                        tempWidth
                    )
                )
                    .toFloat()

                Constant.Companion.Align.ALIGN_CENTER -> elementMaxWidth.div(2)
                    .minus(tempWidth.div(2))
                    .plus(defaultStartX).toFloat()

                else -> defaultStartX
            }
            result.add(
                TextElement(
                    text = tempText,
                    align = align,
                    textWidth = tempWidth,
                    maxWidth = elementMaxWidth,
                ).apply {
                    setStartXValue(startX)
                    setEndXValue(startX.plus(tempWidth))
                    setTextSize(sourceItem.size)
                    setFaceType(sourceItem.typeface)
                    setBaseLine(itemBaseY)
                    setLineSpace(sourceItem.perLineSpace)
                    setElementHeight(ceil(itemHeight).toInt())
                    strokeWidth = sourceItem.strokeWidth
                    style = sourceItem.style
                    flags = sourceItem.flags
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
                baseY
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
        baseY: Float
    ): Triple<Float, Float, List<BaseElement>> {
        val result = mutableListOf<BaseElement>()
        var tempStartYInCanvas = startYInCanvas
        val contentChar = text.split(" ")
        val isEnglishContent = contentChar.size != 1
        val char = if (isEnglishContent) contentChar else text.toCharArray().toList()
        val charBuilder = StringBuilder()
        val tempCharBuilder = StringBuilder()
        var sumItemY = 0f
//        var textHeight = 0
        var itemBaseY = baseY
        for (index in char.indices) {
            //迭代字符
            val value = char[index]

            tempCharBuilder.append(value)
            if (isEnglishContent) {
                tempCharBuilder.append(" ")
            }

            val tempMeasure = measureText(paint, tempCharBuilder.toString())
            val tempWidth = tempMeasure.first
            //中文与数字高度不一样，换行后为纯数字会出现高度误差
//            textHeight = getMaxFromMany(textHeight.toFloat(), tempMeasure.second.toFloat()).toInt()

            val lastChar = index == char.size.minus(1)
            val fullWidth = tempWidth > elementMaxWidth
            if (fullWidth || lastChar) {
                if (!fullWidth) {
                    //没达到最大宽度，但是已是最后一个字符，需要把最后的字符添加进来，否则会漏掉
                    charBuilder.append(value)
                }
                val measure = measureText(paint, charBuilder.toString())
                val width = measure.first
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
                itemBaseY += measure.second

                result.add(
                    TextElement(
                        text = charBuilder.toString(),
                        align = align,
                        textWidth = width,
                        maxWidth = elementMaxWidth,
                    ).apply {
                        setStartXValue(startX)
                        setEndXValue(startX.plus(width))
                        setTextSize(sourceItem.size)
                        setFaceType(sourceItem.typeface)
                        setBaseLine(itemBaseY)
                        setElementHeight(tempMeasure.second)
                        strokeWidth = sourceItem.strokeWidth
                        style = sourceItem.style
                        flags = sourceItem.flags
                        setLineSpace(sourceItem.perLineSpace)
                    }
                )
                sumItemY = sumItemY.plus(tempMeasure.second)
                charBuilder.setLength(0)
                tempCharBuilder.setLength(0)
                if (fullWidth) {
                    itemBaseY += sourceItem.perLineSpace
                    tempStartYInCanvas += sourceItem.perLineSpace
                    sumItemY += sourceItem.perLineSpace
                    tempStartYInCanvas = tempStartYInCanvas.plus(tempMeasure.second)
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
                align,
                tempStartYInCanvas,
                defaultStartX,
                elementMaxWidth,
                paint,
                sourceItem,
                itemBaseY
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
        paddingEnd: Int,
        baseY: Float
    ): DataEntity4<Float, Float, Float, List<BaseElement>> {
        when (item) {
            is TextParam -> {
                //填充画笔
                paint.textSize = item.size
                paint.typeface = item.typeface

                val itemWidth = if (item.weight < 0) measureText(
                    paint,
                    item.text
                ).first.toDouble() + paddingEnd else maxWidth.times(item.weight) + paddingEnd
                val itemHeight = handleTextParamToElement(
                    itemWidth,
                    item,
                    paint,
                    item.text,
                    item.align,
                    defaultStartX,
                    startYInCanvas,
                    baseY,
                    paddingEnd = paddingEnd
                )
                Log.d(
                    "099",
                    "${item.text}；实际宽度:${itemWidth}；默认开始位置(x):${defaultStartX}；"
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
                            GraphicsElement(item.bitmapData, align = item.align).apply {
                                setStartXValue(defaultStartX)
                                setEndXValue(defaultStartX.plus(item.width))
                                setLineSpace(item.perLineSpace)
                                setBaseLine(baseY.plus(item.height))
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
     * 处理元素的Y值
     */
    private fun handleElementBaseY(
        bitmapOption: BitmapOption,
        elementList: List<BaseElement>
    ) {
        //画布最大高度
        val maxHeight = bitmapOption.maxHeight
        val itemMaxHeight = (maxHeight / elementList.size).toFloat()
        var tempSumY = 0f
        elementList.forEachIndexed { index, it ->
            val itemNewBaseY = itemMaxHeight.times(index + 1)
            it.baseY = (itemMaxHeight / 2) + (it.height / 2) + tempSumY
            tempSumY = itemNewBaseY
        }
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

    /**
     * 当前元素是否需要绘制
     * @param bitmapOption 画布参数
     * @param tempContentY 当前元素的底部Y值
     * @return 画布剩余高度不足时继续绘制 = true；当前元素底部Y值 < 画布最大高度 = true
     * true = 需要绘制；false = 无需绘制
     */
    private fun effectItem(bitmapOption: BitmapOption, tempContentY: Int): Boolean {
        if (!bitmapOption.followEffectItem) {
            return true
        }

        return !bitmapOption.fixedHeight() || bitmapOption.maxHeight > tempContentY
    }

}