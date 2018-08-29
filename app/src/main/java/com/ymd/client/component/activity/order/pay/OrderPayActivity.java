package com.ymd.client.component.activity.order.pay;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, OrderPayActivity.class);
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
                OrderPayResultActivity.startAction(OrderPayActivity.this);
            }
        });
    }

    List<Map<String ,Object>> payTypeList = new ArrayList<>();
    private void setPayTypeData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","支付宝支付");
        map.put("icon", R.mipmap.icon_merchant_image_order_1);
        map.put("isChoose", false);
        payTypeList.add(map);

        map = new HashMap<>();
        map.put("name","微信支付");
        map.put("icon", R.mipmap.icon_merchant_image_shang);
        map.put("isChoose", false);
        payTypeList.add(map);

        map = new HashMap<>();
        map.put("name","其他支付");
        map.put("icon", R.mipmap.icon_merchant_star_image_comment);
        map.put("isChoose", false);
        payTypeList.add(map);

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

}
