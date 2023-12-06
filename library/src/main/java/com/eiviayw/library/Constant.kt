package com.eiviayw.library

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-12-04 20:55
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
class Constant private constructor() {
    companion object {
        @Volatile
        private var instance: Constant? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Constant().also { instance = it }
            }

        interface Align{
            companion object{
                /**
                 * 开始对齐
                 */
                const val ALIGN_START = 0
                /**
                 * 居中对齐
                 */
                const val ALIGN_CENTER = 1
                /**
                 * 结束对齐
                 */
                const val ALIGN_END = 2
            }
        }
    }
}