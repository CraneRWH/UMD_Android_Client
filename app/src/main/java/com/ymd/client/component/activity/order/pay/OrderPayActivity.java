package com.ymd.client.component.activity.order.pay;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.bean.PayInfo;
import com.ymd.client.common.base.bean.PayOrderResult;
import com.ymd.client.component.activity.login.LoginActivity;
import com.ymd.client.component.event.OrderListRefreshEvent;
import com.ymd.client.component.event.UEvent;
import com.ymd.client.component.widget.dialog.MyDialog;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.AlertUtil;
import com.ymd.client.utils.DataUtils;
import com.ymd.client.utils.DateUtil;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;
import com.ymd.client.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import chinapnr.android.paysdk.PayWayActivity;
import chinapnr.android.paysdk.SdkInitFailedException;
import chinapnr.android.paysdk.SdkManager;
import chinapnr.android.paysdk.util.PaysdkConstants;

/**
 * 作者:rongweihe
 * 日期:2018/8/29
 * 描述:    “订单支付界面”
 * 修改历史:
 */
public class OrderPayActivity extends BaseActivity {

    @BindView(R.id.remnant_time_tv)
    TextView remnantTimeTv;
    @BindView(R.id.order_money_tv1)
    TextView orderMoneyTv1;
   /* @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.order_code_tv)
    TextView orderCodeTv;*/
    @BindView(R.id.pay_type_iv1)
    ImageView payTypeIv1;
    @BindView(R.id.pay_name_tv1)
    TextView payNameTv1;
    @BindView(R.id.pay_type_tv1)
    TextView payTypeTv1;
    @BindView(R.id.choose_iv1)
    ImageView chooseIv1;
    @BindView(R.id.pay_type_iv2)
    ImageView payTypeIv2;
    @BindView(R.id.choose_iv2)
    ImageView chooseIv2;
    @BindView(R.id.pay_type_lt)
    LinearLayout payTypeLt;
 /*   @BindView(R.id.order_money_tv2)
    TextView orderMoneyTv2;*/
    @BindView(R.id.pay_btn)
    LinearLayout payBtn;

