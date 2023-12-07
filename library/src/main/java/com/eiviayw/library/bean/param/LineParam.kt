package com.eiviayw.library.bean.param

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:49
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 * 实线
 */
class LineParam(
    val weight:Double = 1.0
):BaseParam(){
    fun setLineSpace(height:Int){
        perLineSpace = height
    }
}