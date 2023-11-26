package com.eiviayw.library.bean

import com.eiviayw.library.bean.item.CenterItem
import com.eiviayw.library.bean.item.EndItem
import com.eiviayw.library.bean.item.StartItem


/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-21 21:29
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 * 分散对齐的内容Item(目前最多支持3个，即：左-中-右)
 * 可以单独设置每个内容的字号与样式
 * startTextWeight + centerTextWeight + endTextWeight = 1
 */
data class DistributedSourceData(
    val mID: String,
    val startItem: StartItem,
    val centerItem: CenterItem,
    val endItem: EndItem,
    val startTextWeight: Float,//左边内容宽度比重
    val centerTextWeight: Float,//中间内容宽度比重
    val endTextWeight: Float,//右边内容宽度比重,
) : BaseSourceData(mID)