package cn.utsoft.commons.transguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import butterknife.Unbinder;
import cn.utsoft.commons.guide.animation.TransGuideListener;
import cn.utsoft.commons.guide.shape.Focus;
import cn.utsoft.commons.guide.view.TransGuideView;

public class MainActivity extends AppCompatActivity implements TransGuideListener, View.OnClickListener {

    private Unbinder mUnbinder;

    Button btnMainType1;
    Button btnMainType2;
    Button btnMainType3;
    Button btnLightarea01;
    Button btnLightarea02;
    Button btnLightarea03;
    Button btnLightarea04;
    Button btnLightarea05;
    ListView lvContent;

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
        btnLightarea01 = (Button) findViewById(R.id.btn_lightarea01);
        btnLightarea02 = (Button) findViewById(R.id.btn_lightarea02);
        btnLightarea03 = (Button) findViewById(R.id.btn_lightarea03);
        btnLightarea04 = (Button) findViewById(R.id.btn_lightarea04);
        btnLightarea05 = (Button) findViewById(R.id.btn_lightarea05);
        lvContent = (ListView) findViewById(R.id.lv_content);

        btnMainType1.setOnClickListener(this);
        btnMainType2.setOnClickListener(this);
        btnMainType3.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_type1:
                showTransGuide("点亮所有需要指示的按钮", Focus.RECT, btnLightarea01, btnLightarea02, btnLightarea03, btnLightarea04, btnLightarea05);
                break;
            case R.id.btn_main_type2:
                showTransGuide("点亮指示的按钮1", Focus.RECT, btnLightarea01);
                break;
            case R.id.btn_main_type3:
                new TransGuideView.BuilderGuide(this).setFocusType(Focus.RECT)
                        .setEnableFadeAnimation(false)
                        .setDismissAnyPos(false)
                        .setEnableDotAnimation(true)
                        .setImgsIconBack(R.mipmap.ic_launcher)
                        .setInfoText("点亮指示的按钮1")
                        .setTargetPadding(50)
                        .setTargets(btnLightarea01)
                        .setSkipTextVisible(false)
                        .setPerformClick(true)
                        .setListener(this)
                        .show();
                break;
        }
    }

    private void showTransGuide(String text, Focus type, View... view) {
        new TransGuideView.BuilderGuide(this).setFocusType(type)
                .setEnableFadeAnimation(false)
                .setDismissAnyPos(false)
                .setEnableDotAnimation(true)
                .setImgsIconVisible(true)
                .setInfoText(text)
                .setTargetPadding(50)
                .setTargets(view)
                .setSkipTextVisible(false)
                .setPerformClick(true)
                .setListener(this)
                .show();
    }

    @Override
    public void onUserClicked(View transGuideView) {
        if (transGuideView == btnLightarea01) {
            showTransGuide("点亮指示的按钮1", Focus.MINIMUM, btnLightarea02);
        }
        if (transGuideView == btnLightarea02) {
            showTransGuide("点亮指示的按钮1", Focus.NORMAL, btnLightarea03);
        }
        if (transGuideView == btnLightarea03) {
            showTransGuide("点亮指示的按钮1", Focus.ALL, btnLightarea04);
        }
        if (transGuideView == btnLightarea04) {
            showTransGuide("点亮指示的按钮1", Focus.RECT, btnLightarea05);
        }
    }

    @Override
    public void isShowing(boolean isShowing) {

    }

}
