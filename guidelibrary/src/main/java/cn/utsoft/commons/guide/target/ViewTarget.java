package cn.utsoft.commons.guide.target;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import java.util.List;

public class ViewTarget implements Target {

    private View view;

    private List<View> views;

    public ViewTarget(View view) {
        this.view = view;
    }


    @Override
    public Point getPoint() {

        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new Point(location[0] + (view.getWidth() / 2), location[1] + (view.getHeight() / 2));
    }

    @Override
    public Rect getRect() {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new Rect(
                location[0],
                location[1],
                location[0] + view.getWidth(),
                location[1] + view.getHeight()
        );
    }

    @Override
    public View getView() {
        return view;
    }

}
