package com.example.suqi.widgetdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * @author suqi
 * @time 2018/11/26
 * @description
 */
public class MyGridView extends View {

    /**
     * 每个格子宽高
     */
    int perWidth = dp2px(40);

    int perHeight = dp2px(40);

    /**
     * 每条线的高度
     */
    int lineHeight = dp2px(1);

    /**
     * 选中状态下圆的半径
     */
    float radius = perWidth / 3;

    /**
     * 按下坐标
     */
    float downX;

    float downY;

    /**
     * 移动坐标
     */
    float moveX;

    float moveY;

    /**
     * 列
     */
    int column;

    /**
     * 行
     */
    int row;

    /**
     * 按下的position
     */
    int downPosition;

    /**
     * 移动中的
     */
    int movePosition;

    boolean isMove = false;

    Paint linePaint;

    Paint contentPaint;

    Paint bgPaint;

    Rect mBound;

    /**
     * 选中的position
     */
    ArrayList<Integer> selected = new ArrayList<>();

    public MyGridView(Context context) {
        this(context, null);
    }

    public MyGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(perWidth * 7 + lineHeight * 8, perHeight * 6 + lineHeight * 7);
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setAntiAlias(true);
        linePaint.setColor(getContext().getResources().getColor(R.color.colorAccent));

        contentPaint = new Paint();
        contentPaint.setStyle(Paint.Style.FILL);
        contentPaint.setAntiAlias(true);
        contentPaint.setTextSize(sp2px(14));
        mBound = new Rect();

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawGrid(canvas);
        drawContent(canvas);
    }

    private void drawBackground(Canvas canvas) {
        for (int i = 0; i < 31; i++) {
            if (contain(i)) {
                //画选中状态
                bgPaint.setColor(getContext().getResources().getColor(R.color.switch_btn_check));
                canvas.drawCircle(perWidth / 2 + (i % 7) * perWidth + (i % 7 + 1) * lineHeight,
                        perHeight / 2 + (i / 7) * perHeight + (i / 7 + 1) * lineHeight,
                        radius, bgPaint);
                //右边还有选中画右边长方形
                if (contain(i + 1)) {
                    canvas.drawRect(perWidth / 2 + (i % 7) * perWidth + (i % 7 + 1) * lineHeight,
                            perHeight / 2 + (i / 7) * perHeight - radius + (i / 7 + 1) * lineHeight,
                            (i % 7 + 1) * perWidth + (i % 7 + 2) * lineHeight,
                            perHeight / 2 + (i / 7) * perHeight + radius + (i / 7 + 1) * lineHeight, bgPaint);
                }
                //左边还有选中画左边长方形
                if (contain(i - 1)) {
                    canvas.drawRect((i % 7) * perWidth + (i % 7) * lineHeight,
                            perHeight / 2 + (i / 7) * perHeight - radius + (i / 7 + 1) * lineHeight,
                            (i % 7) * perWidth + perWidth / 2 + (i % 7 + 1) * lineHeight,
                            perHeight / 2 + (i / 7) * perHeight + radius + (i / 7 + 1) * lineHeight, bgPaint);
                }
            }
//            else {
//                bgPaint.setColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
//                canvas.drawRect((i % 7) * perWidth + (i % 7 + 1) * lineHeight,
//                        (i / 7) * perHeight + (i / 7 + 1) * lineHeight,
//                        (i % 7 + 1) * perWidth + (i % 7 + 2) * lineHeight,
//                        (i / 7 + 1) * perHeight + (i / 7 + 2) * lineHeight, bgPaint);
//            }
        }
    }

    private void drawContent(Canvas canvas) {
        for (int i = 0; i < 31; i++) {
            String text = String.valueOf(i + 1);
            contentPaint.setColor(getContext().getResources().getColor(contain(i) ? R.color.white : R.color.colorAccent));
            contentPaint.getTextBounds(text, 0, text.length(), mBound);
            //列数 i % 7   行数i / 7
            canvas.drawText(String.valueOf(i + 1), perWidth / 2 + (i % 7) * perWidth - mBound.width() / 2 + (i % 7 + 1) * lineHeight,
                    perHeight / 2 + (i / 7) * perHeight + mBound.height() / 2 + (i / 7 + 1) * lineHeight, contentPaint);
        }
    }

    private void drawGrid(Canvas canvas) {
        //画竖线
        for (int i = 0; i < 8; i++) {
            canvas.drawLine(i * perWidth + i * lineHeight, 0,
                    i * perWidth + i * lineHeight, 6 * perHeight + 6 * lineHeight, linePaint);
        }

        //画横线
        for (int i = 0; i < 7; i++) {
            canvas.drawLine(0, i * perHeight + i * lineHeight,
                    7 * perWidth + 7 * lineHeight, i * perHeight + i * lineHeight, linePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                row = (int) Math.ceil(downY / perHeight);
                column = (int) Math.ceil(downX / perWidth);
                downPosition = (row - 1) * 7 + (column - 1);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getY()) - downY >= 5 || Math.abs(event.getX() - downX) > 5) {
                    isMove = true;
                    moveX = event.getX();
                    moveY = event.getY();
                    row = (int) Math.ceil(moveY / perHeight);
                    column = (int) Math.ceil(moveX / perWidth);
                    movePosition = (row - 1) * 7 + (column - 1);
                    if (downPosition >= 0 && downPosition < 31 && movePosition >= 0 && movePosition < 31) {
                        if (movePosition >= downPosition) {
                            for (int i = downPosition; i <= movePosition; i++) {
                                setSelected(i);
                            }
                        } else {
                            for (int i = downPosition; i >= movePosition; i--) {
                                setSelected(i);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isMove) {
                    isMove = false;
                } else {
                    setSelected(downPosition);
                }
                break;
        }
        return true;
    }

    public void setSelected(int position) {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i) == position && !isMove) {
                if (selected.size() > 1) {
                    selected.remove(i);
                    postInvalidate();
                }
                return;
            }
        }
        if (!contain(position)) {
            selected.add(position);
            postInvalidate();
        }
    }

    private boolean contain(int value) {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i) == value) {
                return true;
            }
        }
        return false;
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