    private long orderId;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context, long id) {
        Intent intent = new Intent(context, OrderPayActivity.class);
        intent.putExtra("orderId",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        setStatusBar(R.color.white);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setTitle("支付订单");
        setPayTypeData();

        chooseIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseIv1.setImageResource(R.mipmap.icon_payoptions_complete);
                chooseIv2.setImageResource(R.mipmap.icon_payoptions_oval);
                chooseType(null, -1);
            }
        });
        chooseIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseIv1.setImageResource(R.mipmap.icon_payoptions_oval);
                chooseIv2.setImageResource(R.mipmap.icon_payoptions_complete);
                chooseType(null, -1);
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPay();
            }
        });

        if (getIntent().getExtras() != null) {
            orderId = getIntent().getExtras().getLong("orderId");
            requestOrderDetail();
        } else {
            finish();
        }

        // 完成 SDK 初始化，生序生命周期内执行一次即可
        // 注：如需使用后续的交易信息指纹功能，必须先完成 SDK 的初始化
        try {
            SdkManager.initSdk(OrderPayActivity.this.getApplication(),
                    "",     // 线下提供的商户客户号
                    "");    // 线下提供的初始化链接

        } catch (SdkInitFailedException e) {
            // 初始化失败，必须填写正确的商户客户号、初始化链接
            // 并正确传入 Application 实例对象
        }
    }

    private void requestOrderDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        WebUtil.getInstance().requestPOST(this, URLConstant.ORDER_DETAIL, params,true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        resetOrderView(result.optString("ymdOrder"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }
    OrderResultForm orderDetail;
    private void resetOrderView(String resultJson) {
        orderDetail = new Gson().fromJson(resultJson, OrderResultForm.class);
    //    shopNameTv.setText(orderDetail.getmName());
        orderMoneyTv1.setText(ToolUtil.changeString(orderDetail.getPayAmt()));
        Long time = new Date().getTime() + 1700000;
        TimeTask timeTask = new TimeTask(new Date(time));
        timeTask.execute();

    //    orderMoneyTv2.setText(ToolUtil.changeString(orderDetail.getPayAmt()));
    }

    List<Map<String ,Object>> payTypeList = new ArrayList<>();
    private void setPayTypeData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","支付宝支付");
        map.put("icon", R.mipmap.icon_payoptions_zhi);
        map.put("isChoose", false);
        map.put("id", 12);
        payTypeList.add(map);

        map = new HashMap<>();
        map.put("name","微信支付");
        map.put("icon", R.mipmap.icon_payoptions_wei);
        map.put("isChoose", false);
        map.put("id", 10);
        payTypeList.add(map);

   /*     map = new HashMap<>();
        map.put("name","其他支付");
        map.put("icon", R.mipmap.icon_merchant_star_image_comment);
        map.put("isChoose", false);
        payTypeList.add(map);*/

        //开始添加数据
        for(int i=0; i<payTypeList.size(); i++){
            int position = i;
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view= LayoutInflater.from(this).inflate(R.layout.item_order_pay_type_item , payTypeLt,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.pay_type_iv);
            //实例化TextView控件
            TextView tv= (TextView) view.findViewById(R.id.pay_name_tv);
            //将int数组中的数据放到ImageView中
            img.setImageResource(ToolUtil.changeInteger(payTypeList.get(i).get("icon")));
            //给TextView添加文字
            tv.setText(ToolUtil.changeString(payTypeList.get(i).get("name")));
            ImageView chooseView = (ImageView) view.findViewById(R.id.choose_iv);
            chooseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payTypeList.get(position).put("isChoose", !ToolUtil.changeBoolean(payTypeList.get(position).get("isChoose")));
                    chooseType(chooseView, position);
                }
            });
        //    chooseType(chooseView, i);
            //把行布局放到linear里
            payTypeLt.addView(view);
        }
    }

    int payType =0;
    private void chooseType(ImageView view, int position) {
        for (int i = 0 ; i < payTypeList.size(); i ++ ) {
            Map<String,Object> item = payTypeList.get(i);
            if (i != position) {
                item.put("isChoose", false);
                ((ImageView)payTypeLt.getChildAt(i).findViewById(R.id.choose_iv)).setImageResource(R.mipmap.icon_payoptions_oval2);
            } else {
                ((ImageView)payTypeLt.getChildAt(i).findViewById(R.id.choose_iv)).setImageResource(R.mipmap.icon_payoptions_complete2);
            }
        }
        if (position < 0) {
            return;
        }
        payType = position;
        if (ToolUtil.changeBoolean(payTypeList.get(position).get("isChoose"))) {
            view.setImageResource(R.mipmap.icon_payoptions_complete2);
            chooseIv1.setImageResource(R.mipmap.icon_payoptions_oval2);
            chooseIv2.setImageResource(R.mipmap.icon_payoptions_oval2);
        } else {
            view.setImageResource(R.mipmap.icon_payoptions_oval2);
            chooseIv1.setImageResource(R.mipmap.icon_payoptions_oval2);
            chooseIv2.setImageResource(R.mipmap.icon_payoptions_oval2);
        }
    }


    private static final int REQ_CODE = 0x123; //调用sdk返回结果的请求码
    /**
     * 调用支付宝支付
     */
    private void gotoAlipay(String alipayMoneyView,String alipayUrlView){
        Intent mIntent = new Intent(this, PayWayActivity.class);
        mIntent.putExtra(PaysdkConstants.CHINAPNR_PAY_WAY_KEY,PaysdkConstants.ALIPAY_WAY);//选择支付宝支付
        String tradeMoney = "{\"orderId\": \"%s\" } ";
        mIntent.putExtra(PaysdkConstants.PAY_PARAM_INFO_KEY, String.format(tradeMoney, alipayMoneyView));
        mIntent.putExtra(PaysdkConstants.APP_PAY_URL_KEY,alipayUrlView);

        showPayResultDialog();
        startActivityForResult(mIntent,REQ_CODE);
    }

    /**
     * 调用微信支付
     */
    private void gotoWechat(String wechatMoneyView,String wechatUrlView){
        Map<String,String> dataMap = new HashMap();
        dataMap.put("orderId", wechatMoneyView);
        Map<String, Object> params = new HashMap<>();
        params.put("self_param_info", new Gson().toJson(dataMap));
        params.put("pay_type", "10");
        WebUtil.getInstance().requestPOSTS(this, URLConstant.ORDER_PAY, wechatMoneyView,true,
                new WebUtil.WebCallBacks<String>() {
                    @Override
                    public void onWebSuccess(String result) {
                        try {
                            if (result != null && result.length() > 0) {
                                wechatPay(result);
                            } else {
                                ToastUtil.ToastMessage(OrderPayActivity.this, "支付异常，请联系管理员");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        ToastUtil.ToastMessage(OrderPayActivity.this, "支付异常，请联系管理员");
                    }
                });
        /*Intent mIntent = new Intent(this, PayWayActivity.class);
        mIntent.putExtra(PaysdkConstants.CHINAPNR_PAY_WAY_KEY,PaysdkConstants.WECHAT_WAY);//选择微信支付

        mIntent.putExtra(PaysdkConstants.PAY_PARAM_INFO_KEY,String.format(tradeMoney, wechatMoneyView));
        mIntent.putExtra(PaysdkConstants.APP_PAY_URL_KEY,wechatUrlView);
*/

 //       startActivityForResult(mIntent,REQ_CODE);
    }
    private IWXAPI api;
    private void wechatPay(String data) throws Exception  {
        String result = ToolUtil.UrlCode2String(data);
        LogUtil.showW("★★ "  + " ★ " + result);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            if (jsonObject == null) {
                ToastUtil.ToastMessage(this, "支付异常，请联系管理员");
                return;
            } else {
                String pis = jsonObject.optString("pay_info");
                if ( pis == null && pis.length() == 0) {
                    ToastUtil.ToastMessage(this, "支付异常，请联系管理员");
                    return;
                } else {
                    if (jsonObject.optString("bg_bank_code").equals("SUCCESS")) {
                        PayInfo payInfo = new Gson().fromJson(pis, PayInfo.class);
                        api = WXAPIFactory.createWXAPI(this, payInfo.getAppId());
                        PayReq req = new PayReq();
                        req.appId = payInfo.getAppId();
                        req.partnerId = payInfo.getPartnerId();
                        req.prepayId = payInfo.getPrepayId();
                        req.nonceStr = payInfo.getNonceStr();
                        req.timeStamp = payInfo.getTimeStamp();
                        req.packageValue = payInfo.getPackAge();
                        req.sign = payInfo.getPaySign();
                        //	req.extData			= "app data"; // optional
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
                        WXPayEntryActivity.setResultListener(new WXPayEntryActivity.PayResultListener() {

                            @Override
                            public void onResult(int resultCode) {
                                if (resultCode == 0) {
                                    getPayResult();
                                } else {
                                    AlertUtil.FailDialog(OrderPayActivity.this, "订单支付失败");
                                }
                            }
                        });
                    } else {
                        ToastUtil.ToastMessage(this, jsonObject.optString("resp_desc"));
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.ToastMessage(this, "支付异常，请联系管理员");
        } finally {
    //        showPayResultDialog();
        }
    }

    private void showPayResultDialog() {
        AlertUtil.AskDialog(this, "是否支付完成", new MyDialog.SureListener() {
            @Override
            public void onSureListener() {
                getPayResult();
            }
        });
    }

    private void sendPay() {
        String payTypeStr = "";
        for (Map<String,Object> item : payTypeList) {
            if (ToolUtil.changeBoolean(item.get("isChoose"))) {
                payTypeStr = ToolUtil.changeString(item.get("id"));
            }
        }
        if (payTypeStr.length() == 0) {
            ToastUtil.ToastMessage(this, "请选择付款方式");
            return;
        }
     /*  */
        if (payType == 0) {
            gotoAlipay(ToolUtil.changeString(orderDetail.getId()), WebUtil.webUrl + "ymdOrder/pay");
        } else {
            gotoWechat(ToolUtil.changeString(orderDetail.getId()), WebUtil.webUrl + "ymdOrder/pay");
        }
    }

    private void getPayResult() {
        String payTypeStr = "";
        for (Map<String,Object> item : payTypeList) {
            if (ToolUtil.changeBoolean(item.get("isChoose"))) {
                payTypeStr = ToolUtil.changeString(item.get("id"));
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("payType", payTypeStr);
        WebUtil.getInstance().requestPOST(this, URLConstant.ORDER_PAY_INFO, params,true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        //        resetOrderView(result.optString("ymdOrder"));
                        if (result.optInt("payStatus") == 2) {
                            OrderPayResultActivity.startAction(OrderPayActivity.this, orderDetail);
                            EventBus.getDefault().post(new OrderListRefreshEvent(true));
                            EventBus.getDefault().post(new UEvent(true));
                            finish();
                        }
                        else
                            ToastUtil.ToastMessage(OrderPayActivity.this, "支付未成功");
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    class TimeTask extends AsyncTask<Void, Integer, Boolean> {
        Date endTime;
        long time = 1800000;
        public TimeTask(Date time) {
            this.endTime = time;
        }

        @Override
        protected void onPreExecute() {
            remnantTimeTv.setText(DateUtil.getSpacingTime(endTime));
            //    remnantTimeTv.setBackgroundResource(R.color.silver);
            remnantTimeTv.setClickable(false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            remnantTimeTv.setClickable(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            remnantTimeTv.setText(DateUtil.getSpacingTime(endTime));
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            while (true) {
                if (time >= 0) {
                    try {
                        Thread.sleep(1000);
                        publishProgress();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    return true;
                }
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
