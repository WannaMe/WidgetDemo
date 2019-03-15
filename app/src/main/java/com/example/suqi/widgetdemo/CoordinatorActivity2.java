package com.example.suqi.widgetdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @author suqi
 * @time 2018/12/20
 * @description
 */
public class CoordinatorActivity2 extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coor2);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setMinimumHeight(getStatusBarHeight(this) + dp2px(this, 50));
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        //获取状态栏高度的资源
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("getStatusBarHeight", result + "");
        return result;
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
