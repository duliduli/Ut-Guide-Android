package cn.utsoft.commons.guide.animation;

import android.view.View;

public interface TransGuideListener {

    void onUserClicked(View transGuideView);

    void isShowing(boolean isShowing);
}
