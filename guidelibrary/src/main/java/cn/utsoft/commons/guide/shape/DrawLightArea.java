package cn.utsoft.commons.guide.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import java.util.List;

import cn.utsoft.commons.guide.target.Target;
import cn.utsoft.commons.guide.utils.Constants;


public class DrawLightArea {

    private List<Target> targets;

    private Focus focus;

    private int radius;

    private Point circlePoint;

    private int padding;

    public DrawLightArea(List<Target> target) {
        this(target, Focus.MINIMUM);
    }

    public DrawLightArea(List<Target> target, Focus focus) {
        this(target, focus, Constants.DEFAULT_TARGET_PADDING);
    }


    public DrawLightArea(List<Target> target, Focus focus, int padding) {
        this.targets = target;
        this.focus = focus;
        this.padding = padding;
        for (int i = 0; i < target.size(); i++) {
            circlePoint = getFocusPoint(i);
            calculateRadius(padding, i);
        }

    }

    public void draw(Canvas canvas, Paint eraser, int padding, int pos, int conner) {
        if (focus == Focus.RECT) {
            RectF rectF = new RectF();
            rectF.set(targets.get(pos).getRect());
//            canvas.drawRect(targets.get(pos).getRect(), eraser);
            canvas.drawRoundRect(rectF, conner, conner, eraser);
        } else {
            calculateRadius(padding, pos);
            circlePoint = getFocusPoint(pos);
            canvas.drawCircle(circlePoint.x, circlePoint.y, radius, eraser);
        }
    }

    private Point getFocusPoint(int pos) {
        return targets.get(pos).getPoint();
    }

    public void reCalculateAll(int pos) {
        calculateRadius(padding, pos);
        circlePoint = getFocusPoint(pos);
    }

    private void calculateRadius(int padding, int pos) {
        int side = 0;
        if (focus == Focus.MINIMUM)
            side = Math.min(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
        else if (focus == Focus.ALL)
            side = Math.max(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
        else if (focus == Focus.NORMAL) {
            int minSide = Math.min(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
            int maxSide = Math.max(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
            side = (minSide + maxSide) / 2;
        } else {
            radius = 0;
        }
        radius = side + padding;
    }

    public int getRadius() {
        return radius;
    }

    public Point getPoint() {
        return circlePoint;
    }

}
