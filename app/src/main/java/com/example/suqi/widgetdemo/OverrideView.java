package com.example.suqi.widgetdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.hardware.camera2.CameraManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author suqi
 * @time 2018/8/13
 * @description
 */
public class OverrideView extends View {

    float[] points = {150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
    Paint paint;
    Path path;

    public OverrideView(Context context) {
        super(context);
    }

    public OverrideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorAccent));

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 200, paint);
        paint.setTextSize(100);
        canvas.drawText("诶呀", canvas.getWidth() / 2 - 100, canvas.getHeight() / 2, paint);
        Rect rect = new Rect(100, 200, 300, 400);
        canvas.drawRect(rect, paint);
        RectF oval =  new RectF(canvas.getWidth() / 2, 200, canvas.getWidth() / 2 + 400, 500);
        canvas.drawOval(oval, paint);
        canvas.drawLine(100, 100, 500, 100, paint);
        canvas.drawLines(points, paint);
        RectF roundRect = new RectF(100, 500, 300, 700);
        canvas.drawRoundRect(roundRect, 50, 50, paint);
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        path.close();
        canvas.drawPath(path, paint);

        Shader shader = new LinearGradient(100, canvas.getHeight() / 2 + 200, 500, canvas.getHeight() / 2 + 600, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(300, canvas.getHeight() / 2 + 400, 200, paint);

        Shader shader1 = new RadialGradient(800, canvas.getHeight() / 2 + 400, 200, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint.setShader(shader1);
        canvas.drawCircle(800, canvas.getHeight() / 2 + 400, 200, paint);
    }

}
