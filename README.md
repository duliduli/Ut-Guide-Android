# Ut-Guide-Android
透明指示层
===================

**1.1 Gradle集成**

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

```
dependencies {
     
}
```


####二、截图预览

<!--![](https://github.com/MarnonDev/EasyStatusView/blob/master/screenshot/EasyStatusView.gif)-->

demo 下载地址：
----------
<!--https://github.com/niniloveyou/StateButton/blob/master/demo.apk-->

方法与属性：
------

<!--![](https://github.com/niniloveyou/StateButton/blob/master/image.png)-->

<!--![stateButton](https://github.com/niniloveyou/StateButton/blob/master/stateButton.gif)-->

|        Attribute       |      default value     |                 java                                         |   作用    |
|------------------------|------------------------|--------------------------------------------------------------|--------|
| maskColor              |   0x50000000           |  setMaskColor(int maskColor)                |透明指示层的背景颜色|
| delayMillis            |   300                  |  setDelayMillis(int delayMillis)               |延迟显示时间|
| fadeAnimationDuration  |   500                  |  setFadeAnimationDuration(int delayMillis)                |动画时间|
| padding                |   10                   |  setTargetPadding(int padding)    |画圆时内边距|
| focusType              |   Focus.NORMAL         |  setNormalStrokeWidth(int widht)              |指示区域要画的类型|
| isFadeAnimationEnabled |   true                 |  setEnableFadeAnimation(boolean isFadeAnimationEnabled)              | 是否带动画|
| dismissAnyPos          |   false                |  setDismissAnyPos(boolean dismissAnyPos)              |点击任何地方指示层是否消失|
| isDotViewEnabled       |   true                 |  setEnableDotAnimation(boolean isDotAnimationEnabled)          |中间的小圆点是否显示|
| isPerformClick         |   true                 |  setPerformClick(boolean isPerformClick)       ||
| infoTextViewColor      |   0xFF000000           |  setInfoTextColor(int textColor)    |提示内容的文字颜色|
| infoTextViewSize       |   original text size   |  setInfoTextSize(int textSize)    |提示内容的文字大小|
| infoTextViewContent    |   null                 |  setInfoText(String infoText)   |提示的内容|
| isImageViewEnabled     |   true                 |  setImgsIconVisible(boolean isImageViewIconEnabled)           |提示内容前面的图片是否显示|
| imageViewIcon backGround  |   null              |  setImgsIconBack(Integer imageViewIconIds)           |提示内容前面的图片背景|
| skipTextColor             |   original text color  |  setSkipTextColor(int skipColor)             |跳过按钮的文字颜色|
| skipTextSize              |   original text size   |  setSkipTextSize(int skipTextSize)             |跳过按钮的文字大小|
| skipText isVisible        |   false                |  setSkipTextVisible(boolean visible)             |跳过按钮的文字是否显示|
| skipTextContent           |   null                 |  setSkipInfoText(String skipText)             |跳过按钮的文字内容|
| contentLayoutBackGround   |   @android:color/white |  setBackGround(int backGroundColor)             |提示区域的背景颜色|
| Targets      |                      | setTargets(View... view)              |要指示的按钮|


**示例代码**

