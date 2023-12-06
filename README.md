# DrawingSupport

### 👍👍一个绘制 **收据/标签/小票** 图片的工具

#### **🌟功能支持**
>1.自动测量文本宽度，并且自适应换行  
>2.自定义最大宽度限制  
>3.自定义字体大小  
>4.多种对齐方式：左对齐/右对齐/分散对齐/居中对齐  
>5.图文混排

#### **🌟使用步骤**

##### 1. BitmapOption => 配置收据的*绘制标准参数*
>`val receiptOptionKey = 1`  
>`val bitmapOption = BitmapOption()`

##### 2. 创建收据的数据提供者，在进行业务数据与绘制数据的转换
>`val params = ReceiptProvide().convertDrawParam(generateOrder(), generateGoodsData())`
  
###### Ps：数据转换部分实例  
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


##### 3. 把数据提供者处理的<u>结果</u>与<u>绘制的标准参数</u>丢到<u>DrawBitmapHelper</u>中即可得到绘制的结果(Bitmap/Bitmap数组)
>`DrawBitmapHelper.addOption(receiptOptionKey,bitmapOption)`  
>`val bitmapArray = DrawBitmapHelper.convert(receiptOptionKey, params)`

##### 4.业务中使用绘制的结果
>a.转换成Bitmap显示/预览  
>b.存储或发送打印

效果预览 *具体使用细节请查阅 **MainActivity.kt***
![Image Text](https://github.com/Yiwei099/DrawingSupport/blob/master/app/src/main/res/drawable/receipt.png)

### Drawing Support by receipt
