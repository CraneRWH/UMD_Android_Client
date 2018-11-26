package com.ymd.client.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ymd.client.R;
import com.ymd.client.model.constant.Constants;

/**
 * 微信付款的回调
 * @author 荣维鹤
 *
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "微信回调";

	private IWXAPI api;
	private static PayResultListener resultListener = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 这里的布局可以是个空的
		// setContentView(R.layout.activity_content);
		Log.d(TAG, "oncreate");
		api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID,true);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	public static void setResultListener(PayResultListener listener) {
		resultListener = listener;
	}

	// 在这个回调结果中将支付结果发送出去，然后结束掉自己
	@Override
	public void onResp(BaseResp resp) {
		if (resultListener != null) {
			resultListener.onResult(resp.errCode);
		}
		finish();
	}

	public interface PayResultListener {
		public void onResult(int resultCode);
	}

}