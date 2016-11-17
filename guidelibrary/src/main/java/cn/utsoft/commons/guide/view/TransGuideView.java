package cn.utsoft.commons.guide.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.utsoft.commons.guide.R;
import cn.utsoft.commons.guide.animation.AnimationFactory;
import cn.utsoft.commons.guide.animation.AnimationListener;
import cn.utsoft.commons.guide.animation.TransGuideListener;
import cn.utsoft.commons.guide.shape.DrawLightArea;
import cn.utsoft.commons.guide.shape.Focus;
import cn.utsoft.commons.guide.target.Target;
import cn.utsoft.commons.guide.target.ViewTarget;
import cn.utsoft.commons.guide.utils.Constants;


public class TransGuideView extends RelativeLayout {

    private int maskColor;

    private long delayMillis;

    private boolean isReady;

    private boolean isFadeAnimationEnabled;

    private long fadeAnimationDuration;

    private DrawLightArea lightAreaShape;

    private Focus focusType;

    private List<Target> targetViews;

    private Paint eraser;

    private Handler handler;

    private Bitmap bitmap;

    private Canvas canvas;

    private int padding;

    private int width;

    private int height;

    private boolean dismissAnyPos;

    private View infoView;

    private TextView textViewInfo;

    private int colorTextViewInfo;

    private boolean isInfoEnabled;

    private View dotView;

    private boolean isDotViewEnabled;

    private ImageView imageViewIcon;

    private boolean isImageViewEnabled;

    private TextView tv_skip_guide;


    private boolean isLayoutCompleted;

    private TransGuideListener materialIntroListener;

    private boolean isPerformClick;

    private boolean mShowing;

    public TransGuideView(Context context) {
        this(context, null);
    }

    public TransGuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TransGuideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setWillNotDraw(false);
        setVisibility(INVISIBLE);
        /**
         * 初始化
         */
        maskColor = Constants.DEFAULT_MASK_COLOR;
        delayMillis = Constants.DEFAULT_DELAY_MILLIS;
        fadeAnimationDuration = Constants.DEFAULT_FADE_DURATION;
        padding = Constants.DEFAULT_TARGET_PADDING;
        colorTextViewInfo = Constants.DEFAULT_COLOR_TEXTVIEW_INFO;
        focusType = Focus.NORMAL;
        isReady = false;
        isFadeAnimationEnabled = true;
        dismissAnyPos = false;
        isLayoutCompleted = false;
        isInfoEnabled = false;
        isDotViewEnabled = true;
        isPerformClick = true;
        isImageViewEnabled = true;

        handler = new Handler();

        eraser = new Paint(Paint.ANTI_ALIAS_FLAG);
        eraser.setColor(0xFFFFFFFF);
        eraser.setDither(true);
        eraser.setAntiAlias(true);
        eraser.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        View layoutInfo = LayoutInflater.from(getContext()).inflate(R.layout.guide_layout, null);
        infoView = layoutInfo.findViewById(R.id.info_layout);

        textViewInfo = (TextView) layoutInfo.findViewById(R.id.textview_info);
        textViewInfo.setTextColor(colorTextViewInfo);
        imageViewIcon = (ImageView) layoutInfo.findViewById(R.id.iv_icon);
        tv_skip_guide = (TextView) layoutInfo.findViewById(R.id.tv_skip_guide);

