package com.example.suqi.widgetdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author suqi
 * @time 2018/12/29
 * @description
 */
public class ParamItemView extends RelativeLayout {

    private ImageView ivTop;
    private TextView tvVaule;
    private TextView tvUnit;
    private TextView tvInfo;

    public ParamItemView(Context context) {
        super(context);
    }

    public ParamItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParamItemView init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_param, this, true);
        ivTop = findViewById(R.id.iv_top);
        tvVaule = findViewById(R.id.tv_value);
        tvUnit = findViewById(R.id.tv_unit);
        tvInfo = findViewById(R.id.tv_info);
        return this;
    }

    public ParamItemView setImage(int res) {
        ivTop.setImageResource(res);
        return this;
    }

    public ParamItemView setContent(String content) {
        tvVaule.setText(content);
        return this;
    }

    public ParamItemView setInfo(String content) {
        tvInfo.setText(content);
        return this;
    }

    public ParamItemView setUnit(String unit) {
        tvUnit.setText(unit);
        return this;
    }
}
