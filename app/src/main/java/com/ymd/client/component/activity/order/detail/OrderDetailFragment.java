package com.ymd.client.component.activity.order.detail;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.order.pay.OrderPayActivity;
import com.ymd.client.component.adapter.order.OrderDetailBaofangAdapter;
import com.ymd.client.component.event.OrderEvent;
import com.ymd.client.component.widget.datepicker.CustomDatePicker;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.bean.order.YmdMerchantRooms;
import com.ymd.client.model.bean.order.YmdOrderGoods;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者:rongweihe
 * 日期:2018/8/25
 * 描述:    “订单详情界面”
 * 修改历史:
 */
public class OrderDetailFragment extends Fragment {


    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.dating_tv)
    TextView datingTv;
    @BindView(R.id.baofang_tv)
    TextView baofangTv;
    @BindView(R.id.baofang_rv)
    RecyclerView baofangRv;
    @BindView(R.id.eat_location_lt)
    LinearLayout eatLocationLt;
    @BindView(R.id.sub_iv)
    ImageView subIv;
    @BindView(R.id.eat_person_num_tv)
    TextView eatPersonNumTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.eat_person_num_rt)
    RelativeLayout eatPersonNumRt;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.food_list_lt)
    LinearLayout foodListLt;
    @BindView(R.id.more_pay_type_lt)
    LinearLayout morePayTypeLt;
    @BindView(R.id.order_price_tv)
    TextView orderPriceTv;
    @BindView(R.id.dis_price_tv)
    TextView disPriceTv;
    @BindView(R.id.u_dis_price_tv)
    TextView uDisPriceTv;
    @BindView(R.id.u_get_tv)
    TextView uGetTv;
    Unbinder unbinder;

    OrderResultForm orderDetail;
    int fragmentType;
    @BindView(R.id.remark_et)
    EditText remarkEt;

    private int functionType;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    public static OrderDetailFragment newInstance(int type, int functionType,OrderResultForm orderGoods) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("order", orderGoods);
        args.putInt("type", type);
        args.putInt("functionType", functionType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            orderDetail = (OrderResultForm) getArguments().getSerializable("order");
            fragmentType = getArguments().getInt("type");
            functionType = getArguments().getInt("functionType");
            if (functionType == 1) {
                if (fragmentType == 1) {
                    requestRoomList();
                    eatLocationLt.setVisibility(View.VISIBLE);
                } else {
                    eatLocationLt.setVisibility(View.GONE);
                }
            }
            resetGoodList();
            resetOrderView();
        }
        initView(view);
        return view;
    }

    private int roomType = 1;
    YmdMerchantRooms chooseRoom = null;
    int eatNum = 1;
    String dateStr = "";
    String timeStr = "";

    private void initView(View view) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        baofangRv.setLayoutManager(layoutManager);

        baofangTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomType = 0;

                chooseRoomType();
            }
        });
        datingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomType = 1;
                chooseRoomType();
            }
        });
        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eatNum++;
                eatNumRefresh();
            }
        });
        subIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eatNum > 1) {
                    eatNum--;
                }
                eatNumRefresh();
            }
        });
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker.show(dateStr + " " + timeTv.getText().toString());
            }
        });

        if (functionType == 1) {
            initDatePicker();
            chooseRoomType();
        }
    }

    @SuppressLint("ResourceAsColor")
    private void chooseRoomType() {
        if (roomType == 0) {
            baofangTv.setBackgroundResource(R.mipmap.baofang_green_icon);
            baofangTv.setTextColor(Color.WHITE);
            datingTv.setBackgroundResource(R.mipmap.baofang_white_little_icon);
            datingTv.setTextColor(R.color.text_gray_dark);
            baofangRv.setVisibility(View.VISIBLE);
        } else {
            baofangTv.setBackgroundResource(R.mipmap.baofang_white_little_icon);
            baofangTv.setTextColor(R.color.text_gray_dark);
            datingTv.setBackgroundResource(R.mipmap.baofang_green_icon);
            datingTv.setTextColor(R.color.white);
            baofangRv.setVisibility(View.GONE);
        }
    }

    private void resetOrderView() {
        orderPriceTv.setText(ToolUtil.changeString(orderDetail.getTotalAmt()) + "元");
        disPriceTv.setText("-" + ToolUtil.changeString(orderDetail.getDiscountAmt()) + "元");
        uGetTv.setText(ToolUtil.changeString(orderDetail.getuObtain()));
        uDisPriceTv.setText("-" + ToolUtil.changeString(orderDetail.getuCurrency()));
    }

    private void eatNumRefresh() {
        eatPersonNumTv.setText(ToolUtil.changeString(eatNum));
    }

    private void requestRoomList() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", orderDetail.getmId());
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.MERCHANT_ROOM_LIST, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        resetRoomListData(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    List<YmdMerchantRooms> roomsList;

    private void resetRoomListData(String resultJson) {
        roomsList = new Gson().fromJson(resultJson, new TypeToken<List<YmdMerchantRooms>>() {
        }.getType());
        OrderDetailBaofangAdapter adapter = new OrderDetailBaofangAdapter(roomsList, getContext());
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                chooseRoom = (YmdMerchantRooms) data;
            }
        });
        baofangRv.setAdapter(adapter);
    }

    private void resetGoodList() {
        shopNameTv.setText(orderDetail.getmName());
        foodListLt.removeAllViews();
        for (YmdOrderGoods item : orderDetail.getYmdOrderGoodsList()) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_order_food, null);
            ImageView iconView = v.findViewById(R.id.food_icon_iv);
            TextView nameView = v.findViewById(R.id.food_name_tv);
            TextView numView = v.findViewById(R.id.food_num_tv);
            TextView priceView = v.findViewById(R.id.food_price_tv);
            priceView.setText("¥" + item.getGoodsAmt());
            nameView.setText(ToolUtil.changeString(item.getGoodsName()));
            numView.setText("x" + item.getGoodsNum());
            if (item.getGoodsIcon() != null) {
                Glide.with(getActivity()).load(item.getGoodsIcon()).into(iconView);
            }
            foodListLt.addView(v);
        }
    }


    private CustomDatePicker customDatePicker;

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        dateStr = now.split(" ")[0];
        dateTv.setText(now.split(" ")[0]);
        timeTv.setText(now.split(" ")[1]);
        timeStr = now.split(" ")[1];
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 90);
        Date endDate = cal.getTime();
        customDatePicker = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                dateStr = time.split(" ")[0];
                dateTv.setText(dateStr);
                timeStr = time.split(" ")[1];
                timeTv.setText(timeStr);
            }
        }, now, sdf.format(endDate)); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void submitOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", orderDetail.getId());
        params.put("eatNumber", ToolUtil.changeString(eatNum));
        params.put("eatTime", dateStr + " " + timeStr + ":00");
        params.put("room", roomType);
        if (roomType == 0) {
            params.put("roomId", chooseRoom.getRoomId());
        } else {
            params.put("roomId", 0);
        }
        switch (fragmentType) {
            case 0:
                params.put("himself", 1);
                break;
            case 1:
                params.put("himself", 0);
                break;
        }
        params.put("remark", ToolUtil.changeString(remarkEt.getText()));
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.ORDER_APPOINTMENT, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        result.optString("id");

                        OrderPayActivity.startAction(getActivity(), orderDetail.getId());
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent event) {
        if (event.getType() == fragmentType) {
            submitOrder();
        }
    }

}
