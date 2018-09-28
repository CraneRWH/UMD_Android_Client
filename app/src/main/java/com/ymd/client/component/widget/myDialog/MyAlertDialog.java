package com.ymd.client.component.widget.myDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;

public class MyAlertDialog extends Dialog {

	private LinearLayout dialogLayout;
	private LinearLayout bottomLayout;
	private LinearLayout headLayout;
	private Button POSITIVE_BUTTON;
	private Button NEGATIVE_BUTTON;
	private Button NEUTRAL_BUTTON;
	
	private TextView titleView;
	private TextView noticeView;
	private TextView messageView;
	
	private ImageView headImageView;
	//private View lineView;
	
	private Context context;
	
	private double width_weight = 0.4;
	
	/*public SureListener sureListener;
	public CancelListener cancelListener;*/
	
	public MyAlertDialog(Context context) {
		super(context,R.style.myAlertDialog);
		this.context = context;
	}

	public MyAlertDialog(Context context, double width) {
		super(context,R.style.myAlertDialog);
		this.context = context;
		this.width_weight = width;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.my_dialog_alert);
		initDialogWindow();
		bindComponent();
		bindInfoAndListener();
	}

	private void initDialogWindow() {
		DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
		
		LayoutParams p = getWindow().getAttributes();   
		p.width = (int) (dm.widthPixels * width_weight);

		getWindow().setAttributes(p);     
	}

	private void bindInfoAndListener() {
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
	}

	private void bindComponent() {
		dialogLayout = (LinearLayout) findViewById(R.id.dialogLayout);
		POSITIVE_BUTTON = (Button) findViewById(R.id.POSITIVE_BUTTON);		
		NEGATIVE_BUTTON = (Button) findViewById(R.id.NEGATIVE_BUTTON);
		NEUTRAL_BUTTON = (Button) findViewById(R.id.NEUTRAL_BUTTON);
		
		headLayout = (LinearLayout) findViewById(R.id.head_bar);
		bottomLayout = (LinearLayout) findViewById(R.id.layout);
		titleView = (TextView) findViewById(R.id.headTitle);
		noticeView = (TextView) findViewById(R.id.content);
		messageView = (TextView) findViewById(R.id.messageView);

		//lineView = (View) findViewById(R.id.view);
	}

	public void setTitle(String text) {		//设置dialog的标题
		headLayout.setVisibility(View.VISIBLE);
		titleView.setText(text);
	}
	public void setTitle(int textID) {		//设置dialog的标题
		headLayout.setVisibility(View.VISIBLE);
		titleView.setText(context.getResources().getString(textID));
	}

	public void setMessage(String text) {		//设置dialog的内容
		messageView.setVisibility(View.VISIBLE);
		messageView.setText(text);
	}
	public void setMessage(int textID) {		//设置dialog的内容
		messageView.setVisibility(View.VISIBLE);
		messageView.setText(context.getResources().getString(textID));
	}

	public void setNotice(String text) {		//设置dialog的内容
		noticeView.setVisibility(View.VISIBLE);
		noticeView.setText(text);
	}
	public void setNotice(int textID) {		//设置dialog的内容
		noticeView.setVisibility(View.VISIBLE);
		noticeView.setText(context.getResources().getString(textID));
	}

	public void setNeutralButton(String text,View.OnClickListener clickListener) {		//设置dialog的清空按钮
		bottomLayout.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setText(text);
		NEUTRAL_BUTTON.setOnClickListener(clickListener);
	}
	public void setNeutralButton(int textId,View.OnClickListener clickListener) {		//设置dialog的清空按钮
		bottomLayout.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setText(context.getResources().getString(textId));
		NEUTRAL_BUTTON.setOnClickListener(clickListener);
	}
	public void setNeutralButton(String text) {			//设置dialog的清空按钮
		bottomLayout.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setText(text);
		NEUTRAL_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	public void setNeutralButton(int textId) {			//设置dialog的清空按钮
		bottomLayout.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setVisibility(View.VISIBLE);
		NEUTRAL_BUTTON.setText(context.getResources().getString(textId));
		NEUTRAL_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setNegativeButton(String text, final CancelListener cancelListener) {		//设置dialog的取消按钮
		bottomLayout.setVisibility(View.VISIBLE);
	//	lineView.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setText(text);
		NEGATIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cancelListener.onCancelListener();
				dismiss();
			}
		});
	}
	public void setNegativeButton(int textId, final CancelListener cancelListener) {		//设置dialog的取消按钮
		bottomLayout.setVisibility(View.VISIBLE);
	//	lineView.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setText(context.getResources().getString(textId));
		NEGATIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cancelListener.onCancelListener();
				dismiss();
			}
		});
	}
	public void setNegativeButton(String text) {
		bottomLayout.setVisibility(View.VISIBLE);
	//	lineView.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setText(text);
		NEGATIVE_BUTTON.setOnClickListener(new View.OnClickListener() {		//设置dialog的取消按钮

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	public void setNegativeButton(int textId) {			//设置dialog的取消按钮
		bottomLayout.setVisibility(View.VISIBLE);
	//	lineView.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setText(context.getResources().getString(textId));
		NEGATIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setNegativeButton(final CancelListener cancelListener) {		//设置dialog的取消按钮
		bottomLayout.setVisibility(View.VISIBLE);
	//	lineView.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setVisibility(View.VISIBLE);
		NEGATIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cancelListener.onCancelListener();
				dismiss();
			}
		});
	}

	public void setPositiveButton(String text,final SureListener sureListener) {		//设置dialog的确认按钮
		bottomLayout.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setText(text);
		POSITIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sureListener.onSureListener();
				dismiss();
			}
		});
	}

	public void setPositiveButton(int textId,final SureListener sureListener) {		//设置dialog的确认按钮
		bottomLayout.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setText(context.getResources().getString(textId));
		POSITIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sureListener.onSureListener();
				dismiss();
			}
		});
	}
	public void setPositiveButton(String text) {		//设置dialog的确认按钮
		bottomLayout.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setText(text);
		POSITIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	public void setPositiveButton(int textId) {			//设置dialog的确认按钮
		bottomLayout.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setText(context.getResources().getString(textId));
		POSITIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setPositiveButton(final SureListener sureListener) {		//设置dialog的确认按钮
		bottomLayout.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setVisibility(View.VISIBLE);
		POSITIVE_BUTTON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sureListener.onSureListener();
				dismiss();
			}
		});
	}
	
	public Button getButton(int buttonId) {
		return (Button)findViewById(buttonId);
	}
	
	public void addView(View dialogView) {
		dialogLayout.removeAllViews();
		dialogLayout.addView(dialogView);
	}
	
	public interface OnResultListener{
		
		public void onResult(Integer qty);
	}

	public interface SureListener{
		public void onSureListener();
	}
	
	public interface CancelListener{
		public void onCancelListener();
	}
}
