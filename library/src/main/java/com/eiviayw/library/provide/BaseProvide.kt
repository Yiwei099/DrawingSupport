package com.eiviayw.library.provide

import android.graphics.Bitmap
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.draw.BitmapOption
import com.eiviayw.library.draw.DrawBitmapHelper
import java.io.ByteArrayOutputStream

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-25 22:17
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
abstract class BaseProvide(private val option:BitmapOption){

    /**
     * 开始绘制
     * @param params 业务参数
     * @return 绘制结果(bitmap数组)
     */
    protected fun startDraw(params:List<BaseParam>):ByteArray
    = DrawBitmapHelper.convert(option,params)

    /**
     * bitmap转换
     * @return bitmap数组
     */
    protected fun compressBitmapToByteArray(bitmap: Bitmap, quality: Int = 100): ByteArray {
        val ops = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, ops)
        return ops.toByteArray()
    }
}