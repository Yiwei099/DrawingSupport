package com.eiviayw.library.draw

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Drawing private constructor() {
    companion object {
        @Volatile
        private var instance: Drawing? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Drawing().also { instance = it }
            }

        private val baseRect by lazy { Rect() }
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
        startX: Float,
        endX: Float,
        startY: Float,
        endY: Float,
        canvas: Canvas,
        paint: Paint
    ) {
        canvas.drawLine(startX, startY, endX, endY, paint)
    }

    /**
     * 绘制文字
     * @param text 内容
     * @param x X轴起点
     * @param y Y轴终点
     * @param canvas 画布
     * @param paint 画笔
     */
    fun drawText(
        text:String,
        x: Float,
        y: Float,
        canvas: Canvas,
        paint: Paint
    ) {
        canvas.drawText(text,x,y, paint)
    }
    //</editor-fold desc="绘制">

    //<editor-fold desc="图片与画布">

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

    //<editor-fold desc="内容参数(宽，高)">
    /**
     * 获取当前内容高度
     * @param paint 画笔
     * @param text 内容
     */
    fun getFontHeight(paint: Paint, text: String): Int {
        paint.getTextBounds(text, 0, text.length, baseRect)
        return baseRect.height()
    }

    /**
     * 获取当前内容宽度
     * @param paint 画笔
     * @param text 内容
     */
    fun getFontWidth(paint: Paint, text: String): Int {
        paint.getTextBounds(text, 0, text.length, baseRect)
        return baseRect.width()
    }

    /**
     * 获取当前内容宽度
     * @param paint 画笔
     * @param text 内容
     */
    fun measureText(paint: Paint, text: String) = paint.measureText(text)

    //</editor-fold desc="内容参数(宽，高)">
}