package com.eiviayw.drawingsupport

import android.graphics.Bitmap
import android.graphics.Matrix


class Util private constructor() {
    companion object {
        @Volatile
        private var instance: Util? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Util().also { instance = it }
            }
    }

    fun zoomBitmap(
        bitmap: Bitmap, newWidth: Double,
        newHeight: Double
    ): Bitmap? {
        // 获取这个图片的宽和高
        val width = bitmap.width.toFloat()
        val height = bitmap.height.toFloat()
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        // 计算宽高缩放率
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width.toInt(), height.toInt(), matrix, true)
    }

}