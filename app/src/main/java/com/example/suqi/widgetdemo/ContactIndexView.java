package com.example.suqi.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author suqi
 * @time 2019/1/21
 * @description
 */
public class ContactIndexView extends View {
    int perWidth, perHeight;
    Paint textPaint, selectPaint;
    int touchIndex = 0;
    Rect rect;
    OnIndexChangeListener listener;


    private String words[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public ContactIndexView(Context context) {
        this(context, null);
    }

    public ContactIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.GRAY);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(sp2px(14));
        selectPaint = new Paint();
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(Color.BLUE);
        rect = new Rect();
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
            width = dp2px(10);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = dp2px(300);
        }
        perWidth = width;
        perHeight = height / 27;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {
            //判断是不是我们按下的当前字母
            if (touchIndex == i) {
                //绘制文字圆形背景
                canvas.drawCircle(perWidth / 2, perHeight / 2 + i * perHeight, perWidth / 2, selectPaint);
                textPaint.setColor(Color.WHITE);
            } else {
                textPaint.setColor(Color.GRAY);
            }
            //获取文字的宽高
            textPaint.getTextBounds(words[i], 0, 1, rect);
            int wordWidth = rect.width();
            int wordHeight = rect.width();
            //绘制字母
            float wordX = perWidth / 2 - wordWidth / 2;
            float wordY = wordHeight / 2 + perHeight / 2 + i * perHeight;
            canvas.drawText(words[i], wordX, wordY, textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                // 确定当前选中的位置
                int index = (int) (y / perHeight);
                if (index != touchIndex && index >= 0 && index <= words.length - 1) {
                    touchIndex = index;
                    listener.onChange(touchIndex, words[touchIndex]);
                }
                invalidate();
                break;
        }
        return true;
    }

    public void setListener(OnIndexChangeListener listener) {
        this.listener = listener;
    }

    public interface OnIndexChangeListener {
        void onChange(int position, String word);
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
