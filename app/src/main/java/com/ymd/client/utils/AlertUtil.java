package com.ymd.client.utils;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ymd.client.R;
import com.ymd.client.component.widget.dialog.MyDialog;
import com.ymd.client.component.widget.dialog.MyDialog.SureListener;
import com.ymd.client.component.widget.dialog.MyDialog.CancelListener;

/**
 * 弹出框或者提示框
 */
public class AlertUtil {

	public static MyDialog dialog = null;

	public static int RIGHT = 1;	//正确、成功
	public static int WRONG = 0;	//失败、错误
	public static int WARN = 2;		//警告、建议
	public static int ASK = 3;		//询问

	public static void DialogMessage(Context c,String title, int flag) {
		DialogMessage(c,title, null, flag, 0.8 , null, null, null,null);
	}

	public static void DialogMessage(Context c,String message, int flag, double bl) {
		DialogMessage(c, null, message, flag, bl, null, null, null,null);
	}

	public static void DialogMessage(Context c,String title,String message, int flag,double bl) {
		DialogMessage(c,title, message, flag, bl, null, null , null,null);
	}

	public static void DialogMessage(Context c,String message, int flag, MyDialog.SureListener sureListener) {
		DialogMessage(c,null, message, flag, 0.8, null, null, sureListener, null);
	}

	public static void DialogMessage(Context c,
									 String title,	//弹出框标题
									 String message, //弹出框的内容
									 int flag,	//显示图标的样式
									 double bl,	//弹出框宽度比例
									 String sureStr,	//确认按钮显示文字
									 String cancelStr,	//取消按钮显示的文字
									 MyDialog.SureListener sureListener,	//确认按钮监听
									 MyDialog.CancelListener cancelListener	//取消按钮监听
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

	//显示弹出信息
	public static void ToastMessage(Context context,String message) {
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

	public static void ToastMessage(Context context,int rStr) {
		try {
			ToastMessage(context,context.getResources().getString(rStr));
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ToastMessage(Context context,int rStr,int flag) {
		try {
			ToastMessage(context,context.getResources().getString(rStr),flag);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//在屏幕中间显示，带对号或者差号的弹出提示信息
	public static void ToastMessage(Context context,String message,int flag) {
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

	//正确、成功弹出框
	public static void RightDialog(Context c,String message, SureListener sureListener) {
		DialogMessage(c,null, message, 1, 0.8, null, null, sureListener, null);
	}
	public static void RightDialog(Context c,String message) {
		DialogMessage(c,null, message, 1, 0.8, null, null, null, null);
	}

	//错误、失败弹出框
	public static void FailDialog(Context c,String message, SureListener sureListener) {
		DialogMessage(c,null, message, 0, 0.8, null, null, sureListener, null);
	}
	public static void FailDialog(Context c,String message) {
		DialogMessage(c,null, message, 0, 0.8, null, null, null, null);
	}

	//警告、建议弹出框
	public static void WarnDialog(Context c,String message, SureListener sureListener) {
		DialogMessage(c,null, message, 2, 0.8, null, null, sureListener, null);
	}
	public static void WarnDialog(Context c,String message) {
		DialogMessage(c, null, message, 2, 0.8, null, null, null, null);
	}

	//询问弹出框
	public static void AskDialog(Context c,String message ) {
		DialogMessage(c,null, message, 3, 0.8, null, null, null, null);
	}
	public static void AskDialog(Context c, String message, SureListener sureListener, CancelListener cancelListener) {
		DialogMessage(c,null, message, 3, 0.8, null, null, sureListener, cancelListener);
	}
	public static void AskDialog(Context c,String message, SureListener sureListener) {
		DialogMessage(c,null, message, 3, 0.8, null, null, sureListener, null);
	}
	public static void AskDialog(Context c,String message, String sureStr, SureListener sureListener) {
		DialogMessage(c,null, message, 3, 0.8, sureStr, null, sureListener, null);
	}
	public static void AskDialog(Context c,String message,
								 String sureStr,	//确认按钮显示文字
								 String cancelStr	//取消按钮显示的文字
	){
		DialogMessage(c,null, message, 3, 0.8, sureStr, cancelStr, null, null);
	}
	public static void AskDialog(Context c, String message,
								 String sureStr,    //确认按钮显示文字
								 String cancelStr,    //取消按钮显示的文字
								 SureListener sureListener,    //确认按钮监听
								 CancelListener cancelListener    //取消按钮监听
	) {
		DialogMessage(c, null, message, 3, 0.8, sureStr, cancelStr, sureListener, cancelListener);
	}
}
