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
class LabelProvide(
    private var bitmapOption: BitmapOption = BitmapOption(maxWidth = 400, maxHeight = 240, followEffectItem = true)
) : BaseProvide(bitmapOption) {

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

    fun start():ByteArray {
        val params = convertDrawParam()
//        val params = covertTestDrawParam()
        return startDraw(params)
    }

    private fun convertDrawParam() = mutableListOf<BaseParam>().apply {
        add(
            TextParam(
                text = "加拿大鹅旗舰店",
                align = Constant.Companion.Align.ALIGN_CENTER
            )
        )
        add(
            TextParam(
                text = "名称：羽绒服"
            )
        )
//        add(
//            TextParam(
//                text = "颜色：黑色"
//            )
//        )
//        add(
//            TextParam(
//                text = "尺寸：L"
//            )
//        )
//        add(
//            TextParam(
//                text = "品牌：加拿大鹅"
//            )
//        )
//        add(
//            TextParam(
//                text = "成分：鹅绒"
//            )
//        )
//        add(
//            TextParam(
//                text = "质量等级：上等"
//            )
//        )
//
//        add(
//            TextParam(
//                text = "零售价：$40.88"
//            )
//        )
//        add(
//            TextParam(
//                text = "折后价：$30.50"
//            )
//        )

    }

    private fun covertTestDrawParam() = mutableListOf<BaseParam>().apply {
        add(
            TextParam(
                text = "李宁"
            ).apply {
                size = 28f
                typeface = Typeface.DEFAULT_BOLD
                align = Constant.Companion.Align.ALIGN_CENTER
            }
        )

        add(
            TextParam(
                text = "毛衣"
            ).apply {
                size = 26f
                typeface = Typeface.DEFAULT
            }
        )

        add(
            TextParam(
                text = "尺码：S"
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = "颜色：黑色"
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = "零售价：100"
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = "折后价：100"
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = "款号：001"
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = "品牌：李宁"
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = "产品标准：GB/T22-212321321312"
            ).apply {
                size = 26f
            }
        )

        add(
            TextParam(
                text = "质量等级：优"
            ).apply {
                size = 26f
            }
        )
    }
}