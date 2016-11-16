package cn.utsoft.commons.guide.target;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public interface Target {
    /**
     * Returns center point of target.
     * We can get x and y coordinates using
     * point object
     *
     * @return
     */
    Point getPoint();

    /**
     * Returns Rectangle points of target view
     *
     * @return
     */
    Rect getRect();

    /**
     * return target view
     *
     * @return
     */
    View getView();

}
