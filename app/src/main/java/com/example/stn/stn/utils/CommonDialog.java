package com.example.stn.stn.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.stn.stn.R;

/**
 * Created by ASUS on 2017/12/25.
 */

public class CommonDialog extends AlertDialog implements View.OnClickListener {

    private  Context mContext;
    private String title;
    private Button submitTxt;
    private TextView dialogContent;
    private String content;

    public CommonDialog(Context context,String str) {
        super(context);
        this.mContext = context;
        this.content = str;

    }

    public CommonDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }
    public CommonDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }
    private OnCloseListener listener;

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        initView();
    }
    public CommonDialog setPositiveButton(String name){
        return this;
    }
    public CommonDialog setTitle(String title){
        this.title = title;
        return this;
    }
    @Override
    public void onClick(View v) {
        //listener.onClick(this, true);
        this.dismiss();
    }
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commomdialog);
        setCanceledOnTouchOutside(false);
        initView();
    }
    public void initView(){
        submitTxt = (Button)findViewById(R.id.dialog_next);
        submitTxt.setOnClickListener(this);
        this.dialogContent = (TextView) findViewById(R.id.tv_dialog_content);
        this.dialogContent.setText(content);
    }

}
