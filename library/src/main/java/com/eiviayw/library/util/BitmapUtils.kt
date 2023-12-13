package com.eiviayw.library.util

import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.ByteArrayOutputStream

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-12-13 21:43
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
class BitmapUtils private constructor() {
    companion object {
        @Volatile
        private var instance: BitmapUtils? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: BitmapUtils().also { instance = it }
            }
    }

    /**
     * Bitmap 缩放
     * @param bitmap 需要缩放的Bitmap
     * @param newWidth 新的宽度
     * @param newHeight 新的高度
     * @return 缩放后的Bitmap
     */
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

    /**
     * Bitmap转换成Bitmap数组
     * @param bitmap 图像
     * @return Bitmap数组
     */
    fun compressBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val ops = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ops)
        return ops.toByteArray()
    }
}