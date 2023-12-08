package com.eiviayw.library.provide

import android.graphics.Bitmap
import com.eiviayw.library.bean.param.BaseParam
import java.io.ByteArrayOutputStream

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-25 22:17
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
open class BaseProvide{
    open fun generateDrawParam():List<BaseParam>{
        return emptyList()
    }

    protected fun compressBitmapToByteArray(bitmap: Bitmap, quality: Int = 100): ByteArray {
        val ops = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, ops)
        return ops.toByteArray()
    }
}