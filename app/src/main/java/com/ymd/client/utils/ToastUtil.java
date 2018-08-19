package com.ymd.client.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ymd.client.R;


/**
 * 功能简介：Toast工具类，建议使用直接传String id的方法（showShort(int msgId)），
 * 其余参数不要传，这样可以避免内存泄漏，并可以把字符串资源统一放在string文件
 * 修改历史：
 */
public class ToastUtil {


    public static int RIGHT = 1;	//正确、成功
    public static int WRONG = 0;	//失败、错误
    public static int WARN = 2;		//警告、建议
    public static int ASK = 3;		//询问

    //显示弹出信息
    public static void ToastMessage(Context context, String message) {
        //	Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        try {
            View toastRoot = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_toast_show_view, null);
            Toast toast=new Toast(context);
            toast.setView(toastRoot);
            TextView tv=(TextView)toastRoot.findViewById(R.id.textItem);
            tv.setText(message);
            toast.setGravity(Gravity.CENTER, 0, 0);

            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ToastMessage(Context context, int rStr) {
        try {
            ToastMessage(context,context.getResources().getString(rStr));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ToastMessage(Context context, int rStr, int flag) {
        try {
            ToastMessage(context,context.getResources().getString(rStr),flag);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //在屏幕中间显示，带对号或者差号的弹出提示信息
    public static void ToastMessage(Context context, String message, int flag) {
        try {
            View toastRoot = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_toast_show_view, null);
            Toast toast=new Toast(context);
            toast.setView(toastRoot);
            TextView tv=(TextView)toastRoot.findViewById(R.id.textItem);
            ImageView iv = (ImageView)toastRoot.findViewById(R.id.imageIcon);
            if (flag == RIGHT) {
                iv.setImageResource(R.mipmap.right_circle_white_icon);
            }
            else {
                iv.setImageResource(R.mipmap.wrong_circle_white_icon);
            }
            tv.setText(message);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}