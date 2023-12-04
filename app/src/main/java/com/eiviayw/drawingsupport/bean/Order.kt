package com.eiviayw.drawingsupport.bean

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-12-04 20:52
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
data class Order(
    val orderNo:String = "",
    val tableNo:String = "",
    val orderTime:String = "",
    val subTotal:String = "",
    val total:String = "",
    val qua:String = "",
    val orderType:String = "",
    val cashierID:String = "",
    val shopName:String = "",
    val shopContact:String = "",
    val shopAddress:String = "",
)