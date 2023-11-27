package com.eiviayw.drawingsupport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.element.LineElement
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.LineParam
import com.eiviayw.library.bean.param.SourceParam
import com.eiviayw.library.draw.BitmapOption
import com.eiviayw.library.draw.DrawBitmapHelper

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val im = findViewById<ImageView>(R.id.imResult)

        findViewById<TextView>(R.id.tvRefresh).setOnClickListener {
            val bitmap = libraryTest()
            im.setImageBitmap(bitmap)
        }
    }


    private fun libraryTest(): Bitmap {
        val result = mutableListOf<BaseParam>()
        result.add(
            SourceParam(
                firstText = "Tax Invoice",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            SourceParam(
                firstText = "Gomenu linckin",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            SourceParam(
                firstText = "深圳",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            SourceParam(
                firstText = "020-10086",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            SourceParam(
                firstText = "GST:020-10010",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            SourceParam(
                firstText = "Order#:J-1",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            SourceParam(
                firstText = "Served by",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.5,
                secondText = "Master",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.5,
                thirdWeight = 0.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            SourceParam(
                firstText = "Order Date",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.3,
                secondText = "22/11/2023 15:10",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.7,
                thirdWeight = 0.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            SourceParam(
                firstText = "Transaction#",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.4,
                secondText = "RO2023112214162097857023-0123456789abcdefghijklmnopqrstuvwxyz",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.6,
                thirdWeight = 0.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(LineParam())

        result.add(
            SourceParam(
                firstText = "Name",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondText = "AMT",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.3,
                thirdWeight = 0.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(LineParam())

        result.add(
            SourceParam(
                firstText = "生椰拿铁",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondText = "$9.9",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.3,
                thirdWeight = 0.0,
            ).apply {
                setTextSize(26f)
            }
        )

        DrawBitmapHelper.addOption(1, BitmapOption())
        val bitmapArray = DrawBitmapHelper.convert(1, result)
        return BitmapFactory.decodeByteArray(
            bitmapArray,
            0,
            bitmapArray.size
        )
    }

    override fun onDestroy() {
        DrawBitmapHelper.onDestroy()
        super.onDestroy()
    }
}