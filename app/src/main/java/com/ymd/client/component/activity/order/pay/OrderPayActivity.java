package com.ymd.client.component.activity.order.pay;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.dialog.MyDialog;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.AlertUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.ArrayList;
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
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.order_code_tv)
    TextView orderCodeTv;
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
    @BindView(R.id.order_money_tv2)
    TextView orderMoneyTv2;
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
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
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
        shopNameTv.setText(orderDetail.getmName());
        orderMoneyTv1.setText(ToolUtil.changeString(orderDetail.getPayAmt()));
        orderMoneyTv2.setText(ToolUtil.changeString(orderDetail.getPayAmt()));
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
                ((ImageView)payTypeLt.getChildAt(i).findViewById(R.id.choose_iv)).setImageResource(R.mipmap.icon_payoptions_oval);
            }
        }
        if (position < 0) {
            return;
        }
        payType = position;
        if (ToolUtil.changeBoolean(payTypeList.get(position).get("isChoose"))) {
            view.setImageResource(R.mipmap.icon_payoptions_complete);
            chooseIv1.setImageResource(R.mipmap.icon_payoptions_oval);
            chooseIv2.setImageResource(R.mipmap.icon_payoptions_oval);
        } else {
            view.setImageResource(R.mipmap.icon_payoptions_oval);
            chooseIv1.setImageResource(R.mipmap.icon_payoptions_oval);
            chooseIv2.setImageResource(R.mipmap.icon_payoptions_oval);
        }
    }


    private static final int REQ_CODE = 0x123; //调用sdk返回结果的请求码
    /**
     * 调用支付宝支付
     */
    private void gotoAlipay(String alipayMoneyView,String alipayUrlView){
        Intent mIntent = new Intent(this, PayWayActivity.class);
        mIntent.putExtra(PaysdkConstants.CHINAPNR_PAY_WAY_KEY,PaysdkConstants.ALIPAY_WAY);//选择支付宝支付
        String tradeMoney = "{\"tradeMoney\": \"%s\" } ";
        mIntent.putExtra(PaysdkConstants.PAY_PARAM_INFO_KEY, TextUtils.isEmpty(alipayMoneyView) ? "" : String.format(tradeMoney, alipayMoneyView));
        mIntent.putExtra(PaysdkConstants.APP_PAY_URL_KEY,TextUtils.isEmpty(alipayUrlView) ? "http://mertest.chinapnr.com/service-demo/appPay/pay" : alipayUrlView);

        showPayResultDialog();
        startActivityForResult(mIntent,REQ_CODE);
    }

    /**
     * 调用微信支付
     */
    private void gotoWechat(String wechatMoneyView,String wechatUrlView){

        Intent mIntent = new Intent(this, PayWayActivity.class);
        mIntent.putExtra(PaysdkConstants.CHINAPNR_PAY_WAY_KEY,PaysdkConstants.WECHAT_WAY);//选择微信支付
        String tradeMoney = "{\"tradeMoney\": \"%s\" } ";
        mIntent.putExtra(PaysdkConstants.PAY_PARAM_INFO_KEY,TextUtils.isEmpty(wechatMoneyView) ? "" : String.format(tradeMoney, wechatMoneyView));
        mIntent.putExtra(PaysdkConstants.APP_PAY_URL_KEY,TextUtils.isEmpty(wechatUrlView) ? "weixin://wxpay/bizpayurl?pr=J15udq4" : wechatUrlView);

        showPayResultDialog();
        startActivityForResult(mIntent,REQ_CODE);
    }

    private void showPayResultDialog() {
        AlertUtil.AskDialog(this, "是否支付完成", new MyDialog.SureListener() {
            @Override
            public void onSureListener() {

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
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("payType", payTypeStr);
        WebUtil.getInstance().requestPOST(this, URLConstant.ORDER_PAY_INFO, params,true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                //        resetOrderView(result.optString("ymdOrder"));
                  /*      if (payType == 0) {
                            gotoAlipay(result.optString("money"), result.optString("url"));
                        } else {
                            gotoWechat(result.optString("money"), result.optString("url"));
                        }*/
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

}
