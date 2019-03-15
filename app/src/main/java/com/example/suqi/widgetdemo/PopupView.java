package com.example.suqi.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author suqi
 * @time 2018/9/25
 * @description
 */
public class PopupView extends View {

    Paint mPaint;

    int width;

    int height;

    Path path;

    int mWaveHeight;


    public PopupView(Context context) {
        this(context, null);
    }

    public PopupView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = dp2px(300);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = dp2px(200);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(0, 0, width, height);
        canvas.drawRect(rect, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.CYAN);
        RectF content = new RectF(dp2px(20), dp2px(20),
                width - dp2px(20), height - dp2px(40));
        canvas.drawRoundRect(content, dp2px(10), dp2px(10), mPaint);
        path.moveTo(width / 2 - dp2px(10), height - dp2px(40));
        path.lineTo(width / 2, height - dp2px(20));
        path.lineTo(width / 2 + dp2px(10), height - dp2px(40));
        path.close();
        canvas.drawPath(path, mPaint);

        mWaveHeight = (height - dp2px(60)) / 20;
        float startX = width - dp2px(20);
        float startY = dp2px(20);
        path.moveTo(startX, startY);
        for (int i = 0; i < 20; i++) {
            path.lineTo(startX + mWaveHeight / 2 , startY + mWaveHeight * i + mWaveHeight / 2);
            path.lineTo(startX, startY + mWaveHeight * (i + 1));
        }
        path.close();
        canvas.drawPath(path, mPaint);

        for (int i = 0; i < 20; i++) {
            canvas.drawCircle(dp2px(20), startY + mWaveHeight * i + mWaveHeight / 2, mWaveHeight / 2, mPaint);
        }
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
