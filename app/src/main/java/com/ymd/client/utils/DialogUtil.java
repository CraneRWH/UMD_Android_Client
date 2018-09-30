package com.ymd.client.utils;

import android.content.Context;

import com.ymd.client.R;
import com.ymd.client.component.widget.dialog.LoadingDialog;
import com.ymd.client.component.widget.dialog.MyDialog;


/**
 * 弹出框或者提示框
 */
public class DialogUtil {

	public static MyDialog dialog = null;

	public static int RIGHT = 1;	//正确、成功
	public static int WRONG = 0;	//失败、错误
	public static int WARN = 2;		//警告、建议
	public static int ASK = 3;		//询问

	public static void DialogMessage(Context c, String title, int flag) {
		DialogMessage(c,title, null, flag, 0.8 , null, null, null,null);
	}

	public static void DialogMessage(Context c, String message, int flag, double bl) {
		DialogMessage(c, null, message, flag, bl, null, null, null,null);
	}

	public static void DialogMessage(Context c, String title, String message, int flag, double bl) {
		DialogMessage(c,title, message, flag, bl, null, null , null,null);
	}

	public static void DialogMessage(Context c, String message, int flag, MyDialog.SureListener sureListener) {
		DialogMessage(c,null, message, flag, 0.8, null, null, sureListener, null);
	}

	public static void DialogMessage(Context c,
                                     String title,    //弹出框标题
                                     String message, //弹出框的内容
                                     int flag,    //显示图标的样式
                                     double bl,    //弹出框宽度比例
                                     String sureStr,    //确认按钮显示文字
                                     String cancelStr,    //取消按钮显示的文字
                                     MyDialog.SureListener sureListener,    //确认按钮监听
                                     MyDialog.CancelListener cancelListener    //取消按钮监听
	) {
		DialogMessage(c,title, message, null, flag, 0.8, sureStr, cancelStr, sureListener, cancelListener);
	}

	public static void DialogMessage(Context c,
                                     String title,    //弹出框标题
                                     String message, //弹出框的内容
                                     String content,    //左对齐内容
                                     int flag,    //显示图标的样式
                                     double bl,    //弹出框宽度比例
                                     String sureStr,    //确认按钮显示文字
                                     String cancelStr,    //取消按钮显示的文字
                                     MyDialog.SureListener sureListener,    //确认按钮监听
                                     MyDialog.CancelListener cancelListener    //取消按钮监听
	) {

		try {
			if (dialog != null) {
				dialog.dismiss();
			}
			dialog = null;
			dialog = new MyDialog(c, bl);
			dialog.show();
			dialog.setHeadImage(flag);
			if (title != null) {
				dialog.setTitle(title);
			}
			if (message != null) {
				dialog.setMessage(message);
			}
			if (content != null) {
				dialog.setNotice(content);
			}
			if (sureStr != null) {
				dialog.setPositiveButton(sureStr);
			} else {
				dialog.setPositiveButton(R.string.sure_str);
			}
			if (sureListener != null) {
				dialog.setCancelable(false);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setPositiveButton(sureListener);
			}
			if (flag == ASK) {
				dialog.setNegativeButton(R.string.cancel_str);
			}
			if (cancelStr != null) {
				dialog.setNegativeButton(cancelStr);
			}
			if (cancelListener != null) {
				dialog.setCancelable(false);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setNegativeButton(cancelListener);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}



	//正确、成功弹出框
	public static void RightDialog(Context c, String message, MyDialog.SureListener sureListener) {
		DialogMessage(c,null, message, 1, 0.8, null, null, sureListener, null);
	}
	public static void RightDialog(Context c, String message) {
		DialogMessage(c,null, message, 1, 0.8, null, null, null, null);
	}

	//错误、失败弹出框
	public static void FailDialog(Context c, String message, MyDialog.SureListener sureListener) {
		DialogMessage(c,null, message, 0, 0.8, null, null, sureListener, null);
	}
	public static void FailDialog(Context c, String message) {
		DialogMessage(c,null, message, 0, 0.8, null, null, null, null);
	}

	//警告、建议弹出框
	public static void WarnDialog(Context c, String message, MyDialog.SureListener sureListener) {
		DialogMessage(c,null, message, 2, 0.8, null, null, sureListener, null);
	}
	public static void WarnDialog(Context c, String message) {
		DialogMessage(c, null, message, 2, 0.8, null, null, null, null);
	}

	//询问弹出框
	public static void AskDialog(Context c, String message ) {
		DialogMessage(c,null, message, 3, 0.8, null, null, null, null);
	}
	public static void AskDialog(Context c, String message, MyDialog.SureListener sureListener, MyDialog.CancelListener cancelListener) {
		DialogMessage(c,null, message, 3, 0.8, null, null, sureListener, cancelListener);
	}
	public static void AskDialog(Context c, String message, MyDialog.SureListener sureListener) {
		DialogMessage(c,null, message, 3, 0.8, null, null, sureListener, null);
	}
	public static void AskDialog(Context c, String message, String sureStr, MyDialog.SureListener sureListener) {
		DialogMessage(c,null, message, 3, 0.8, sureStr, null, sureListener, null);
	}
	public static void AskDialog(Context c, String message,
                                 String sureStr,    //确认按钮显示文字
                                 String cancelStr    //取消按钮显示的文字
	){
		DialogMessage(c,null, message, 3, 0.8, sureStr, cancelStr, null, null);
	}
	public static void AskDialog(Context c, String message,
                                 String sureStr,    //确认按钮显示文字
                                 String cancelStr,    //取消按钮显示的文字
                                 MyDialog.SureListener sureListener,    //确认按钮监听
                                 MyDialog.CancelListener cancelListener    //取消按钮监听
	) {
		DialogMessage(c, null, message, 3, 0.8, sureStr, cancelStr, sureListener, cancelListener);
	}

	/**
	 * 自定义等待对话框
	 *
	 * @param context             上下文对象
	 * @param message             内容提示
	 * @param isCancelableOutside 是否可点击外部取消对话框
	 */
	public static LoadingDialog showProgrssDialog(Context context, String message, boolean isCancelableOutside) {
		LoadingDialog dialog = new LoadingDialog(context);
		dialog.setMessage(message);
		dialog.setCanceledOnTouchOutside(isCancelableOutside);
		return dialog;
	}

	/**
	 * 自定义等待对话框
	 *
	 * @param context 上下文对象
	 * @param message 内容提示
	 */
	public static LoadingDialog showProgrssDialog(Context context, String message) {
		return showProgrssDialog(context, message, false);
	}

	/**
	 * 自定义等待对话框
	 *
	 * @param context 上下文对象
	 */
	public static LoadingDialog showProgrssDialog(Context context) {
		return showProgrssDialog(context, null);
	}
}
