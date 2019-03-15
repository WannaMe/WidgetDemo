package com.example.suqi.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author suqi
 * @time 2018/10/19
 * @description
 */
public class ScrollBar extends View {

    Paint paint;

    int width;

    int height;

    String thumbValue = "5";

    String leftText = "16";

    String rightText = "36";

    int thumbX;

    int thumbY;

    float downX;

    float downY;

    boolean isMove;

    int thumbRadius = dp2px(9);

    public ScrollBar(Context context) {
        this(context, null);
    }

    public ScrollBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
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
            height = dp2px(60);
        }
        thumbY = height / 2;
        thumbX = dp2px(10);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);

        //前面圆圈被遮挡时
        if (dp2px(10) <= thumbX && thumbX <= dp2px(19)) {
            canvas.drawLine(thumbX + thumbRadius, height / 2, width - dp2px(10), height / 2, paint);
            canvas.drawCircle(width - dp2px(10), height / 2, 4, paint);
        }
        if (dp2px(19) < thumbX && thumbX < width - dp2px(19)) {
            canvas.drawCircle(dp2px(10), height / 2, 4, paint);
            canvas.drawCircle(width - dp2px(10), height / 2, 4, paint);
            canvas.drawLine(dp2px(10), height / 2, thumbX - thumbRadius, height / 2, paint);
            canvas.drawLine(thumbX + thumbRadius, height / 2, width - dp2px(10), height / 2, paint);
        }
        if (width - dp2px(19) <= thumbX && thumbX <= width - dp2px(10)) {
            canvas.drawCircle(dp2px(10), height / 2, 4, paint);
            canvas.drawLine(dp2px(10), height / 2, thumbX - thumbRadius, height / 2, paint);
        }

        paint.setTextSize(sp2px(12));
        Rect rect = new Rect();
        paint.getTextBounds(leftText, 0, leftText.length(), rect);
        canvas.drawText(leftText, dp2px(10) - rect.width() / 2, height / 2 + dp2px(20) + rect.height(), paint);
        paint.getTextBounds(rightText, 0, rightText.length(), rect);
        canvas.drawText(rightText, width - dp2px(10) - rect.width() / 2, height / 2 + dp2px(20) + rect.height(), paint);
        paint.getTextBounds(thumbValue, 0, thumbValue.length(), rect);
        canvas.drawText(thumbValue, thumbX - rect.width() / 2, thumbY - thumbRadius - dp2px(10), paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(thumbX, thumbY, thumbRadius, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                int distanceX = (int) Math.abs(downX - thumbX);
                int distanceY = (int) Math.abs(downY - thumbY);
                Log.d("scroll", "downX == " + downX + " == downY == "
                        + downY + " == distanceX == " + distanceX + " == distanceY == " + distanceY);
                int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                if (distanceZ > thumbRadius + dp2px(10)) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("scroll", "thumbX == " + thumbX + " == eventX == " + event.getX() + " == value == " + (event.getX() - downX));
                thumbX = (int) (thumbX + event.getX() - downX);
                if (thumbX <= dp2px(10)) {
                    thumbX = dp2px(10);
                }
                if (thumbX >= width - dp2px(10)) {
                    thumbX = width - dp2px(10);
                }
                thumbValue = String.valueOf(thumbX);
                downX = event.getX();
                downY = event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
