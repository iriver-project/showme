package com.sktelecom.showme.base.view.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sktelecom.showme.base.util.CommonUtil;

public class Circle extends View {

    private static final int START_ANGLE_POINT = 0;

    private final Paint paint;
    private final RectF rect;
    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 10;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.RED);
        int size = CommonUtil.Companion.getWith().dpTopx(context, 140);
        rect = new RectF(strokeWidth, strokeWidth, size + strokeWidth, size + strokeWidth);
        angle = 120;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}