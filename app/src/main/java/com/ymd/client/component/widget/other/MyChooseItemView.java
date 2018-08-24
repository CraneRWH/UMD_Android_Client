package com.ymd.client.component.widget.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.utils.ToolUtil;

/**
 * 选项卡的卡头(横向排列)
 * @author ronweihe
 */
public class MyChooseItemView extends LinearLayout {

	private TextView itemView;
	private TextView warnView;
	private TextView warnNumView;
	
	private boolean choose;
	
	private String defaultStr;
	private int chooseColor = 0;
	private int noChooseColor = 0;
//	private int textColor = 0;
	
	private int warnNum = -1;
	
	public MyChooseItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_choose_item_view, this);
		
		itemView = (TextView) findViewById(R.id.chooseTextView);
		warnNumView = (TextView) findViewById(R.id.warnNumView);
		warnView = (TextView) findViewById(R.id.chooseWarnView);
		TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.custom);
		chooseColor = type.getColor(R.styleable.custom_chooseColor, getResources().getColor(R.color.bg_header));
		noChooseColor = type.getColor(R.styleable.custom_noChooseColor, getResources().getColor(R.color.text_gray));
//		textColor = type.getColor(R.styleable.custom_textColor, getResources().getColor(R.color.dimGray));
		defaultStr = type.getString(R.styleable.custom_defaultValue);
		
		warnView.setBackgroundColor(chooseColor);
		if (defaultStr == null) {
			defaultStr = "";
		}
		choose = false;
		chooseView();
		setDefault();
	}
	
	public void setChoose(boolean choose) {
		this.choose = choose;
		chooseView();
	}
	
	public boolean getChoose() {
		return this.choose;
	}
	
	private void chooseView(){
		if (choose) {
			itemView.setTextColor(chooseColor);
			warnNumView.setTextColor(chooseColor);
			warnView.setVisibility(View.VISIBLE);
		} else {
			itemView.setTextColor(noChooseColor);
			warnNumView.setTextColor(noChooseColor);
			warnView.setVisibility(View.INVISIBLE);
		}
	}
	
	public void setText(String str) {
		this.defaultStr = str;
		setDefault();
	}
	
	public String getText() {
		return defaultStr;
	}
	
	public void setDefault() {
		itemView.setText(defaultStr);
	}
	
	public void setWarnNum(int num) {
		this.warnNum = num;
		warnNumView.setVisibility(View.VISIBLE);
		warnNumView.setText("(" + num + ")");
	}
	
	public void setWarnNum(Object num) {
		this.warnNum = ToolUtil.changeInteger(num);
		warnNumView.setVisibility(View.VISIBLE);
		warnNumView.setText("(" + num + ")");
	}
	
	public int getWarnNum() {
		return this.warnNum;
	}

}
