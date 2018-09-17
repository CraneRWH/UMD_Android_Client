package com.ymd.client.component.activity.order.detail;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.homePage.food.seller.fragment.ChooseDishesFragment;
import com.ymd.client.component.activity.order.pay.OrderPayActivity;
import com.ymd.client.component.adapter.order.OrderDetailBaofangAdapter;
import com.ymd.client.component.event.GoodsEvent;
import com.ymd.client.component.event.OrderEvent;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

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
    public OrderDetailFragment() {
        // Required empty public constructor
    }

    public static OrderDetailFragment newInstance(int type,OrderResultForm orderGoods) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("order", orderGoods);
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments()!=null) {
            orderDetail = (OrderResultForm) getArguments().getSerializable("order");
            fragmentType = getArguments().getInt("type");
            if (fragmentType == 0) {
                requestRoomList();
                eatLocationLt.setVisibility(View.VISIBLE);
            } else {
                eatLocationLt.setVisibility(View.GONE);
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
                eatNum ++;
                eatNumRefresh();
            }
        });
        subIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eatNum > 1) {
                    eatNum --;
                }
                eatNumRefresh();
            }
        });
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerView.show();
            }
        });

        initTimePicker();
        chooseRoomType();
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
        uGetTv.setText( ToolUtil.changeString(orderDetail.getuObtain()));
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
        roomsList = new Gson().fromJson(resultJson, new TypeToken<List<YmdMerchantRooms>>(){}.getType());
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
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_order_page_product, null);
            TextView nameView = v.findViewById(R.id.product_name_tv);
            TextView numView = v.findViewById(R.id.product_num_tv);
            nameView.setText(ToolUtil.changeString(item.getGoodsName()));
            numView.setText("x" + item.getGoodsNum());
            foodListLt.addView(v);
        }
    }

    private TimePickerView pickerView;//时间选择器
    /**
     * 获取时间
     * @param date 选择的时间
     * @return 截取的年月日
     */
    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {
        Calendar endDate = Calendar.getInstance();//结束时间
        Calendar startDate = Calendar.getInstance();//开始时间
        startDate.set(1900, 1, 1);

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(1990, 0, 1);//选中的时间
        pickerView = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                dateStr = getTime(date);
                dateTv.setText(dateStr);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();

        Dialog mDialog = pickerView.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
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
        params.put("eatTime", dateStr.replace(".","-") + " " +timeTv.getText() + ":00");
        params.put("room", roomType);
        if (roomType ==0 ) {
            params.put("roomId", chooseRoom.getRoomId());
        } else {
            params.put("roomId", -1);
        }
        switch (fragmentType) {
            case 0: params.put("himself", 1);
            break;
            case 1: params.put("himself", 0);
                break;
        }
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.ORDER_APPOINTMENT, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        result.optString("id");

                        OrderPayActivity.startAction(getActivity());
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
