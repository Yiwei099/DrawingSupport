# DrawingSupport

#### 一个绘制 **收据/标签/小票** 图片的工具

##### 1. BitmapOption => 配置收据的*绘制标准参数*
>`val receiptOptionKey = 1`  
>`val bitmapOption = BitmapOption()`

##### 2. 创建收据的数据提供者，在进行业务数据与绘制数据的转换
>`val params = ReceiptProvide().convertDrawParam(generateOrder(), generateGoodsData())`

##### 3. 把数据提供者处理的<u>结果</u>与<u>绘制的标准参数</u>丢到<u>DrawBitmapHelper</u>中即可得到绘制的结果(Bitmap/Bitmap数组)
>`DrawBitmapHelper.addOption(receiptOptionKey,bitmapOption)`  
>`val bitmapArray = DrawBitmapHelper.convert(receiptOptionKey, params)`

##### 4.业务中使用绘制的结果
>a.转换成Bitmap显示/预览  
>b.存储或发送打印

效果预览 *具体使用细节请查阅 **MainActivity.kt***
![Image Text](https://github.com/Yiwei099/DrawingSupport/blob/master/app/src/main/res/drawable/receipt.png)

### Drawing Support by receipt
