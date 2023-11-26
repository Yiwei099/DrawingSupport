package com.eiviayw.library.bean

import android.graphics.Typeface

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-21 21:05
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 * 左边对齐的内容Item
 */
data class StartSourceData(
    val mID:String,
    val content:String,//内容
    val mTextSize:Int,
    val mTextTypeface:Typeface = Typeface.DEFAULT,
    val indentation:Int = 0,//缩进
): BaseSourceData(mID,mTextSize,mTextTypeface)