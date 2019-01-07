package com.ymd.client.component.widget.myDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.WindowManager.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.utils.ToolUtil;


public class MyProgressDialog extends Dialog {

	private TextView titleView;
	private ProgressBar progressBar;
	private TextView progressTextView;
	
	public MyProgressDialog(Context context) {
		super(context, R.style.myAlertDialog);
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_progress_dialog);
		initDialogWindow();
		bindComponent();
		bindInfoAndListener();
	}

	private void bindComponent() {
		titleView = (TextView) findViewById(R.id.headTitle);
		progressBar = (ProgressBar) findViewById(R.id.progress);
		progressTextView = (TextView) findViewById(R.id.progressNum);
	}

	private void initDialogWindow() {
		DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
		
		LayoutParams p = getWindow().getAttributes();   
		p.width = (int) (dm.widthPixels * 0.8);
		
		getWindow().setAttributes(p);     
		
	}

	private void bindInfoAndListener() {
		setCanceledOnTouchOutside(false);
		setCancelable(false);
	}
	
	public void setProgress(int progress) {
		Message msg = Message.obtain(progressHandler);
		msg.what = progress;
		progressHandler.sendMessage(msg);
	}
	
	private Handler progressHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressBar.setProgress(msg.what);
		}
	};
	
	public void setMax(int progress) {
		Message msg = Message.obtain(maxHandler);
		msg.what = progress;
		maxHandler.sendMessage(msg);
	}
	
	private Handler maxHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressBar.setMax(msg.what);
		}
	};
	
	public void setProgressNum(int progress) {
		Message msg = Message.obtain(textHandler);
		msg.what = progress;
		textHandler.sendMessage(msg);
	}
	
	private Handler textHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressTextView.setText(ToolUtil.changeString(msg.what));
		}
	};
	
	public interface OnResultListener{
		
		public void onResult(Integer qty);
	}


}
