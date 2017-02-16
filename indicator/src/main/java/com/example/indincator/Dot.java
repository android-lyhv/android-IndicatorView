package com.example.indincator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Copyright@ AsianTech.Inc
 * Created by ly.ho.sumiu on 16/02/2017.
 */

public class Dot {
    private Paint paint;

    private PointF pointFCenter;

    private int currentRadius;

    public Dot() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointFCenter = new PointF();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    public void setCenter(float x, float y) {
        pointFCenter.set(x, y);
    }

    public int getCurrentRadius() {
        return currentRadius;
    }

    public void setCurrentRadius(int radius) {
        this.currentRadius = radius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(pointFCenter.x, pointFCenter.y, currentRadius, paint);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
