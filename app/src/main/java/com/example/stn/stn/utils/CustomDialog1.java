package com.example.stn.stn.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Name: CustomDialog1
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-12 10:51.
 */
public class CustomDialog1 {

    /**
     * 底部对话框
     * @param context
     * @param layout
     * @param animation
     * @param showSoftInput
     * @return
     */
    public static AlertDialog createBottomDialog(Context context, int layout, int animation, boolean showSoftInput) {
        return createDialog(context, layout, animation, showSoftInput, Gravity.BOTTOM);
    }


    /**
     * 顶部对话框
     * @param context
     * @param layout
     * @param animation
     * @param showSoftInput
     * @return
     */
    public static AlertDialog createTopDialog(Context context, int layout, int animation, boolean showSoftInput) {
        return createDialog(context, layout, animation, showSoftInput, Gravity.TOP);
    }

    /**
     * 居中对话框
     * @param context
     * @param layout
     * @param showSoftInput
     * @return
     */
    public static AlertDialog createCenterDialog(Context context, int layout, boolean showSoftInput) {
        return createDialog(context, layout, showSoftInput, Gravity.CENTER);
    }


    /**
     * 创建对话框
     * @param context
     * @param layout
     * @param showSoftInput
     * @param gravity
     * @return
     */

    public static AlertDialog createDialog(Context context, int layout,
                                           boolean showSoftInput, int gravity) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height =WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        window.setAttributes(lp);
        return dialog;
    }

    public static AlertDialog createDialog(Context context, int layout, int animation,
                                           boolean showSoftInput, int gravity) {
        AlertDialog dialog = CustomDialog1.createDialog(context, layout, showSoftInput, gravity);
        dialog.getWindow().setWindowAnimations(animation);
        return dialog;
    }

}
