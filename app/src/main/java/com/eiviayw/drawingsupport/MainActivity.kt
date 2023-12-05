package com.eiviayw.drawingsupport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eiviayw.drawingsupport.bean.Goods
import com.eiviayw.drawingsupport.bean.Order
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.LineDashedParam
import com.eiviayw.library.bean.param.TextParam
import com.eiviayw.library.draw.BitmapOption
import com.eiviayw.library.draw.DrawBitmapHelper

class MainActivity : AppCompatActivity() {
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DrawBitmapHelper.addOption(1, BitmapOption())
        val receiptProvide = ReceiptProvide()

        val im = findViewById<ImageView>(R.id.imResult)

        findViewById<TextView>(R.id.tvRefresh).setOnClickListener {
            recycleBitmap()
            val params =
                receiptProvide.convertDrawParam(generateOrder(), generateGoodsData())
            val bitmapArray = DrawBitmapHelper.convert(1, params)
            bitmap = BitmapFactory.decodeByteArray(
                bitmapArray,
                0,
                bitmapArray.size
            )
            im.setImageBitmap(bitmap)
        }
    }

    private fun generateOrder() = Order(
        orderType = "Dine in",
        orderNo = "RO2023112214162097857023-012345678中哈哈是的cdefghijklmnopqrstuvwxyz",
        tableNo = "J-1",
        orderTime = "2023-12-02 17:20",
        subTotal = "$100.50",
        total = "$100.00",
        qua = "4",
        cashierID = "Yiwei099",
        shopName = "广州酒家",
        shopContact = "020-10086",
        shopAddress = "广东·广州"
    )

    private fun generateGoodsData() = mutableListOf<Goods>().apply {
        add(
            Goods(
                goodsName = "多肉葡萄",
                price = "28.00",
                qua = "2",
                totalPrice = "$56.00"
            )
        )

        add(
            Goods(
                goodsName = "多肉葡萄，芝芝芒芒，芝芝莓莓，酷黑莓桑，多肉青提，椰椰芒芒",
                price = "18.00",
                qua = "2",
                totalPrice = "$36.00"
            )
        )

        add(
            Goods(
                goodsName = "Test printing ultra long text with automatic line wrapping",
                price = "16.00",
                qua = "2",
                totalPrice = "$32.00"
            )
        )

        add(
            Goods(
                goodsName = "Mixed 中英 Chinese 超长混合 and 测试 English 效果",
                price = "28.00",
                qua = "1",
                totalPrice = "$28.00"
            )
        )

        add(
            Goods(
                goodsName = "Latte",
                price = "14.00",
                qua = "2",
                totalPrice = "$28.00"
            )
        )
    }

    private fun recycleBitmap(){
        if (bitmap != null) {
            bitmap?.recycle()
            bitmap = null
        }
    }

    override fun onDestroy() {
        DrawBitmapHelper.onDestroy()
        recycleBitmap()
        super.onDestroy()
    }
}