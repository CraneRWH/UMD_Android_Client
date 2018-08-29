package com.ymd.client.component.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.utils.ScreenUtil;

/**
 * @author zhl
 * @class com.ymaidan.client.widget
 * @time 2018/6/14 0014 13:16
 * @description dialog类
 */
public class CommonDialogs {
    public final static int SELECT_DIALOG = 1;
    public final static int RADIO_DIALOG = 2;

    /**
     * 创建一个内容多选对话框
     *
     * @param context
     * @param title                   标题
     * @param items                   数组
     * @param dialogItemClickListener 监听点击的内容结果
     * @return
     */
    public static Dialog showListDialog(Context context,
                                        String title, String[] items,
                                        final DialogItemClickListener dialogItemClickListener) {
        return ShowDialog(context, title, items, dialogItemClickListener);
    }

    /**
     * 创建一个单选对话框
     *
     * @param context
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showRadioDialog(Context context,
                                         String toast, final DialogClickListener dialogClickListener) {
        return ShowDialog(context,
                context.getResources().getString(R.string.pointMessage), toast, context.getResources().getString(R.string.sure), "取消",
                dialogClickListener, RADIO_DIALOG);
    }

    /**
     * 创建一个选择对话框
     *
     * @param context
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showSelectDialog(Context context,
                                          String toast, final DialogClickListener dialogClickListener) {
        return ShowDialog(context,
                context.getResources().getString(R.string.pointMessage), toast, context.getResources().getString(R.string.sure), "取消",
                dialogClickListener, SELECT_DIALOG);
    }

    /**
     * 创建一个选择对话框
     *
     * @param context
     * @param title               提示标题
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showSelectDialog(Context context,
                                          String title, String toast,
                                          final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, toast, context.getResources().getString(R.string.sure), "取消", dialogClickListener,
                SELECT_DIALOG);
    }

    /**
     * 创建一个选择对话框
     *
     * @param context
     * @param title               提示标题
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showSelectDialog(Context context,
                                          String title, String toast, String comfirm, String cancel,
                                          final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, toast, comfirm, cancel, dialogClickListener,
                SELECT_DIALOG);
    }

    /**
     * 创建一个选择对话框
     *
     * @param context
     * @param title               提示标题
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public static Dialog showSelectDialog(Context context,
                                          String title, String toast, String comfirm,
                                          final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, toast, comfirm, "取消", dialogClickListener,
                SELECT_DIALOG);
    }

    public static Dialog showOneBtnDialog(Context context,
                                          String title, String toast,
                                          final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, toast, context.getResources().getString(R.string.sure), "", dialogClickListener,
                RADIO_DIALOG);
    }

    private static Dialog ShowDialog(Context context, String title,
                                     String toast, String confirm, String cancel, final DialogClickListener dialogClickListener,
                                     int DialogType) {
        final Dialog dialog = new Dialog(context,
                R.style.DialogStyle);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        ((TextView) view.findViewById(R.id.toast)).setText(toast);
        if (DialogType == RADIO_DIALOG) {
            ((TextView) view.findViewById(R.id.ok)).setText(confirm);
        } else {
            ((TextView) view.findViewById(R.id.confirm)).setText(confirm);
            ((TextView) view.findViewById(R.id.cancel)).setText(cancel);
            view.findViewById(R.id.ok).setVisibility(View.GONE);
            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.cancel();
                            }
                        }, 200);
                    }
                });
        view.findViewById(R.id.confirm).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.confirm();
                            }
                        }, 200);
                    }
                });
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = ScreenUtil.getScreenHeightPix(context) / 10 * 8;
        } else {
            lp.width = ScreenUtil.getScreenWidthPix(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();

        return dialog;
    }

    private static Dialog ShowDialog(Context context, String title,
                                     String[] items, final DialogItemClickListener dialogClickListener) {
        final Dialog dialog = new Dialog(context,
                R.style.DialogStyle);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_radio,
                null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        // 根据items动态创建
        LinearLayout parent = (LinearLayout) view
                .findViewById(R.id.dialogLayout);
        parent.removeAllViews();
        int length = items.length;
        for (int i = 0; i < items.length; i++) {
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(-1, ScreenUtil.dip2px(context, 48));
            params1.rightMargin = 1;
            final TextView tv = new TextView(context);
            tv.setLayoutParams(params1);
            tv.setTextSize(16);
            tv.setText(items[i]);
            tv.setTextColor(context.getResources().getColor(R.color.common_color_bg));
            int pad = context.getResources().getDimensionPixelSize(
                    R.dimen.mar_pad_len_20px);
            tv.setPadding(pad, pad, pad, pad);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);


            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    dialogClickListener.confirm(tv.getText().toString());
                }
            });
            parent.addView(tv);
            if (i != length - 1) {
                tv.setBackgroundResource(R.drawable.dialog_item_middle_background);

                TextView divider = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, 1);
                divider.setLayoutParams(params);
                divider.setBackgroundResource(R.color.common_color_gray_bg);
                parent.addView(divider);
            } else {
                tv.setBackgroundResource(R.drawable.dialog_item_bottom_background);
            }
        }
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = ScreenUtil.getScreenWidthPix(context);
        mWindow.setGravity(Gravity.BOTTOM);
        //dialog_btn_bg
        // 添加动画
        mWindow.setWindowAnimations(R.style.dialogAnim);
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public interface DialogItemClickListener {
        void confirm(String result);
    }

    public interface DialogClickListener {
        void confirm();

        void cancel();
    }
}
