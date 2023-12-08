# DrawingSupport

### 👍👍一个绘制 **收据/标签/小票** 图片的工具

#### **🌟功能支持**
>1.自动测量文本宽度，并且自适应换行  
>2.自定义最大宽度限制  
>3.自定义字体大小  
>4.自定义横向/纵向对齐方式  
>5.图文混排  
>6.自定义行距  

#### **🌟获取方式**
>1⃣️ Add it in your root build.gradle at the end of repositories:   
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
  }
}
```
>2⃣️ Add the dependency:  
```
implementation 'com.github.Yiwei099:DrawingSupport:1.0.0'
```  

#### **🌟使用步骤**
>1⃣️ BitmapOption => 配置收据的*绘制标准参数*
```
val receiptOptionKey = "ReceiptProvide"  
val bitmapOption = BitmapOption()
```

>2⃣️ 创建收据的数据提供者，在进行业务数据与绘制数据的转换
```
val params = ReceiptProvide().convertDrawParam(generateOrder(), generateGoodsData())

//数据转换部分实例  
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
                    gravity = Constant.Companion.Gravity.CENTER
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
```

>3⃣️ 把数据提供者处理的<u>结果</u>与<u>绘制的标准参数</u>丢到<u>DrawBitmapHelper</u>中即可得到绘制的结果(Bitmap数组)
```
DrawBitmapHelper.addOption(receiptOptionKey,bitmapOption)
val bitmapArray = DrawBitmapHelper.convert(receiptOptionKey, params)
```

##### 业务中使用绘制的结果
>a.转换成Bitmap显示/预览  
>b.存储或发送打印  

效果预览 *具体使用细节请查阅 **MainActivity.kt***  
![Image Text](https://github.com/Yiwei099/DrawingSupport/blob/master/app/src/main/res/drawable/receipt.png)

### Drawing Support by receipt