        dotView = LayoutInflater.from(getContext()).inflate(R.layout.guide_dotview, null);
        dotView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for (int i = 0; i < targetViews.size(); i++) {
                    lightAreaShape.reCalculateAll(i);
                    if (lightAreaShape != null && lightAreaShape.getPoint().y != 0 && !isLayoutCompleted) {
                        if (isInfoEnabled) {
                            setInfoLayout();
                        }
                        if (isDotViewEnabled) {
                            setDotViewLayout();
                        }
                        removeOnGlobalLayoutListener(TransGuideView.this, this);
                    }
                }
            }
        });

        /**
         *
         */
        tv_skip_guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMaterialView();
                if (materialIntroListener != null) {
                    materialIntroListener.isShowing(false);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isReady)
            return;
        if (targetViews != null) {
            for (int i = 0; i < targetViews.size(); i++) {
                drawLightArea(canvas, i);
            }
        }
    }

    private void drawLightArea(Canvas canvas, int pos) {
        if (pos == 0) {
            if (bitmap == null || canvas == null) {
                if (bitmap != null) bitmap.recycle();
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                this.canvas = new Canvas(bitmap);
            }
            /**
             * 画背景
             */
            this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            this.canvas.drawColor(maskColor);

            canvas.drawBitmap(bitmap, 0, 0, null);
        }
        lightAreaShape.draw(this.canvas, eraser, padding, pos);
    }

    /**
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xT = event.getX();
        float yT = event.getY();

        int xV = lightAreaShape.getPoint().x;
        int yV = lightAreaShape.getPoint().y;

        int radius = lightAreaShape.getRadius();

        double dx = Math.pow(xT - xV, 2);
        double dy = Math.pow(yT - yV, 2);

        boolean isTouchOnFocus = (dx + dy) <= Math.pow(radius, 2);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                if (isTouchOnFocus || dismissAnyPos)
                    dismiss();
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Show Guide view
     *
     * @param activity
     */

    private void show(Activity activity) {

        ((ViewGroup) activity.getWindow().getDecorView()).addView(this);

        setReady(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFadeAnimationEnabled)
                    AnimationFactory.animateFadeIn(TransGuideView.this, fadeAnimationDuration, new AnimationListener.OnAnimationStartListener() {
                        @Override
                        public void onAnimationStart() {
                            setVisibility(VISIBLE);
                        }
                    });
                else
                    setVisibility(VISIBLE);
            }
        }, delayMillis);
        mShowing = true;
        if (materialIntroListener != null) {
            materialIntroListener.isShowing(mShowing);
        }
    }

    /**
     * Dismiss content layout
     */
    private void dismiss() {
        if (!mShowing) {
            return;
        }
        AnimationFactory.animateFadeOut(this, fadeAnimationDuration, new AnimationListener.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                setVisibility(GONE);
                removeMaterialView();
                if (materialIntroListener != null) {
                    materialIntroListener.onUserClicked(targetViews.get(targetViews.size() - 1).getView());
                }
            }
        });
        mShowing = false;
    }

    private void removeMaterialView() {
        if (getParent() != null)
            ((ViewGroup) getParent()).removeView(this);
    }

    /**
     * content layout
     */
    private void setInfoLayout() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                isLayoutCompleted = true;
                if (infoView.getParent() != null)
                    ((ViewGroup) infoView.getParent()).removeView(infoView);
                LayoutParams infoDialogParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ((RelativeLayout) infoView).setGravity(Gravity.BOTTOM);
                if (lightAreaShape.getPoint().y < height / 2) {
                    infoDialogParams.setMargins(0, 0, 0, height - (lightAreaShape.getPoint().y + lightAreaShape.getRadius()) - 3 * lightAreaShape.getRadius());
                } else {
                    infoDialogParams.setMargins(0, 0, 0, height - (lightAreaShape.getPoint().y + lightAreaShape.getRadius()) + 2 * lightAreaShape.getRadius());
                }
                infoView.setLayoutParams(infoDialogParams);
                infoView.postInvalidate();
                addView(infoView);
                if (!isImageViewEnabled) {
                    imageViewIcon.setVisibility(GONE);
                }
                infoView.setVisibility(VISIBLE);
            }
        });
    }

    /**
     * DotView
     */
    private void setDotViewLayout() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dotView.getParent() != null)
                    ((ViewGroup) dotView.getParent()).removeView(dotView);
                LayoutParams dotViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dotViewLayoutParams.height = (int) (Constants.DEFAULT_DOT_SIZE * Resources.getSystem().getDisplayMetrics().density);
                dotViewLayoutParams.width = (int) (Constants.DEFAULT_DOT_SIZE * Resources.getSystem().getDisplayMetrics().density);
                dotViewLayoutParams.setMargins(lightAreaShape.getPoint().x - (dotViewLayoutParams.width / 2), lightAreaShape.getPoint().y - (dotViewLayoutParams.height / 2), 0, 0);
                dotView.setLayoutParams(dotViewLayoutParams);
                dotView.postInvalidate();
                addView(dotView);
                dotView.setVisibility(VISIBLE);
                AnimationFactory.performAnimation(dotView);
            }
        });
    }

    public boolean isShowing() {
        return mShowing;
    }

    private void setMaskColor(int maskColor) {
        this.maskColor = maskColor;
    }

    private void setDelay(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    private void setfadeAnimationDuration(int fadeAnimationDuration) {
        this.fadeAnimationDuration = fadeAnimationDuration;
    }

    /**
     * @param isFadeAnimationEnabled
     */
    private void enableFadeAnimation(boolean isFadeAnimationEnabled) {
        this.isFadeAnimationEnabled = isFadeAnimationEnabled;
    }

    private void setReady(boolean isReady) {
        this.isReady = isReady;
    }


    private void setTargets(List<Target> targetViews) {
        this.targetViews = targetViews;
    }

    private void setFocusType(Focus focusType) {
        this.focusType = focusType;
    }

    private void setCircle(DrawLightArea lightAreaShape) {
        this.lightAreaShape = lightAreaShape;
    }

    private void setPadding(int padding) {
        this.padding = padding;
    }

    private void setDismissAnyPos(boolean dismissAnyPos) {
        this.dismissAnyPos = dismissAnyPos;
    }

    private void setColorTextViewInfo(int colorTextViewInfo) {
        this.colorTextViewInfo = colorTextViewInfo;
        textViewInfo.setTextColor(this.colorTextViewInfo);
    }

    private void setTvSkipGuide(String textofSkip) {
        this.tv_skip_guide.setText(textofSkip);
    }

    private void setSkipColor(int skipColor) {
        this.tv_skip_guide.setTextColor(skipColor);
    }

    private void setSkipTextSize(int skipTextSize) {
        this.tv_skip_guide.setTextSize(TypedValue.COMPLEX_UNIT_SP, skipTextSize);
    }

    private void setSkipVisible(boolean visibility) {

        if (visibility) {
            this.tv_skip_guide.setVisibility(VISIBLE);
        } else {
            this.tv_skip_guide.setVisibility(GONE);
        }

    }

    private void setTextViewInfo(String textViewInfo) {
        this.textViewInfo.setText(textViewInfo);
    }

    private void setTextViewInfoSize(int textViewInfoSize) {
        this.textViewInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, textViewInfoSize);
    }

    private void enableInfoDialog(boolean isInfoEnabled) {
        this.isInfoEnabled = isInfoEnabled;
    }


    private void enableImageViewIcon(boolean isImageViewEnabled) {
        this.isImageViewEnabled = isImageViewEnabled;
    }

    private void enableDotView(boolean isDotViewEnabled) {
        this.isDotViewEnabled = isDotViewEnabled;
    }


    private void setListener(TransGuideListener materialIntroListener) {
        this.materialIntroListener = materialIntroListener;
    }

    private void setPerformClick(boolean isPerformClick) {
        this.isPerformClick = isPerformClick;
    }


    private void setImgIcon(Integer imgsId) {
        this.imageViewIcon.setBackgroundResource(imgsId);
    }


    private void setInfoViewBack(int colorInfoView) {
        this.infoView.setBackgroundResource(colorInfoView);
    }

    /**
     * Builder Class
     */
    public static class BuilderGuide {

        private TransGuideView materialIntroView;

        private Activity activity;

        public BuilderGuide(Activity activity) {
            this.activity = activity;
            materialIntroView = new TransGuideView(activity);
        }

        /**
         * @param maskColor 遮盖阴影区域背景颜色   默认半透明
         * @return
         */
        public BuilderGuide setMaskColor(int maskColor) {
            materialIntroView.setMaskColor(maskColor);
            return this;
        }

        /**
         * @param delayMillis 指示內容延迟显示时间
         * @return
         */
        public BuilderGuide setDelayMillis(int delayMillis) {
            materialIntroView.setDelay(delayMillis);
            return this;
        }

        /**
         * @param delayMillis 动画时间
         * @return
         */
        public BuilderGuide setFadeAnimationDuration(int delayMillis) {
            materialIntroView.setfadeAnimationDuration(delayMillis);
            return this;
        }

        /**
         * @param isFadeAnimationEnabled 是否带动画显示消失 默认为true
         * @return
         */
        public BuilderGuide setEnableFadeAnimation(boolean isFadeAnimationEnabled) {
            materialIntroView.enableFadeAnimation(isFadeAnimationEnabled);
            return this;
        }

        /**
         * @param focusType 类型 默认画圆全包裹
         * @return
         */
        public BuilderGuide setFocusType(Focus focusType) {
            materialIntroView.setFocusType(focusType);
            return this;
        }

        /**
         * @param view 要指示的View的集合
         * @return
         */
        public BuilderGuide setTargets(View... view) {
            ArrayList<Target> list = new ArrayList<>();
            for (View view1 : view) {
                list.add(new ViewTarget(view1));
            }
            materialIntroView.setTargets(list);
            return this;
        }

        /**
         * @param padding 画圆时内边距 默认为10
         * @return
         */
        public BuilderGuide setTargetPadding(int padding) {
            materialIntroView.setPadding(padding);
            return this;
        }

        /**
         * @param infoText 指示的内容
         * @return
         */
        public BuilderGuide setInfoText(String infoText) {
            materialIntroView.enableInfoDialog(true);
            materialIntroView.setTextViewInfo(infoText);
            return this;
        }

        /**
         * @param textSize 指示内容的字体大小
         * @return
         */
        public BuilderGuide setInfoTextSize(int textSize) {
            materialIntroView.setTextViewInfoSize(textSize);
            return this;
        }

        /**
         * @param textColor 内容的文字颜色
         * @return
         */
        public BuilderGuide setInfoTextColor(int textColor) {
            materialIntroView.setColorTextViewInfo(textColor);
            return this;
        }

        /**
         * @param skipText 跳过按钮的文字内容
         * @return
         */
        public BuilderGuide setSkipInfoText(String skipText) {
            materialIntroView.setTvSkipGuide(skipText);
            return this;
        }

        /**
         * @param visible 跳过是否显示
         * @return
         */
        public BuilderGuide setSkipTextVisible(boolean visible) {
            materialIntroView.setSkipVisible(visible);
            return this;
        }

        /**
         * @param skipColor 跳过文字的颜色
         * @return
         */
        public BuilderGuide setSkipTextColor(int skipColor) {
            materialIntroView.setSkipColor(skipColor);
            return this;
        }

        public BuilderGuide setSkipTextSize(int skipTextSize) {
            materialIntroView.setSkipTextSize(skipTextSize);
            return this;
        }

        /**
         * @param dismissAnyPos 点击任何位置 是否消失  默认为false
         * @return
         */
        public BuilderGuide setDismissAnyPos(boolean dismissAnyPos) {
            materialIntroView.setDismissAnyPos(dismissAnyPos);
            return this;
        }


        /**
         * @param isDotAnimationEnabled 闪动的小圆点是否显示
         * @return
         */
        public BuilderGuide setEnableDotAnimation(boolean isDotAnimationEnabled) {
            materialIntroView.enableDotView(isDotAnimationEnabled);
            return this;
        }

        /**
         * @param isImageViewIconEnabled 是否显示图片
         * @return
         */
        public BuilderGuide setImgsIconVisible(boolean isImageViewIconEnabled) {
            materialIntroView.enableImageViewIcon(isImageViewIconEnabled);
            return this;
        }

        /**
         * @param imageViewIconIds 文字前面的图片
         * @return
         */
        public BuilderGuide setImgsIconBack(Integer imageViewIconIds) {
            materialIntroView.setImgIcon(imageViewIconIds);
            return this;
        }

        public BuilderGuide setBackGround(int backGroundColor) {
            materialIntroView.setInfoViewBack(backGroundColor);
            return this;
        }

        /**
         * 监听当前点击的是那个View
         *
         * @param materialIntroListener
         * @return
         */
        public BuilderGuide setListener(TransGuideListener materialIntroListener) {
            materialIntroView.setListener(materialIntroListener);
            return this;
        }

        public BuilderGuide setPerformClick(boolean isPerformClick) {
            materialIntroView.setPerformClick(isPerformClick);
            return this;
        }

        public boolean isShowing() {
            return materialIntroView.mShowing;
        }

        public TransGuideView build() {
            DrawLightArea circle = new DrawLightArea(
                    materialIntroView.targetViews,
                    materialIntroView.focusType,
                    materialIntroView.padding);
            materialIntroView.setCircle(circle);
            return materialIntroView;
        }


        public TransGuideView show() {
            build().show(activity);
            return materialIntroView;
        }
    }
}
