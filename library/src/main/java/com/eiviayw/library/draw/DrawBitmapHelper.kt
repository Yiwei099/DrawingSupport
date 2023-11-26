package com.yyw.drawsupport.draw

import android.graphics.Paint
import com.yyw.drawsupport.bean.BaseSourceData
import com.yyw.drawsupport.util.SerializationUtils

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-21 21:43
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
object DrawBitmapHelper {
    private val optionMap: MutableMap<Int, BitmapOption> = mutableMapOf()

    fun convert(sourceData: List<BaseSourceData>, bitmapType: Int) {
        val bitmapOption = optionMap[bitmapType] ?: BitmapOption()
        val mainPaint = Paint().apply {
            //是否开启抗锯齿
            isAntiAlias = bitmapOption.antiAlias
            isFilterBitmap = bitmapOption.antiAlias
        }
        //复制数据源，防止对象引用
        val tempSource = SerializationUtils.copy(sourceData).toMutableList()
    }

    //<editor-fold desc="参数管理">
    fun addOption(key: Int, value: BitmapOption): DrawBitmapHelper {
        optionMap[key] = value
        return this
    }

    fun addOptions(options: Map<Int, BitmapOption>): DrawBitmapHelper {
        optionMap.putAll(options)
        return this
    }

    fun onDestroy() {
        optionMap.clear()
    }
    //</editor-fold desc="参数管理">
}