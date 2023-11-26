package com.yyw.drawsupport.bean

import android.graphics.Typeface

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-21 21:26
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 * 右边对齐的内容Item
 */
data class EndSourceData(
    val mID:String,
    val content:String,//内容
    val mTextSize:Int,
    val mTextTypeface: Typeface = Typeface.DEFAULT,
):BaseSourceData(mID,mTextSize,mTextTypeface)