package com.ymd.client.component.activity.order.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.order.detail.OrderDetailActivity;
import com.ymd.client.component.widget.other.MyChooseItemView;
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
 * 描述:    “订单支付结果界面”
 * 修改历史:
 */
public class OrderPayResultActivity extends BaseActivity {

    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.pay_result_tv)
    TextView payResultTv;
    @BindView(R.id.qr_code_iv)
    ImageView qrCodeIv;
    @BindView(R.id.food_code_mcv)
    MyChooseItemView foodCodeMcv;
    @BindView(R.id.phone_iv)
    ImageView phoneIv;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.food_list_lt)
    LinearLayout foodListLt;
    @BindView(R.id.more_pay_type_lt)
    LinearLayout morePayTypeLt;
    @BindView(R.id.remark_tv)
    TextView remarkTv;
    @BindView(R.id.order_code_tv)
    TextView orderCodeTv;
    @BindView(R.id.order_time_tv)
    TextView orderTimeTv;
    @BindView(R.id.order_date_tv)
    TextView orderDateTv;
    @BindView(R.id.order_u_tv)
    TextView orderUTv;
    @BindView(R.id.u_dis_tv)
    TextView uDisTv;
    @BindView(R.id.order_all_money_tv)
    TextView orderAllMoneyTv;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, OrderPayResultActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_result);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setShopData();
    }

    private void setShopData() {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","麻辣烫");
        map.put("icon", R.mipmap.icon_merchant_image_order_1);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);

        map = new HashMap<>();
        map.put("name","蒸饺");
        map.put("icon", R.mipmap.icon_merchant_image_shang);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);

        map = new HashMap<>();
        map.put("name","面条");
        map.put("icon", R.mipmap.icon_merchant_star_image_comment);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);

        map = new HashMap<>();
        map.put("name","炒饼");
        map.put("icon", R.mipmap.icon_merchant_mage);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);

        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view= LayoutInflater.from(this).inflate(R.layout.item_fragment_order_food , foodListLt,false);
            TextView nameTv = (TextView) view.findViewById(R.id.food_name_tv);
            TextView numTv = (TextView) view.findViewById(R.id.food_num_tv);
            TextView priceTv = (TextView) view.findViewById(R.id.food_price_tv);
            ImageView iconIv = (ImageView) view.findViewById(R.id.food_icon_iv);
            iconIv.setImageResource(ToolUtil.changeInteger(list.get(i).get("icon")));
            nameTv.setText(ToolUtil.changeString(list.get(i).get("name")));
            numTv.setText("x"+ToolUtil.changeString(list.get(i).get("num")));
            priceTv.setText("¥"+ToolUtil.changeString(list.get(i).get("price")));
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            foodListLt.addView(view);
        }
    }
}
