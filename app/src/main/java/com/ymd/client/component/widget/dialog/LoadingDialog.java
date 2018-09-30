package com.ymd.client.component.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymd.client.R;


/***
 * @ModuleName education
 * @ClassName RunningManDialog
 * @modify 日期 作者名 修改内容
 * @Description 该类描述   自定义加载对话框
 */
public class LoadingDialog extends Dialog {

    private TextView tv_text;

    public LoadingDialog(Context context) {
        super(context);
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);
        tv_text = (TextView) findViewById(R.id.tv_text);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public LoadingDialog setMessage(String message) {
        tv_text.setText(message);
        return this;
    }
}