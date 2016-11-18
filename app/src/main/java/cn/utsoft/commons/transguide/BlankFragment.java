package cn.utsoft.commons.transguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.utsoft.commons.guide.animation.TransGuideListener;
import cn.utsoft.commons.guide.shape.Focus;
import cn.utsoft.commons.guide.view.TransGuideView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements TransGuideListener {

    private TextView tv_text1;
    private TextView tv_text2;
    private TextView tv_text3;
    private TextView tv_text4;

    private View view;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        init();
        return view;
    }

    private void init() {
        tv_text1 = (TextView) view.findViewById(R.id.tv_text1);
        tv_text2 = (TextView) view.findViewById(R.id.tv_text2);
        tv_text3 = (TextView) view.findViewById(R.id.tv_text3);
        tv_text4 = (TextView) view.findViewById(R.id.tv_text4);

        showTransGuide("Fragment中要指示的按钮1", false, false, false, Focus.RECT, tv_text1);

    }

    @Override
    public void onUserClicked(View transGuideView) {
        if (transGuideView == tv_text1) {
            showTransGuide("Fragment中要指示的按钮2", false, false, false, Focus.MINIMUM, tv_text2);
        }
        if (transGuideView == tv_text2) {
            showTransGuide("Fragment中要指示的按钮3", false, true, true, Focus.NORMAL, tv_text3);
        }
        if (transGuideView == tv_text3) {
            showTransGuide("Fragment中要指示的按钮4", false, false, true, Focus.RECT, tv_text4);
        }
    }

    @Override
    public void isShowing(boolean isShowing) {

    }

    private void showTransGuide(String text, boolean dissmsAnyPos, boolean skiptextVis, boolean imgiconsvis, Focus type, View... view) {
        new TransGuideView.BuilderGuide(getActivity()).setFocusType(type)
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
