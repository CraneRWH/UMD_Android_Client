package com.ymd.client.component.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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

    private Context context = null;
    private static LoadingDialog runningManDialog = null;

    private LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;

    }

    /***
     * 创建对话框对象
     *
     * @param context
     * @return
     */
    public static LoadingDialog createDialog(Context context) {
        return createDialog(context, null);
    }

    /***
     * 创建对话框对象
     *
     * @param context
     * @param message
     * @return
     */
    public static LoadingDialog createDialog(Context context, String message) {
        runningManDialog = new LoadingDialog(context, R.style.running_man_dialog);
        runningManDialog.setContentView(R.layout.progress_dialog);
        runningManDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView tvMsg = (TextView) runningManDialog.findViewById(R.id.loadingTv);
        if (tvMsg != null && !TextUtils.isEmpty(message)) {
            tvMsg.setText(message);
        }
        return runningManDialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (runningManDialog == null) {
            return;
        }
        ImageView imageView = (ImageView) runningManDialog.findViewById(R.id.loadingIv);
        imageView.setBackgroundResource(R.drawable.dialog_runningman);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        final AnimationDrawable mAnimation = (AnimationDrawable) imageView.getBackground();
        imageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
    }

    /**
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public void setMessage(String strMessage) {
        TextView tvMsg = (TextView) runningManDialog.findViewById(R.id.loadingTv);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
    }

}