package com.example.suqi.widgetdemo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author suqi
 * @time 2018/9/26
 * @description
 */
public class PanelView extends View {


    Paint paint;

    Paint paint2;

    int width;

    int height;

    int progress = 0;

    int sweepAngle = 270;

    int startAngle = 135;

    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint2 = new Paint();
        paint.setAntiAlias(true);
        setListener();
        setBackgroundColor(0xFFFF6347);
    }

    private void setListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = (int) (Math.random() * 100);
                setCurrentNumAnim((int) (900 / 100f * value));
                ValueAnimator an = ValueAnimator.ofInt(0, value);
                an.setDuration(500);
                an.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        progress = (int) animation.getAnimatedValue();
                        postInvalidate();
                    }
                });
                an.start();
            }
        });
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
            height = dp2px(300);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        drawRound(canvas);
        drawScale(canvas);
        drawIndicator(canvas);
    }

    private int[] indicatorColor = {0xffffffff, 0x00ffffff, 0x99ffffff, 0xffffffff};

    private void drawIndicator(Canvas canvas) {
        paint2.setStrokeWidth(dp2px(5));
        paint2.setColor(Color.WHITE);
        paint2.setStyle(Paint.Style.STROKE);
        RectF rectf = new RectF(dp2px(30), dp2px(30), width - dp2px(30), height - dp2px(30));
        canvas.drawArc(rectf, startAngle, sweepAngle / 100f * progress, false, paint2);
        float x = (float) ((height / 2 - dp2px(30)) * Math.cos(
                Math.toRadians(startAngle + sweepAngle / 100f * progress)));
        float y = (float) ((height / 2 - dp2px(30)) * Math.sin(
                Math.toRadians(startAngle + sweepAngle / 100f * progress)));
        paint2.setStyle(Paint.Style.FILL);
        canvas.drawCircle(height / 2 + x, height / 2 + y, dp2px(5), paint2);
    }

    private String[] text = {"较差", "中等", "良好", "优秀", "极好"};

    private void drawScale(Canvas canvas) {
        float angle = sweepAngle / 30;
        canvas.rotate(-startAngle - angle, width / 2, height / 2);

        //旋转画布
        for (int i = 0; i <= 30; i++) {
            paint.setAlpha(startAngle);
            canvas.rotate(angle, width / 2, height / 2);
            if (i % 6 == 0) {
                paint.setStrokeWidth(dp2px(2));
                drawText(canvas, i * 900 / 30 + "", paint);
            } else {
                paint.setStrokeWidth(dp2px(1));
            }
            canvas.drawLine(width / 2, dp2px(45), width / 2, dp2px(55), paint);
            if (i % 6 == 3) {
                drawText(canvas, text[(i - 3) / 6] + "", paint);
            }
        }
//        canvas.restore();
        canvas.rotate(startAngle - 30 * angle, width / 2, height / 2);
    }

    private void drawText(Canvas canvas, String text, Paint paint) {
        paint.setAlpha(255);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(sp2px(12));
        float textWidth = paint.measureText(text); //相比getTextBounds来说，这个方法获得的类型是float，更精确些//
        // Rect rect = new Rect();//
        // paint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text, (width - textWidth) / 2, dp2px(68), paint);
        paint.setStyle(Paint.Style.STROKE);

    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private void drawRound(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setAlpha(startAngle);
        paint.setStrokeWidth(dp2px(5));
        canvas.drawArc(new RectF(dp2px(30), dp2px(30), width - dp2px(30), height - dp2px(30)),
                startAngle, sweepAngle, false, paint);
        RectF secondRectF = new RectF(dp2px(50), dp2px(50),
                width - dp2px(50), height - dp2px(50));
        paint.setStrokeWidth(dp2px(10));

        canvas.drawArc(secondRectF, startAngle, sweepAngle, false, paint);
    }

    public void setCurrentNumAnim(int num) {
        ValueAnimator anim = ValueAnimator.ofInt(0, num);
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                int color = calculateColor(value);
                setBackgroundColor(color);
            }
        });
        anim.start();

    }

    private int calculateColor(int value) {
        ArgbEvaluator evealuator = new ArgbEvaluator();
        float fraction = 0;
        int color = 0;
        if (value <= 900 / 2) {
            fraction = (float) value / (900 / 2);
            color = (int) evealuator.evaluate(fraction, 0xFFFF6347, 0xFFFF8C00); //由红到橙
        } else {
            fraction = ((float) value - 900 / 2) / (900 / 2);
            color = (int) evealuator.evaluate(fraction, 0xFFFF8C00, 0xFF00CED1); //由橙到蓝
        }
        return color;
    }

}
