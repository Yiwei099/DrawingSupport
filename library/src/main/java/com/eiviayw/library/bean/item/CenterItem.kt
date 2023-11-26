package com.eiviayw.library.bean.item

import android.graphics.Typeface
import com.eiviayw.library.bean.BaseSourceData

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-25 21:51
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
class CenterItem(
    val mID:String,
    val text:String,
    val mTextSize: Int,
    val mTextTypeFace: Typeface = Typeface.DEFAULT,
) : BaseSourceData(mID){
}