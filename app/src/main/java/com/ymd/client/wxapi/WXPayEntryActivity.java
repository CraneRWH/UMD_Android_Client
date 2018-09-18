package com.ymd.client.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{


	private IWXAPI api;
	private Button pay_finsh;
	private TextView wechatDes;
	private TestHandle myHandle = new TestHandle();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_results);
		api = WXAPIFactory.createWXAPI(this, "wx2a5538052969956e");//appid需换成商户自己开放平台appid
		api.handleIntent(getIntent(), this);
		wechatDes = (TextView)findViewById(R.id.wechatDes) ;
		pay_finsh = (Button)findViewById(R.id.pay_finsh);
		pay_finsh.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				WXPayEntryActivity.this.finish();
			}
		});

	}

	private class TestHandle extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) // 支付成功
			{
				//即使支付成功应该也已后台的交易查询结果为主
				wechatDes.setText("微信付款成功!");
				Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
			}
			else
			{

				wechatDes.setText("微信付款失败!");
				Toast.makeText(WXPayEntryActivity.this, "支付失败" , Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	public void onReq(BaseReq req)
	{
	}

	@Override
	public void onResp(BaseResp resp)
	{
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX)
		{
			// resp.errCode == -1 原因：支付错误,可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
			// resp.errCode == -2 原因 用户取消,无需处理。发生场景：用户不支付了，点击取消，返回APP
			//resp.errCode == 0 支付成功
			myHandle.sendEmptyMessageDelayed(resp.errCode,2000);

		}
	}
}