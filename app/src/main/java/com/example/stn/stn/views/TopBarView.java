package com.example.stn.stn.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.stn.stn.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Name: TopBarView
 * Author: xulong
 * Comment: 定义导航栏
 * Date: 2016-07-18 17:17.
 */
public class TopBarView extends RelativeLayout{

    private TextView tv_title;
    private ImageButton ibtn_left;
    private View v_red_tip;
    private TextView btn_right;

    private Context mContext;

    public TopBarView(Context context) {
        super(context);
    }

    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context, attrs);
    }

    
    public TopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.activity_widget_actionbar, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_left = (ImageButton) findViewById(R.id.ibtn_left);
        v_red_tip = findViewById (R.id.v_red_tip);
        btn_right = (TextView) findViewById(R.id.btn_right);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarView);
        String titleText = typedArray.getString(R.styleable.TopBarView_titleText);
        String rightText = typedArray.getString(R.styleable.TopBarView_rightText);
        Drawable leftImage = typedArray.getDrawable(R.styleable.TopBarView_leftImage);

        tv_title.setText(titleText);

        if (rightText != null && rightText.length() > 0) {
            btn_right.setText(rightText);
            btn_right.setVisibility(View.VISIBLE);
        } else {
            btn_right.setVisibility(View.GONE);
        }

        if (leftImage != null) {
            ibtn_left.setBackground(leftImage);
            ibtn_left.setVisibility(View.VISIBLE);
        } else {
            ibtn_left.setVisibility(View.GONE);
        }
        typedArray.recycle();

        ibtn_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack(mContext);
            }
        });
    }

    private void doBack(final Context context) {
        Class c = context.getClass();
        try {
            Method method = c.getMethod("onBackPressed");
            method.invoke(context);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //设置左边按钮的背景
    public void setLeftDrawable(Drawable drawable) {
        ibtn_left.setBackground(drawable);
    }

    //设置左边的按钮点击事件
    public void setOnLeftClickListener(OnClickListener listener) {
        ibtn_left.setOnClickListener(listener);
    }

    //设置左边的红点
    public void setLeftRedTipVisibility(int visibility) {
        v_red_tip.setVisibility(visibility);
    }

    //设置右键是否可见
    public void setRightVisibility(int visibility) {
        btn_right.setVisibility(visibility);
    }

    //设置右键点击事件
    public void setOnRightClickListener(OnClickListener listener) {
        btn_right.setOnClickListener(listener);
    }

    //设置标题文字
    public void setTitleText(String titleText) {
        tv_title.setText(titleText);
    }



}
