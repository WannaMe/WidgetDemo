package com.example.suqi.widgetdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author suqi
 * @time 2018/9/27
 * @description
 */
public class CurvesView extends View {

    Paint paint;

    Path path, progressPath;

    float moveX = dp2px(20);

    float moveY = dp2px(100);

    float downX;

    float downY;

    boolean isBack = false;

    public CurvesView(Context context) {
        this(context, null);
    }

    public CurvesView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CurvesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        path = new Path();
        progressPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = dp2px(340);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = dp2px(300);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2px(4));
        path.reset();
        path.moveTo(dp2px(20), dp2px(100));
        if (isBack && moveY > dp2px(100)) {
            ValueAnimator animator = ValueAnimator.ofFloat(moveY - 10, dp2px(100));
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    moveY = (float) animation.getAnimatedValue();
                    invalidate();
                    if (moveY == dp2px(100))
                        isBack = false;
                }
            });
            animator.start();
        }
        path.quadTo(moveX, moveY, dp2px(300), dp2px(100));
        canvas.drawPath(path, paint);
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        path.reset();
        path.moveTo(dp2px(20), dp2px(250));
        path.lineTo(dp2px(70), dp2px(200));
        path.lineTo(dp2px(100), dp2px(250));
        path.lineTo(dp2px(130), dp2px(250));
        path.lineTo(dp2px(130), dp2px(200));
        path.lineTo(dp2px(180), dp2px(200));
        path.lineTo(dp2px(180), dp2px(250));
        canvas.drawPath(path, paint);

        paint.setColor(Color.GRAY);
        progressPath.moveTo(dp2px(20), dp2px(150));
        progressPath.lineTo(dp2px(200), dp2px(150));
        canvas.drawPath(progressPath, paint);
        progressPath.reset();
        paint.setColor(Color.BLUE);
        progressPath.moveTo(dp2px(20), dp2px(150));
        progressPath.lineTo(dp2px(100), dp2px(150));
        canvas.drawPath(progressPath, paint);

        canvas.drawLine(dp2px(20), dp2px(200), dp2px(150), dp2px(200), paint);
        canvas.drawLine(dp2px(20), dp2px(200), dp2px(100), dp2px(200), paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() - downY >= 0) {
                    moveX = event.getX();
                    moveY = dp2px(100) + event.getY() - downY;
                    if (event.getY() - downY > dp2px(100))
                        moveY = dp2px(200);
                    if (event.getX() < dp2px(20))
                        moveX = dp2px(20);
                    if (event.getX() > dp2px(300))
                        moveX = dp2px(300);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isBack = true;
                invalidate();
                break;
        }
        return true;
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
