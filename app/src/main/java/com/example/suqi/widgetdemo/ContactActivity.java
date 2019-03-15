package com.example.suqi.widgetdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suqi
 * @time 2019/1/21
 * @description
 */
public class ContactActivity extends AppCompatActivity implements ContactIndexView.OnIndexChangeListener{

    ContactIndexView indexView;
    TextView textView;
    WaveView waveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        indexView = findViewById(R.id.indexView);
        waveView = findViewById(R.id.waveview);
        textView = findViewById(R.id.show);
        indexView.setListener(this);
        waveView.setWaveColor(
                Color.parseColor("#88b8f1ed"),
                Color.parseColor("#b8f1ed"));

        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                waveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        waveShiftAnim.start();

    }

    @Override
    public void onChange(int position, String word) {
        textView.setText("  index:  " + position + "   content:  " + word);
    }
}
