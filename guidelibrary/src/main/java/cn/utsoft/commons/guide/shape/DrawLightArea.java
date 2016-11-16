package cn.utsoft.commons.guide.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.List;

import cn.utsoft.commons.guide.target.Target;
import cn.utsoft.commons.guide.utils.Constants;


public class DrawLightArea {

    private Target target;
    private List<Target> targets;

    private Focus focus;

    private int radius;

    private Point circlePoint;

    private int padding;

    public DrawLightArea(Target target) {
        this(target, Focus.MINIMUM);
    }

    public DrawLightArea(Target target, Focus focus) {
        this(target, focus, Constants.DEFAULT_TARGET_PADDING);
    }

    public DrawLightArea(Target target, Focus focus, int padding) {
        this.target = target;
        this.focus = focus;
        this.padding = padding;
        circlePoint = getFocusPoint(Constants.DEFAULT_POSITION);
        calculateRadius(padding, Constants.DEFAULT_POSITION);
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

    public void draw(Canvas canvas, Paint eraser, int padding, int pos) {
        if (target != null) {
            drawDifferent(canvas, eraser, padding, Constants.DEFAULT_POSITION);
        } else
            drawDifferent(canvas, eraser, padding, pos);
    }

    private void drawDifferent(Canvas canvas, Paint eraser, int padding, int pos) {
        if (focus == Focus.RECT) {
            if (target != null) {
                canvas.drawRect(target.getRect(), eraser);
            } else {
                canvas.drawRect(targets.get(pos).getRect(), eraser);
            }
        } else {
            calculateRadius(padding, pos);
            circlePoint = getFocusPoint(pos);
            canvas.drawCircle(circlePoint.x, circlePoint.y, radius, eraser);
        }
    }

    private Point getFocusPoint(int pos) {
        if (target != null) {
            return target.getPoint();
        } else
            return targets.get(pos).getPoint();
    }

    public void reCalculateAll(int pos) {
        calculateRadius(padding, pos);
        circlePoint = getFocusPoint(pos);
    }

    private void calculateRadius(int padding, int pos) {
        if (target != null) {
            int side;
            if (focus == Focus.MINIMUM)
                side = Math.min(target.getRect().width() / 2, target.getRect().height() / 2);
            else if (focus == Focus.ALL)
                side = Math.max(target.getRect().width() / 2, target.getRect().height() / 2);
            else {
                int minSide = Math.min(target.getRect().width() / 2, target.getRect().height() / 2);
                int maxSide = Math.max(target.getRect().width() / 2, target.getRect().height() / 2);
                side = (minSide + maxSide) / 2;
            }
            radius = side + padding;
        } else {
            int side;
            if (focus == Focus.MINIMUM)
                side = Math.min(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
            else if (focus == Focus.ALL)
                side = Math.max(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
            else {
                int minSide = Math.min(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
                int maxSide = Math.max(targets.get(pos).getRect().width() / 2, targets.get(pos).getRect().height() / 2);
                side = (minSide + maxSide) / 2;
            }
            radius = side + padding;
        }
    }

    public int getRadius(int pos) {
        return radius;
    }

    public Point getPoint(int pos) {
        return circlePoint;
    }

}
