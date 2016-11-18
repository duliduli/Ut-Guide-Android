# Ut-Guide-Android
透明指示层
===================

#### 一、简介：

**1.0.0 Gradle集成**

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
     ...
     compile 'com.github.duliduli:Ut-Guide-Android:1.0.0'
}
```

**1.0.0 功能说明**

####二、截图预览

![](https://github.com/2366634662/Ut-Guide-Android/raw/master/screenshot/TransGuide.gif)

demo 下载地址：
----------
            https://github.com/duliduli/Ut-Guide-Android


#### 三、使用

方法与属性：
------


|        Attribute       |      default value     |                 java                                         |   作用    |
|------------------------|------------------------|--------------------------------------------------------------|--------|
| maskColor              |   0x50000000           |  setMaskColor(int maskColor)                |透明指示层的背景颜色|
| delayMillis            |   300                  |  setDelayMillis(int delayMillis)               |延迟显示时间|
| fadeAnimationDuration  |   500                  |  setFadeAnimationDuration(int delayMillis)                |动画显示时间|
| padding                |   10                   |  setTargetPadding(int padding)    |画圆时内边距|
| focusType              |   Focus.NORMAL         |  setNormalStrokeWidth(int widht)              |指示区域要画的类型|
| isFadeAnimationEnabled |   true                 |  setEnableFadeAnimation(boolean isFadeAnimationEnabled)              | 是否带有动画|
| dismissAnyPos          |   false                |  setDismissAnyPos(boolean dismissAnyPos)              |点击任何地方指示层是否消失|
| isDotViewEnabled       |   true                 |  setEnableDotAnimation(boolean isDotAnimationEnabled)          |中间的小圆点是否显示|
| infoTextViewColor      |   0xFF000000           |  setInfoTextColor(int textColor)    |提示内容的文字颜色|
| infoTextViewSize       |   original text size   |  setInfoTextSize(int textSize)    |提示内容的文字大小|
| infoTextViewContent    |   null                 |  setInfoText(String infoText)   |提示的内容|
| isImageViewEnabled     |   true                 |  setImgsIconVisible(boolean isImageViewIconEnabled)           |提示内容前面的图片是否显示|
| imageViewIcon backGround  |   null              |  setImgsIconBack(Integer imageViewIconIds)           |提示内容前面的图片资源|
| skipTextColor             |   original text color  |  setSkipTextColor(int skipColor)             |跳过按钮的文字颜色|
| skipTextSize              |   original text size   |  setSkipTextSize(int skipTextSize)             |跳过按钮的文字大小|
| skipText isVisible        |   false                |  setSkipTextVisible(boolean visible)             |跳过按钮的文字是否显示|
| skipTextContent           |   null                 |  setSkipInfoText(String skipText)             |跳过按钮的文字内容|
| contentLayoutBackGround   |   @android:color/white |  setBackGround(int backGroundColor)             |提示区域的背景颜色|
| conner   |   0 |  setLightAreaConner(int conner)             |矩形圆角|
| Targets      |                      | setTargets(View... view)              |  要指示的按钮  |
| isShowing      |                      |              |  指示层是否在显示  |

Focus
------
| Type | 作用 | 
|------------------------|-------------------------|
| Focus.NORMAL| 普通圆形| 
| Focus.ALL| 全包裹圆形| 
| Focus.MINIMUM| 固定大小圆形| 
| Focus.RECT| 矩形| 

**示例代码**

````
private void showTransGuide(String text, boolean dissmsAnyPos, boolean skiptextVis, boolean imgiconsvis, Focus type, View... view) {
        new TransGuideView.BuilderGuide(this).setFocusType(type)
                .setEnableFadeAnimation(false)
                .setDismissAnyPos(dissmsAnyPos)
                .setEnableDotAnimation(true)
                .setImgsIconVisible(imgiconsvis)
                .setImgsIconBack(R.mipmap.ic_launcher)
                .setInfoText(text)
                .setTargetPadding(50)
                .setTargets(view)
                .setSkipTextVisible(skiptextVis)
                .setListener(this)
                .show();
    }

````


**指示多个，回调监听**



````
        public class MainActivity extends AppCompatActivity implements TransGuideListener,{
            
            ...............
            
                @Override
                public void onUserClicked(View transGuideView) {
                    if (transGuideView == btnLightarea01) {
                        showTransGuide("要指示的按钮2", false, false, false, Focus.MINIMUM, btnLightarea02);
                    }
                    if (transGuideView == btnLightarea02) {
                        showTransGuide("要指示的按钮3", false, true, true, Focus.NORMAL, btnLightarea03);
                    }
                    if (transGuideView == btnLightarea03) {
                        showTransGuide("要指示的按钮4", false, false, false, Focus.ALL, btnLightarea04);
                    }
                    if (transGuideView == btnLightarea04) {
                        showTransGuide("要指示的按钮5", false, false, false, Focus.RECT, btnLightarea05);
                    }
                }
                
                @Override
                public void isShowing(boolean isShowing) {
            
                }
                

}

````

#### 四、关于我 
- 邮箱：15736079012@163.com
