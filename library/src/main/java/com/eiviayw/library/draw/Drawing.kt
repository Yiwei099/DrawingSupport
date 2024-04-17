package com.eiviayw.library.draw

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.eiviayw.library.bean.element.GraphicsElement
import com.eiviayw.library.bean.element.LineDashedElement
import com.eiviayw.library.bean.element.LineElement
import com.eiviayw.library.bean.element.TextElement


/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-25 22:17
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
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
     * 绘制实线
     * @param element 元素块数据
     * @param canvas 画布
     * @param paint 画笔
     */
    fun drawLine(
        element: LineElement,
        canvas: Canvas,
        paint: Paint
    ) {
        canvas.drawLine(element.startX, element.baseY, element.endX, element.baseY, paint)
    }

    /**
     * 绘制虚线
     * @param element 元素块数据
     * @param canvas 画布
     * @param paint 画笔
     */
    fun drawDashedLine(
        element: LineDashedElement,
        canvas: Canvas, paint: Paint
    ) {
        val mPath = Path()
        mPath.moveTo(element.startX, element.baseY)
        mPath.lineTo(element.endX, element.baseY)
        mPath.close()
        canvas.drawPath(mPath, paint)
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
        canvas.drawText(textElement.text, textElement.startX, textElement.baseY, paint)
    }

    fun drawGraphics(
        graphicsElement: GraphicsElement,
        canvas: Canvas,
        paint: Paint
    ) {
        val bitmap = BitmapFactory.decodeByteArray(
            graphicsElement.bitmapData,
            0,
            graphicsElement.bitmapData.size
        )
        canvas.drawBitmap(bitmap, graphicsElement.startX, graphicsElement.baseY.minus(bitmap.height), paint)
        bitmap.recycle()
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