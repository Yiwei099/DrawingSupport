package com.eiviayw.drawingsupport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.LineDashedParam
import com.eiviayw.library.bean.param.TextParam
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
            TextParam(
                firstText = "Tax Invoice",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            TextParam(
                firstText = "Gomenu linckin",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "深圳",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "020-10086",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "GST:020-10010",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Order#:J-1",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            TextParam(
                firstText = "Served by",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.5,
                secondText = "Master",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.5,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Order Date",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.3,
                secondText = "22/11/2023 15:10",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.7
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Transaction#",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.4,
                secondText = "RO2023112214162097857023-012345678中哈哈是的cdefghijklmnopqrstuvwxyz",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.6
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(LineDashedParam())

        result.add(
            TextParam(
                firstText = "Name",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondText = "AMT",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(LineDashedParam())

        result.add(
            TextParam(
                firstText = "生椰拿铁",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondText = "$9.9",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )
        result.add(
            TextParam(
                firstText = "1 x 9.9",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )
        result.add(
            TextParam(
                firstText = "Test printing ultra long text with automatic line wrapping",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondText = "$9.9",
                secondTextAlign = Constant.ALIGN_END,
                secondWeight = 0.3,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )
        result.add(
            TextParam(
                firstText = "1 x 9.9",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )
        result.add(LineDashedParam())
        result.add(
            TextParam(
                firstText = "Subtotal",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondTextAlign = Constant.ALIGN_END,
                secondText = "$19.8",
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Total",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondTextAlign = Constant.ALIGN_END,
                secondText = "$19.8",
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            TextParam(
                firstText = "Cash payment",
                firstTextAlign = Constant.ALIGN_START,
                firstWeight = 0.7,
                secondTextAlign = Constant.ALIGN_END,
                secondText = "$19.8",
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Dine in",
                firstTextAlign = Constant.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
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