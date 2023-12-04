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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val im = findViewById<ImageView>(R.id.imResult)

        findViewById<TextView>(R.id.tvRefresh).setOnClickListener {
            val bitmap = libraryTest(generateOrder(), generateGoodsData())
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
        shopName = "Hey Tea",
        shopContact = "020-10086",
        shopAddress = "广东·广州"
    )

    private fun generateGoodsData() = mutableListOf<Goods>().apply {
        add(
            Goods(
                goodsName = "多肉葡萄",
                price = "$28.00",
                qua = "2",
                totalPrice = "$56.00"
            )
        )

        add(
            Goods(
                goodsName = "多肉葡萄，芝芝芒芒，芝芝莓莓，酷黑莓桑，多肉青提，椰椰芒芒",
                price = "$18.00",
                qua = "2",
                totalPrice = "$36.00"
            )
        )

        add(
            Goods(
                goodsName = "Test printing ultra long text with automatic line wrapping",
                price = "$16.00",
                qua = "2",
                totalPrice = "$32.00"
            )
        )

        add(
            Goods(
                goodsName = "Mixed 中英 Chinese 超长混合 and 测试 English 效果",
                price = "$28.00",
                qua = "1",
                totalPrice = "$28.00"
            )
        )

        add(
            Goods(
                goodsName = "Latte",
                price = "$14.00",
                qua = "2",
                totalPrice = "$28.00"
            )
        )
    }

    private fun libraryTest(order: Order, goodsData: List<Goods>): Bitmap {
        val result = mutableListOf<BaseParam>()
        result.add(
            TextParam(
                firstText = "Tax Invoice",
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            TextParam(
                firstText = order.shopName,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = order.shopAddress,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = order.shopContact,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Order#:${order.tableNo}",
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            TextParam(
                firstText = "Served by",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.5,
                secondText = order.cashierID,
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondWeight = 0.5,
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Order Date",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.3,
                secondText = order.orderTime,
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondWeight = 0.7
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Transaction#",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.4,
                secondText = order.orderNo,
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondWeight = 0.6
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(LineDashedParam())

        result.add(
            TextParam(
                firstText = "Name",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.7,
                secondText = "AMT",
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(LineDashedParam())

        goodsData.forEach {
            result.add(
                TextParam(
                    firstText = it.goodsName,
                    firstTextAlign = Constant.Companion.Align.ALIGN_START,
                    firstWeight = 0.7,
                    secondText = it.totalPrice,
                    secondTextAlign = Constant.Companion.Align.ALIGN_END,
                    secondWeight = 0.3
                ).apply {
                    setTextSize(26f)
                    setFaceType(Typeface.DEFAULT_BOLD)
                }
            )

            result.add(
                TextParam(
                    firstText = "${it.qua} x ${it.price}",
                    firstTextAlign = Constant.Companion.Align.ALIGN_START,
                    firstWeight = 0.7
                ).apply {
                    setTextSize(26f)
                    setFaceType(Typeface.DEFAULT_BOLD)
                }
            )
        }
        result.add(LineDashedParam())
        result.add(
            TextParam(
                firstText = "Subtotal",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.7,
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondText = order.subTotal,
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = "Total",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.7,
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondText = order.total,
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        result.add(
            TextParam(
                firstText = "Cash payment",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.7,
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondText = order.total,
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
            }
        )

        result.add(
            TextParam(
                firstText = order.orderType,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
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