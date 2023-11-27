package com.eiviayw.library.draw

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.element.LineElement
import com.eiviayw.library.bean.element.TextElement

class Drawing private constructor() {
    companion object {
        @Volatile
        private var instance: Drawing? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Drawing().also { instance = it }
            }
    }

    //<editor-fold desc="绘制">

    /**
     * 绘制线条
     * @param startX X轴起点
     * @param endX X轴终点
     * @param startY Y轴起点
     * @param endY Y轴终点
     * @param canvas 画布
     * @param paint 画笔
     */
    fun drawLine(
        element: LineElement,
        canvas: Canvas,
        paint: Paint
    ) {
        canvas.drawLine(element.startX, element.startY, element.endX, element.endY, paint)
    }

    /**
     * 绘制文字
     * @param textElement 元素块数据
     * @param bitmapOption 画布固定参数
     * @param canvas 画布
     * @param paint 画笔
     */
    fun drawText(
        textElement: TextElement,
        bitmapOption: BitmapOption,
        canvas: Canvas,
        paint: Paint
    ) {
        when (textElement.align) {
            Constant.ALIGN_CENTER -> {
                val startX =
                    getCenterStart(bitmapOption.maxWidth).minus(textElement.textWidth.div(2))
                        .toFloat()
                canvas.drawText(textElement.text, startX, textElement.startY, paint)
            }
            else -> {
                canvas.drawText(textElement.text, textElement.startX, textElement.startY, paint)
            }
        }

    }
    //</editor-fold desc="绘制">

    //<editor-fold desc="图片与画布">

    //居中对齐
    private fun getCenterStart(width: Int) = width.div(2)

    /**
     * 创建图片
     */
    fun createBimap(width: Int, height: Int): Bitmap =
        Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    /**
     * 创建画布(背景颜色只能是白底，不设置颜色或者设置为透明，用于打印时的效果会是纯黑色)
     */
    fun getNewCanvas(bitmap: Bitmap) = Canvas(bitmap).apply { drawColor(Color.WHITE) }

    //</editor-fold desc="图片与画布">
}