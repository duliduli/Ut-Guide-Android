package cn.utsoft.commons.transguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Unbinder;
import cn.utsoft.commons.guide.animation.TransGuideListener;
import cn.utsoft.commons.guide.shape.Focus;
import cn.utsoft.commons.guide.view.TransGuideView;

public class MainActivity extends AppCompatActivity implements TransGuideListener, View.OnClickListener {

    private Unbinder mUnbinder;

    Button btnMainType1;
    Button btnMainType2;
    Button btnMainType3;
    Button btnMainType4;
    Button btnLightarea01;
    Button btnLightarea02;
    Button btnLightarea03;
    Button btnLightarea04;
    Button btnLightarea05;

    private BlankFragment fg_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnMainType1 = (Button) findViewById(R.id.btn_main_type1);
        btnMainType2 = (Button) findViewById(R.id.btn_main_type2);
        btnMainType3 = (Button) findViewById(R.id.btn_main_type3);
        btnMainType4 = (Button) findViewById(R.id.btn_main_type4);
        btnLightarea01 = (Button) findViewById(R.id.btn_lightarea01);
        btnLightarea02 = (Button) findViewById(R.id.btn_lightarea02);
        btnLightarea03 = (Button) findViewById(R.id.btn_lightarea03);
        btnLightarea04 = (Button) findViewById(R.id.btn_lightarea04);
        btnLightarea05 = (Button) findViewById(R.id.btn_lightarea05);

        btnMainType1.setOnClickListener(this);
        btnMainType2.setOnClickListener(this);
        btnMainType3.setOnClickListener(this);
        btnMainType4.setOnClickListener(this);

        fg_fragment = (BlankFragment) getSupportFragmentManager().findFragmentById(R.id.fg_fragment);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_type1:
                showTransGuide("点亮所有需要指示的按钮", false, false, false, Focus.RECT, btnLightarea01, btnLightarea02, btnLightarea03, btnLightarea04, btnLightarea05);
                break;
            case R.id.btn_main_type2:
                showTransGuide("要指示的按钮1", false, false, false, Focus.RECT, btnLightarea01);
                break;
            case R.id.btn_main_type3:
                showTransGuide("要指示的按钮1", false, false, false, Focus.RECT, btnLightarea01);
                break;
            case R.id.btn_main_type4:
                showTransGuide("点亮所有需要指示的按钮", true, false, false, Focus.RECT, btnLightarea01, btnLightarea02, btnLightarea03, btnLightarea04, btnLightarea05);
                break;
        }
    }


    @Override
    public void onUserClicked(View transGuideView) {
        if (transGuideView == btnLightarea01) {
            showTransGuide("要指示的按钮2", false, false, false, Focus.MINIMUM, btnLightarea02);
        }
        if (transGuideView == btnLightarea02) {
            showTransGuide("要指示的按钮3", false, true, true, Focus.NORMAL, btnLightarea03);
        }
        if (transGuideView == btnLightarea03) {
            showTransGuide("要指示的按钮4", false, false, true, Focus.RECT, btnLightarea04);
        }
        if (transGuideView == btnLightarea04) {
            showTransGuide("要指示的按钮5", false, false, false, Focus.RECT, btnLightarea05);
        }
    }

    @Override
    public void isShowing(boolean isShowing) {

    }

    private void showTransGuide(String text, boolean dissmsAnyPos, boolean skiptextVis, boolean imgiconsvis, Focus type, View... view) {
        new TransGuideView.BuilderGuide(this).setFocusType(type)
                .setEnableFadeAnimation(false)
                .setDismissAnyPos(dissmsAnyPos)
//                .setInfoTextSize(25)
                .setEnableDotAnimation(true)
                .setImgsIconVisible(false)
                .setImgsIconVisible(imgiconsvis)
                .setImgsIconBack(R.mipmap.ic_launcher)
                .setInfoText(text)
                .setTargetPadding(50)
                .setLightAreaConner(15)
                .setBackGround(R.color.colorback1)
                .setTargets(view)
                .setSkipTextVisible(skiptextVis)
                .setListener(this)
                .show();
    }
}
