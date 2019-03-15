package com.example.suqi.widgetdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author suqi
 * @time 2018/9/27
 * @description
 */
public class CurvesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curves);
        ParamItemView p1 = findViewById(R.id.line1);
        ParamItemView p2 = findViewById(R.id.line2);
        ParamItemView p3 = findViewById(R.id.line3);
        p1.init();
        p2.init();
        p3.init();
    }
}
