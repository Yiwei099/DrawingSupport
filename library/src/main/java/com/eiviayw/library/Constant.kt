package com.eiviayw.library

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