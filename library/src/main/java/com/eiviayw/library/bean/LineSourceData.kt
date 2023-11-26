package com.yyw.drawsupport.bean

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-21 21:40
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
data class LineSourceData(
    val mID: String,
    val lineType: Int,//虚线 or 实线
    val lineHeight: Int,//线的高度
    val bold: Boolean = false//是否加粗
) : BaseSourceData(mID)