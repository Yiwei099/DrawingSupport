package com.eiviayw.drawingsupport

import android.graphics.Bitmap
import android.graphics.Typeface
import com.eiviayw.drawingsupport.bean.Goods
import com.eiviayw.drawingsupport.bean.Order
import com.eiviayw.library.Constant
import com.eiviayw.library.bean.param.BaseParam
import com.eiviayw.library.bean.param.GraphicsParam
import com.eiviayw.library.bean.param.LineDashedParam
import com.eiviayw.library.bean.param.MultiElementParam
import com.eiviayw.library.bean.param.TextParam
import com.eiviayw.library.provide.BaseProvide
import java.io.ByteArrayOutputStream

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

    fun convertDrawParam(order: Order,goodsData:List<Goods>,bitmap: Bitmap) = mutableListOf<BaseParam>().apply {
        addAll(convertOrderHeader(order))
        addAll(convertOrderGoods(goodsData))
        addAll(convertOrderFooter(order))
        add(GraphicsParam(
            compressBitmapToByteArray(bitmap),
            bitmap.width,
            bitmap.height
        ).apply {
            perLineSpace = 30
        })
        add(
            TextParam(
                text = "00002",
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                size = 26f
                typeface = Typeface.DEFAULT_BOLD
            }
        )
    }

    private fun convertOrderHeader(order: Order) = mutableListOf<BaseParam>().apply {
        add(
            TextParam(
                text = "Tax Invoice",
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                size = 26f
                typeface = Typeface.DEFAULT_BOLD
            }
        )

        add(
            TextParam(
                text = order.shopName,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = order.shopAddress,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                size = 26f
                typeface = Typeface.DEFAULT_BOLD
            }
        )

        add(
            TextParam(
                text = order.shopContact,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                size = 26f
                typeface = Typeface.DEFAULT_BOLD
            }
        )

        add(
            TextParam(
                text = "Order#:${order.tableNo}",
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                size = 26f
                typeface = Typeface.DEFAULT_BOLD
            }
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Served by",
                    weight = 0.5,
                ).apply {
                    size = 26f
                },
                param2 = TextParam(
                    text = order.cashierID,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    size = 26f
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Order Date",
                    weight = 0.3,
                ).apply {
                    size = 26f
                },
                param2 = TextParam(
                    text = order.orderTime,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.7,
                ).apply {
                    size = 26f
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Transaction#",
                    weight = 0.4,
                ).apply {
                    size = 26f
                },
                param2 = TextParam(
                    text = order.orderNo,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.6,
                ).apply {
                    perLineSpace = 10
                    size = 26f
                }
            )
        )

        add(LineDashedParam().apply {
            perLineSpace = 30
        })

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Name",
                    weight = 0.5,
                ).apply {
                    size = 26f
                },
                param2 = TextParam(
                    text = "AMT",
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    size = 26f
                }
            ).apply {
                perLineSpace = 0
            }
        )

        add(LineDashedParam().apply {
            perLineSpace = 30
        })
    }

    private fun convertOrderGoods(goodsData: List<Goods>) = mutableListOf<BaseParam>().apply {
        goodsData.forEachIndexed { index, it ->
            add(
                MultiElementParam(
                    param1 = TextParam(
                        text = "${index.plus(1)}.${it.goodsName}",
                        weight = 0.7,
                    ).apply {
                        size = 26f
                        typeface = Typeface.DEFAULT_BOLD
                    },
                    param2 = TextParam(
                        text = it.totalPrice,
                        align = Constant.Companion.Align.ALIGN_END,
                        weight = 0.3,
                    ).apply {
                        size = 26f
                        typeface = Typeface.DEFAULT_BOLD
                    }
                ).apply {
                    perLineSpace = 8
                }
            )

            add(
                TextParam(
                    text = "${it.qua} x ${it.price}",
                    align = Constant.Companion.Align.ALIGN_START,
                    weight = 0.7
                ).apply {
                   perLineSpace =  if (index == goodsData.size -1) 0 else 18
                    size = 26f
                    typeface = Typeface.DEFAULT_BOLD
                }
            )
        }
    }

    private fun convertOrderFooter(order: Order) = mutableListOf<BaseParam>().apply {
        add(LineDashedParam().apply {
            perLineSpace = 30
        })

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Count",
                    weight = 0.5,
                ).apply {
                    size = 26f
                },
                param2 = TextParam(
                    text = order.qua,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    size = 26f
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Subtotal",
                    weight = 0.5,
                ).apply {
                    size = 26f
                },
                param2 = TextParam(
                    text = order.subTotal,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    size = 26f
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Total",
                    weight = 0.5,
                ).apply {
                    size = 26f
                    typeface = Typeface.DEFAULT_BOLD
                },
                param2 = TextParam(
                    text = order.total,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    size = 26f
                    typeface = Typeface.DEFAULT_BOLD
                }
            )
        )

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "Cash payment",
                    weight = 0.5,
                ).apply {
                    size = 26f
                },
                param2 = TextParam(
                    text = order.total,
                    align = Constant.Companion.Align.ALIGN_END,
                    weight = 0.5,
                ).apply {
                    size = 26f
                }
            )
        )

        add(
            TextParam(
                text = order.orderType,
                align = Constant.Companion.Align.ALIGN_CENTER,
            ).apply {
                size = 26f
                typeface = Typeface.DEFAULT_BOLD
            }
        )
    }

    private fun compressBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val ops = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ops)
        return ops.toByteArray()
    }
}