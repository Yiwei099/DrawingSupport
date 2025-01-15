# DrawingSupport (A tool for generating images of **Receipts/Labels/Tickets**)

## Origin
- There are too many printer brands, and the development cost of constructing SDK instructions is high. Text mode printing also yields unsatisfactory results.
- Printers have varying text language support; some overseas printers cannot print Chinese characters, and certain languages or symbols may not print correctly or appear garbled.
- Using View + Screenshot to generate images wastes system resources, blocks the main thread, and complicates thread management.
- Layout is very difficult and limited whether using View + Screenshot or SDK instructions.

## Overview
> ① Automatically measure text width and wrap lines adaptively.
> ② Customize maximum width (to fit different paper sizes: 58mm/80mm).
> ③ Customize maximum height (to fit label paper: limit or no limit on label size).
> ④ Customize font size.
> ⑤ Customize horizontal/vertical alignment.
> ⑥ Mixed text and image layout.
> ⑦ Customize line spacing.

## Getting Started
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

## Details
### 1. Image Configuration
#### a. BitmapOption - Image Configuration
```
BitmapOption(
    maxWidth: Int = 576,//画布宽度
    topIndentation: Float = 40f,//顶部方向边距
    startIndentation: Float = 20f,//开始方向边距
    endIndentation: Float = 20f,//结束方向边距
    bottomBlankHeight: Int = 10,//底部留白
    antiAlias: Boolean = false,//开启抗锯齿：true-开启，false-关闭
    maxHeight:Int = 0,//画布高度
    gravity:Int = Constant.Companion.Gravity.TOP,//内容对齐方式：顶部/居中/底部，默认顶部 （只适用于固定画布高度的场景下）
    followEffectItem:Boolean = false,//使用于固定画布高度的场景下：true - 画布剩余高度不足时终止绘制；
    config:Bitmap.Config = Bitmap.Config.RGB_565,//默认RGB_565
)
```


### 2. Elements
#### a. TextParam - Plain Text
```
// Text content 
val text = "DrawingSupport"
// Font size 
val fontSize = 26f 
// Text alignment (left/center/right) 
val align = Constant.Companion.Align.ALIGN_CENTER 
// Element maximum width 
val weight = 0.5
// Text style (bold/italic/normal) 
val typeface = Typeface.DEFAULT 
// Element vertical alignment (top/center/bottom) 
val gravity = Constant.Companion.Gravity.TOP 
// Element bottom margin 
val perLineSpace = 10 
// Paint style 
var style: Paint.Style? = Paint.Style.FILL, 
// Stroke width 
var strokeWidth: Float = 0f, 
// Flags 
var flags: Int = Paint.DEV_KERN_TEXT_FLAG or Paint.EMBEDDED_BITMAP_TEXT_FLAG

// Create element 
val textItem = TextParam(text, weight, align).apply{ 
    size = fontSize 
    typeface = typeface 
    gravity = gravity 
    perLineSpace = perLineSpace 
    ...
    }
```
#### b. LineParam - Solid Line
```
//Element maximum width
val weight = 1.0
//Element bottom margin
val perLineSpace = 30

//Create element 
val lineItem = LineParam(weight).apply{
    perLineSpace = perLineSpace
}
```
#### c. LineDashedParam - Dashed Line
```
// Element maximum width 
val weight = 1.0 
// Element bottom margin 
val perLineSpace = 30

// Create element 
val lineDashedItem = LineDashedParam(weight).apply{         
    perLineSpace = perLineSpace
}
```
