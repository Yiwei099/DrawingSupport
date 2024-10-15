package com.eiviayw.drawingsupport.bean

data class ParamItem(
    val id:Int = 0,
    val name:String = ""
){
    companion object{
        const val TEXT = 0
        const val GRAPHICS = 1
        const val LINE = 2
        const val DASHED_LINE = 3
        const val MULTI = 4
    }
}
