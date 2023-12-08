package com.eiviayw.drawingsupport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.eiviayw.drawingsupport.bean.Goods
import com.eiviayw.drawingsupport.bean.Order
import com.eiviayw.drawingsupport.label.LabelProvide
import com.eiviayw.drawingsupport.receipt.ReceiptProvide
import com.eiviayw.library.draw.BitmapOption
import com.eiviayw.library.draw.DrawBitmapHelper

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 * 元素块基类
 */
class MainActivity : AppCompatActivity() {
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DrawBitmapHelper.addOption(ReceiptProvide.KEY, BitmapOption())
        val receiptProvide = ReceiptProvide()
        DrawBitmapHelper.addOption(LabelProvide.KEY,BitmapOption())
        val labelProvide = LabelProvide()

        val im = findViewById<ImageView>(R.id.imResult)

        findViewById<Button>(R.id.tvRefresh).setOnClickListener {
            recycleBitmap()
            val bitmapCode = BitmapFactory.decodeResource(this.resources, R.drawable.barcode)
            val params =
                receiptProvide.convertDrawParam(generateOrder(), generateGoodsData(),bitmapCode)
            bitmapCode.recycle()
            val bitmapArray = DrawBitmapHelper.convert(ReceiptProvide.KEY, params)
            bitmap = BitmapFactory.decodeByteArray(
                bitmapArray,
                0,
                bitmapArray.size
            )
            im.setImageBitmap(bitmap)
        }

        findViewById<Button>(R.id.tvRefreshLabel).setOnClickListener{
            recycleBitmap()
            val bitmapCode = BitmapFactory.decodeResource(this.resources, R.drawable.barcode)
            val params =
                labelProvide.convertDrawParam(Goods(),bitmapCode)
            bitmapCode.recycle()
            val bitmapArray = DrawBitmapHelper.convert(LabelProvide.KEY, params)
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