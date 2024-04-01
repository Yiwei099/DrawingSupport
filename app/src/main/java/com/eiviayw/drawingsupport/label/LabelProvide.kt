package com.eiviayw.drawingsupport.label

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
import com.eiviayw.library.draw.BitmapOption
import com.eiviayw.library.provide.BaseProvide

/**
 * 指路：https://github.com/Yiwei099
 *
 * Created with Android Studio.
 * @Author: YYW
 * @Date: 2023-11-26 20:39
 * @Version Copyright (c) 2023, Android Engineer YYW All Rights Reserved.
 *
 * 标签数据提供者
 */
class LabelProvide : BaseProvide(BitmapOption(maxWidth = 400, maxHeight = 240)) {
    fun start(goods: Goods, bitmap: Bitmap): ByteArray {
        val params = convertDrawParam(goods, bitmap)
        return startDraw(params)
    }

    private fun convertDrawParam(goods: Goods, bitmap: Bitmap) = mutableListOf<BaseParam>().apply {
        add(
            TextParam(
                text = goods.goodsName
            ).apply {
                size = 40f
                perLineSpace = -10
            }
        )

        add(LineDashedParam())

        add(MultiElementParam(
            param1 = GraphicsParam(
                compressBitmapToByteArray(bitmap),
                bitmap.width,
                bitmap.height
            ),
            param2 = TextParam(
                text = goods.totalPrice,
                weight = 0.3
            ).apply {
                size = 30f
                typeface = Typeface.DEFAULT_BOLD
                align = Constant.Companion.Align.ALIGN_CENTER
                gravity = Constant.Companion.Gravity.CENTER
            }
        ).apply {
            perLineSpace = 40
        })
    }

    fun start(order: Order, goods: Goods): ByteArray {
        val params = convertDrawParam(order, goods)
        return startDraw(params)
    }

    private fun convertDrawParam(order: Order, goods: Goods) = mutableListOf<BaseParam>().apply {
        add(
            MultiElementParam(
                param1 = TextParam(
                    text = "#${order.tableNo}",
                    weight = 0.6,
                ),
                param2 = TextParam(
                    text = "${order.orderType}:1/1",
                    weight = 0.4
                ).apply {
                    align = Constant.Companion.Align.ALIGN_END
                }
            ).apply {
                perLineSpace = -10
            }
        )

        add(LineDashedParam().apply {
            perLineSpace = 30
            typeface = Typeface.DEFAULT_BOLD
        })

        add(
            TextParam(
                text = goods.goodsName
            ).apply {
                size = 30f
                typeface = Typeface.DEFAULT_BOLD
            }
        )

        add(
            MultiElementParam(
                TextParam(
                    "",
                    weight = 0.15
                ).apply {
                    size = 32f
                },
                TextParam(
                    "- Add Vegetables($2)( $2蔬菜)",
                    weight = 0.85
                ).apply {
                    size = 22f
                }
            )
        )

        add(
            MultiElementParam(
                TextParam(
                    "",
                    weight = 0.15
                ).apply {
                    size = 32f
                },
                TextParam(
                    "- Add Vegetables($2)( $2蔬菜)",
                    weight = 0.85
                ).apply {
                    size = 22f
                }
            )
        )
        add(
            MultiElementParam(
                TextParam(
                    "",
                    weight = 0.15
                ).apply {
                    size = 32f
                },
                TextParam(
                    "- Add Vegetables($2)( $2蔬菜)",
                    weight = 0.85
                ).apply {
                    size = 22f
                }
            )
        )

        add(LineDashedParam().apply {
            perLineSpace = 30
            typeface = Typeface.DEFAULT_BOLD
        })

        add(
            MultiElementParam(
                param1 = TextParam(
                    text = order.orderTime,
                    weight = 0.7,
                ),
                param2 = TextParam(
                    text = goods.price,
                    weight = 0.3,
                    align = Constant.Companion.Align.ALIGN_END
                )
            )
        )
    }
}