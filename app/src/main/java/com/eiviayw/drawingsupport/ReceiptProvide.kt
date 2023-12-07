package com.eiviayw.drawingsupport

import android.graphics.Typeface
import com.eiviayw.drawingsupport.bean.Goods
import com.eiviayw.drawingsupport.bean.Order
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.LineDashedParam
import com.eiviayw.library.bean.param.MultiElementParam
import com.eiviayw.library.bean.param.TextParam
import com.eiviayw.library.provide.BaseProvide

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 * 收据数据提供者
 */
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
                text = "Tax Invoice",
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        add(
            TextParam(
                text = order.shopName,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                setTextSize(26f)
            }
        )

        add(
            TextParam(
                text = order.shopAddress,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                setTextSize(26f)
            }
        )

        add(
            TextParam(
                text = order.shopContact,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                setTextSize(26f)
            }
        )

        add(
            TextParam(
                text = "Order#:${order.tableNo}",
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Served by",
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                },
                param2 = TextParam(
                    text = order.cashierID,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Order Date",
                    weight = 0.3,
                ).apply {
                    setTextSize(26f)
                },
                param2 = TextParam(
                    text = order.orderTime,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.7,
                ).apply {
                    setTextSize(26f)
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Transaction#",
                    weight = 0.4,
                ).apply {
                    setTextSize(26f)
                },
                param2 = TextParam(
                    text = order.orderNo,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.6,
                ).apply {
                    setTextSize(26f)
                }
            )
        )

        add(LineDashedParam())

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Name",
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                },
                param2 = TextParam(
                    text = "AMT",
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                }
            )
        )

        add(LineDashedParam())
    }

    private fun convertOrderGoods(goodsData: List<Goods>) = mutableListOf<BaseParam>().apply {
        goodsData.forEach {
            add(
                MultiElementParam(
                    param1 = TextParam(
                        text = it.goodsName,
                        weight = 0.7,
                    ).apply {
                        setTextSize(26f)
                        setFaceType(Typeface.DEFAULT_BOLD)
                    },
                    param2 = TextParam(
                        text = it.totalPrice,
                        align = Constant.Companion.Align.ALIGN_END,
                        weight = 0.3,
                    ).apply {
                        setTextSize(26f)
                        setFaceType(Typeface.DEFAULT_BOLD)
                    }
                )
            )

            add(
                TextParam(
                    text = "${it.qua} x ${it.price}",
                    align = Constant.Companion.Align.ALIGN_START,
                    weight = 0.7
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
            MultiElementParam(
                param1 = TextParam(
                    text = "Subtotal",
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                },
                param2 = TextParam(
                    text = order.subTotal,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Total",
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                    setFaceType(Typeface.DEFAULT_BOLD)
                },
                param2 = TextParam(
                    text = order.total,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                    setFaceType(Typeface.DEFAULT_BOLD)
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Items",
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                },
                param2 = TextParam(
                    text = order.qua,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Cash payment",
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                },
                param2 = TextParam(
                    text = order.total,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    setTextSize(26f)
                }
            )
        )

        add(
            TextParam(
                text = order.orderType,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                setTextSize(26f)
                setFaceType(Typeface.DEFAULT_BOLD)
            }
        )
    }
}