package com.ymd.client.component.activity.order.u_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.activity.order.pay.OrderPayActivity;
import com.ymd.client.utils.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠买单
 */
public class UOrderPayActivity extends AppCompatActivity {

    @BindView(R.id.order_all_money_tv)
    TextView orderAllMoneyTv;
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

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context, long id) {
        Intent intent = new Intent(context, UOrderPayActivity.class);
        intent.putExtra("orderId",id);
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
    }


}
