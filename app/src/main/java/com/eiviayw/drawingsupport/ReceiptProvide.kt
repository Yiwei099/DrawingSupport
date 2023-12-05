package com.eiviayw.drawingsupport

import android.graphics.Typeface
import com.eiviayw.drawingsupport.bean.Goods
import com.eiviayw.drawingsupport.bean.Order
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.LineDashedParam
import com.eiviayw.library.bean.param.TextParam
import com.eiviayw.library.provide.BaseProvide

class ReceiptProvide:BaseProvide(){
    override fun generateDrawParam(): List<BaseParam> {
        return super.generateDrawParam()
    }

    fun convertDrawParam(order: Order,goodsData:List<Goods>) = mutableListOf<BaseParam>().apply {
        addAll(convertOrderHeader(order))
        addAll(convertOrderGoods(goodsData))
        addAll(convertOrderFooter(order))
    }

    private fun convertOrderHeader(order: Order) = mutableListOf<BaseParam>().apply {
        add(
            TextParam(
                firstText = "Tax Invoice",
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        add(
            TextParam(
                firstText = order.shopName,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        add(
            TextParam(
                firstText = order.shopAddress,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        add(
            TextParam(
                firstText = order.shopContact,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
            }
        )

        add(
            TextParam(
                firstText = "Order#:${order.tableNo}",
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        add(
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

        add(
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

        add(
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

        add(LineDashedParam())

        add(
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

        add(LineDashedParam())
    }

    private fun convertOrderGoods(goodsData: List<Goods>) = mutableListOf<BaseParam>().apply {
        goodsData.forEach {
            add(
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

            add(
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
    }

    private fun convertOrderFooter(order: Order) = mutableListOf<BaseParam>().apply {
        add(LineDashedParam())
        add(
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

        add(
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

        add(
            TextParam(
                firstText = "Items",
                firstTextAlign = Constant.Companion.Align.ALIGN_START,
                firstWeight = 0.7,
                secondTextAlign = Constant.Companion.Align.ALIGN_END,
                secondText = order.qua,
                secondWeight = 0.3
            ).apply {
                setTextSize(26f)
            }
        )

        add(
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

        add(
            TextParam(
                firstText = order.orderType,
                firstTextAlign = Constant.Companion.Align.ALIGN_CENTER,
                firstWeight = 1.0,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )
    }
}