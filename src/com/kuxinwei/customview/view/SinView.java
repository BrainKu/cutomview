package com.kuxinwei.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kuxinwei on 2014/8/22.
 */
public class SinView extends View {

    private Paint mPaint;
    private Path mPath = new Path();

    int height = 0;
    int width = 0;

    public SinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setAlpha(128);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initPath();
    }

    private void initPath() {
        mPath.setFillType(Path.FillType.WINDING);
        float step = width / 540.0f;
        float halfY = height / 12;
        float offset = 0;
        float startX = 0;
        float startY = halfY + offset;
        float curX = startX;
        float curY = startY;
        float preX = curX;
        float preY = curY;
        mPath.moveTo(startX, startY);
        for (int i = 0; i < 540; i++) {
            curX += step;
            curY = offset + halfY - halfY * (float) Math.sin(Math.toRadians((i + 1)));
            mPath.quadTo(preX, preY, curX, curY);

            preX = curX;
            preY = curY;
        }
        mPath.lineTo(curX, height);
        mPath.lineTo(startX, height);
        mPath.lineTo(startX, startY);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
