package com.yyw.drawsupport.util

import android.util.Log
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-09-25 22:31
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 */
object SerializationUtils {

    fun <T>copy(src:List<T>):List<T>{
        val byteOut: ByteArrayOutputStream?
        val out: ObjectOutputStream?
        val byteIn: ByteArrayInputStream?
        val inStream: ObjectInputStream?
        try {
            byteOut = ByteArrayOutputStream()
            out = ObjectOutputStream(byteOut)
            out.writeObject(src)
            byteIn = ByteArrayInputStream(byteOut.toByteArray())
            inStream = ObjectInputStream(byteIn)
            return (inStream.readObject() as List<T>)
        } catch (ex: Exception) {
            Log.e("深拷贝异常", ex.toString())
        } finally {
        }
        return emptyList()
    }
}