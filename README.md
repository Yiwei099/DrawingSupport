# DrawingSupport(一个绘制 **收据/标签/小票** 图片的工具)

## 诞生
- 打印机品牌太多，SDK指令构造开发成本过高且文本模式打印效果不理想  
- 打印机文本语言支持不一，海外有无法打印中文的打印机；部分语言或者符号无法打印或乱码  
- 使用 View + 截图 的生成方式浪费系统资源，阻塞主线程，线程管理麻烦  
- 无论是 View + 截图 还是 SDK 指令 的方式，排版都非常困难且单一  

## 概述
>① 自动测量文本宽度，并且自适应换行  
>② 自定义最大宽度(适应不同的打印纸尺寸：58mm/80mm)  
>③ 自定义限制最大高度(适应标签纸：限制或不限制标签大小)  
>④ 自定义字体大小  
>⑤ 自定义横向/纵向对齐方式  
>⑥ 图文混排  
>⑦ 自定义行距  

## 获取依赖
### 1. Add it in your root build.gradle at the end of repositories:   
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
  }
}
```
### 2. Add the dependency:  
```
implementation 'com.github.Yiwei099:DrawingSupport:$releaseVersion'
```  

## 详情
### 1.元素
#### a. TextParam - 纯文本
```
//文本内容
val text = "DrawingSupport"
//内容字体大小
val fontSize = 26f
//文本对齐方式(左/中/右)
val align = Constant.Companion.Align.ALIGN_CENTER
//元素最大宽度
val weight = 0.5  
//文本样式(加粗/斜体/正常)
val typeface = Typeface.DEFAULT
//元素对齐方式(顶部/居中/底部)
val gravity = Constant.Companion.Gravity.TOP
//元素底部外边距
val perLineSpace = 10

//创建元素
val textItem = TextParam(text,weight,align).apply{
    size = fontSize
    typeface = typeface
    gravity = gravity
    perLineSpace = perLineSpace
}
```
#### b. LineParam - 实线
```
//元素最大宽度
val weight = 1.0
//元素底部外边距
val perLineSpace = 30

//创建元素
val lineItem = LineParam(weight).apply{
    perLineSpace = perLineSpace
}
```
#### c. LineDashedParam - 虚线
```
//元素最大宽度
val weight = 1.0
//元素底部外边距
val perLineSpace = 30

//创建元素
val lineDashedItem = LineDashedParam(weight).apply{
    perLineSpace = perLineSpace
}
```
#### d. GraphicsParam - 图像
```
//图像数据
val bitmap = Bitmap
//图像Byte数组
val bitmapData = BitmapUtils.compressBitmapToByteArray(bitmap)
//图像宽度
val width = bitmap.width
//图像高度
val height = bitmap.height

//创建元素
val graphicsItem = GraphicsParam(bitmapData,width,height).apply {
    perLineSpace = 40
}
```

#### e. MultiElementParam - 混排
```
//一行最多支持三个元素，且 param1.weight + param2.weight + param3.weight <= 1.0，只有两个 param 时会自动忽略 param3.weight
val multiPairItem = MultiElementParam(
    param1 = textItem,
    param2 = textItem,
)

//图文混排
val multiGraphicsItem = MultiElementParam(
    param1 = textItem,
    param2 = graphicsItem,
)

```

## 使用
### 1. 继承 **BaseProvide** 在内部进行 **业务数据** 与 **绘制数据** 的转换，并提供图像的标准参数 **BitmapOption**
```
class ReceiptProvide: BaseProvide(BitmapOption()) {
    //转换数据总入口
    fun start(order: Order,goodsData: List<Goods>,bitmap: Bitmap):ByteArray{
        //...2. 数据转换完毕后调用绘制函数
    }
    
    //内部转换具体函数
    private fun convertData(order: Order,goodsData: List<Goods>,bitmap: Bitmap):List<BaseParam>{
        val result = mutableListOf<BaseParam>()
        //...3.具体转换细节
        return result
    }
}
```
### 2. 数据转换完毕后调用绘制函数
```
//数据转换结果
val params = convertData(order,goodsData,bitmap)
//绘制结果数据
val byteArray = startDraw(params)
```
### 3. 具体转换细节(部分)
```
//部分细节
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

### 4. 获取绘制结果数据
```
val bitmapArray = receiptProvide.start(Order, GoodsList, bitmapCode)
```
### 5. 使用绘制的结果
#### a. 显示在UI上
```
val bitmap = BitmapUtils.byteDataToBitmap(bitmapArray)
imageView.setImageBitmap(bitmap)
```
#### b. 打印([100%契合的打印工具：PrintSupport](https://github.com/Yiwei099/PrintSupport))
```
//创建局域网打印机
val key = "192.168.100.150"
val printer = EscNetGPrinter(context, netKey) //打印Esc

//创建图片策略的打印任务
val mission = GraphicMission(bitmapArray)

//调用addMission发起打印
printer.addMission(mission)

//更多打印机的使用请查阅 PrintSupport
```
#### d. 存储
```
//...存储逻辑
```
#### c. 数据生成Bitmap后，必要时需要销毁
```
bitmap.recycle()
```

## 效果预览 
> 具体使用细节请查阅 **MainActivity.kt***  
![Image Text](https://github.com/Yiwei099/DrawingSupport/blob/master/app/src/main/res/drawable/receipt.png)

## Drawing Support by receipt(不定期更新)
