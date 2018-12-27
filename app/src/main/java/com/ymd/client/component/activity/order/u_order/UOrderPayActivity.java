package com.ymd.client.component.activity.order.u_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.ymd.client.component.activity.mine.OpenMemberActivity;
import com.ymd.client.component.event.OrderListRefreshEvent;
import com.ymd.client.component.event.UEvent;
import com.ymd.client.component.widget.dialog.MyDialog;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.order.UOrderObject;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.AlertUtil;
import com.ymd.client.utils.FastDoubleClickUtil;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;
import com.ymd.client.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import chinapnr.android.paysdk.PayWayActivity;
import chinapnr.android.paysdk.util.PaysdkConstants;

/**
 * 优惠买单
 */
public class UOrderPayActivity extends BaseActivity {

    @BindView(R.id.order_all_money_tv)
    EditText orderAllMoneyTv;
    @BindView(R.id.person_type_iv)
    ImageView personTypeIv;
    @BindView(R.id.person_price_tv)
    TextView personPriceTv;
    @BindView(R.id.dis_money_tv)
    TextView disMoneyTv;
    @BindView(R.id.kt_member_btn)
    Button ktMemberBtn;
    @BindView(R.id.kt_member_llt)
    LinearLayout ktMemberLlt;
    @BindView(R.id.u_dis_money_tv)
    TextView uDisMoneyTv;
    @BindView(R.id.receive_u_num_tv)
    TextView receiveUNumTv;
    @BindView(R.id.pay_money_tv)
    TextView payMoneyTv;
    @BindView(R.id.pay_type_lt)
    LinearLayout payTypeLt;
    @BindView(R.id.order_money_tv2)
    TextView orderMoneyTv2;
    @BindView(R.id.pay_btn)
    LinearLayout payBtn;

    MerchantInfoEntity merchantInfo;
    int functionType;

    @BindView(R.id.sure_btn)
    ImageView sureBtn;

    private UOrderObject uOrderObject;

    private final static int KT_MEMBER_CODE = 1;



    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context, MerchantInfoEntity merchant, int functionType) {
        Intent intent = new Intent(context, UOrderPayActivity.class);
        intent.putExtra("merchant", merchant);
        intent.putExtra("functionType", functionType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uorder_pay);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setTitle("支付订单");
        setPayTypeData();

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FastDoubleClickUtil.isFastDoubleClick()) {
                    submitUOrder();
                }
            }
        });

        merchantInfo = (MerchantInfoEntity) getIntent().getExtras().getSerializable("merchant");
        functionType = getIntent().getExtras().getInt("functionType");

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FastDoubleClickUtil.isFastDoubleClick()) {
                    sendPay();
                }
            }
        });

        ktMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FastDoubleClickUtil.isFastDoubleClick())
                    startActivityForResult(new Intent(UOrderPayActivity.this, OpenMemberActivity.class), KT_MEMBER_CODE);
            }
        });

        resetMemberView();
    }

    private void resetMemberView() {

        if (LoginInfo.getInstance().getLoginInfo().getMembership() == 1) {
            ktMemberLlt.setVisibility(View.GONE);
            personTypeIv.setImageResource(R.mipmap.icon_hui);
            personPriceTv.setText("会员独享8.5折");
        } else {
            ktMemberLlt.setVisibility(View.VISIBLE);
            personTypeIv.setImageResource(R.mipmap.icon_pu_str);
            personPriceTv.setText("9折");
        }

    }

    List<Map<String, Object>> payTypeList = new ArrayList<>();

    private void setPayTypeData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "支付宝支付");
        map.put("icon", R.mipmap.icon_payoptions_zhi);
        map.put("isChoose", false);
        map.put("id", 12);
        payTypeList.add(map);

        map = new HashMap<>();
        map.put("name", "微信支付");
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
        for (int i = 0; i < payTypeList.size(); i++) {
            int position = i;
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_pay_type_item, payTypeLt, false);
            //通过View寻找ID实例化控件
            ImageView img = (ImageView) view.findViewById(R.id.pay_type_iv);
            //实例化TextView控件
            TextView tv = (TextView) view.findViewById(R.id.pay_name_tv);
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

    int payType = 0;

    private void chooseType(ImageView view, int position) {
        for (int i = 0; i < payTypeList.size(); i++) {
            Map<String, Object> item = payTypeList.get(i);
            if (i != position) {
                item.put("isChoose", false);
                ((ImageView) payTypeLt.getChildAt(i).findViewById(R.id.choose_iv)).setImageResource(R.mipmap.icon_payoptions_oval);
            }
        }
        if (position < 0) {
            return;
        }
        payType = position;
    }


    private void submitUOrder() {
        if (TextUtils.isEmpty(orderAllMoneyTv.getText())) {
            ToastUtil.ToastMessage(this, "请输入付款金额");
            return;
        }
        double allMoney = ToolUtil.changeDouble(orderAllMoneyTv.getText());
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        params.put("totalAmt", allMoney);
        params.put("uCurrency", "0");
        params.put("orderType", functionType == 0 ? 0 : 1);
        WebUtil.getInstance().requestPOST(this, URLConstant.CREATE_U_ORDER, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        //    toOrderDetail(result.optString("id"));

                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
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
                                ToastUtil.ToastMessage(UOrderPayActivity.this, "支付异常，请联系管理员");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        ToastUtil.ToastMessage(UOrderPayActivity.this, "支付异常，请联系管理员");
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
                                    AlertUtil.FailDialog(UOrderPayActivity.this, "订单支付失败");
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
            gotoAlipay(ToolUtil.changeString(uOrderObject.getId()), WebUtil.webUrl + "ymdOrder/pay");
        } else {
            gotoWechat(ToolUtil.changeString(uOrderObject.getId()), WebUtil.webUrl + "ymdOrder/pay");
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
        params.put("orderId", uOrderObject.getId());
        params.put("payType", payTypeStr);
        WebUtil.getInstance().requestPOST(this, URLConstant.ORDER_PAY_INFO, params,true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        //        resetOrderView(result.optString("ymdOrder"));
                        if (result.optInt("payStatus") == 2) {
                            UOrderPayResultActivity.startAction(UOrderPayActivity.this, uOrderObject);
                            EventBus.getDefault().post(new OrderListRefreshEvent(true));
                            EventBus.getDefault().post(new UEvent(true));
                            finish();
                        }
                        else
                            ToastUtil.ToastMessage(UOrderPayActivity.this, "支付未成功");
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KT_MEMBER_CODE) {
            resetMemberView();
        }
    }
}
