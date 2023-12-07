package com.eiviayw.library.bean.param

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:49
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 */
data class MultiElementParam(
    val param1:BaseParam = BaseParam(),
    val param2:BaseParam = BaseParam(),
    val param3:BaseParam = BaseParam(),
):BaseParam(){
    fun setLineSpace(height:Int){
        perLineSpace = height
    }
}