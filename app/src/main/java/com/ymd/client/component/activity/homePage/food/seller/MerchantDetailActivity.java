package com.ymd.client.component.activity.homePage.food.seller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ymd.client.R;
import com.ymd.client.component.activity.homePage.food.seller.fragment.ChooseDishesFragment;
import com.ymd.client.component.activity.homePage.food.seller.fragment.EvaluateSellerFragment;
import com.ymd.client.component.activity.homePage.food.seller.fragment.MerchantZiZhiFragment;
import com.ymd.client.component.activity.homePage.food.seller.fragment.SellerDetailFragment;
import com.ymd.client.component.activity.order.detail.OrderDetailActivity;
import com.ymd.client.component.adapter.TabFragmentAdapter;
import com.ymd.client.component.adapter.food.MerchantZiZhiAdapter;
import com.ymd.client.component.event.GoodsEvent;
import com.ymd.client.component.event.MessageEvent;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.YmdGoodsEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.AnimationUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商户详情
 */
public class MerchantDetailActivity extends TabBaseActivity {
    @BindView(R.id.icon_iv)
    ImageView iconIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.scoreBarView)
    RatingBar scoreBarView;
    @BindView(R.id.work_time_tv)
    TextView workTimeTv;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.phone_iv)
    ImageView phoneIv;
    @BindView(R.id.collection_iv)
    ImageView collectionIv;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    @BindView(R.id.warn_num_tv)
    TextView warnNumTv;
    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.product_money_tv)
    TextView productMoneyTv;
    @BindView(R.id.dis_tv)
    TextView disTv;
    @BindView(R.id.submit_btn)
    TextView submitBtn;
    @BindView(R.id.noShop)
    TextView noShop;
    @BindView(R.id.foot_view)
    LinearLayout footView;
    @BindView(R.id.shopCartMain)
    RelativeLayout shopCartMain;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private TabLayout slidingTabLayout;
    //fragment列表
    private List<Fragment> mFragments = new ArrayList<>();
    //tab名的列表
    private List<String> mTitles = new ArrayList<>();
    private ViewPager viewPager;
    private TabFragmentAdapter adapter;
    private TextView shopCartNum;
    private TextView totalPrice;
    private ViewGroup anim_mask_layout;//动画层

    MerchantInfoEntity merchantInfo;

    ShopCarPopupWindow shopCarPopupWindow;

    private List<YmdGoodsEntity> buyList = new ArrayList<>();

    private int functionType;   //美食相关类别的标记

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context, MerchantInfoEntity merchant, int functionType) {
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("merchant", merchant);
        intent.putExtra("functionType", functionType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        ButterKnife.bind(this);
        setCollsapsing();
        initView();

        requestMerchantInfo();
    }

    private void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        slidingTabLayout = (TabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        shopCartNum = (TextView) findViewById(R.id.product_money_tv);
        totalPrice = (TextView) findViewById(R.id.order_money_tv);

        merchantInfo = (MerchantInfoEntity) getIntent().getExtras().getSerializable("merchant");
        functionType = getIntent().getExtras().getInt("functionType");

        collectionIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCollection();
            }
        });

        shopCarPopupWindow = new ShopCarPopupWindow(this, new ShopCarPopupWindow.ResultListener() {
            @Override
            public void onResult(int position) {

            }
        });

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    if (shopCarPopupWindow.isShowing()) {
                    shopCarPopupWindow.dismiss();
                } else {
                    shopCarPopupWindow.showPopupWindow(shopCartMain);
                }*/
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyList.isEmpty()) {
                    ToastUtil.ToastMessage(MerchantDetailActivity.this,"请选择要购买的商品");
                } else {
                    submitOrder();
                }
            }
        });

        phoneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diallPhone(ToolUtil.changeString(merchantInfo.getMerTel()));
            }
        });

    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void diallPhone(String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.ToastMessage(this, "无商家电话");
        }
    }

    private void resetMerchantViewData() {
        //    Glide.with(this).load(merchantInfo.get)
        nameTv.setText(ToolUtil.changeString(merchantInfo.getName()));
        scoreBarView.setRating(ToolUtil.changeFloat(merchantInfo.getScore()));
        addressTv.setText(ToolUtil.changeString(merchantInfo.getAddress()));
        if (merchantInfo.getDiscount() != null) {
            disTv.setText("享受" + merchantInfo.getDiscount() + "折优惠");
            disTv.setVisibility(View.VISIBLE);
            noShop.setVisibility(View.GONE);
        } else {
            disTv.setVisibility(View.GONE);
            merchantInfo.setDiscount("10");
        }
    }

    private void setViewPager() {

        ChooseDishesFragment disheslFragment = ChooseDishesFragment.newInstance(merchantInfo);
        EvaluateSellerFragment evaluateSellerFragment = EvaluateSellerFragment.newInstance(merchantInfo);
    //    SellerDetailFragment detailFragment = SellerDetailFragment.newInstance(merchantInfo);
        MerchantZiZhiFragment detailFragment = MerchantZiZhiFragment.newInstance(merchantInfo);
        mFragments.add(disheslFragment);
        mFragments.add(evaluateSellerFragment);
        mFragments.add(detailFragment);

        mTitles.add("点餐");
     //   if (merchantInfo.getLatitude())
        mTitles.add("点评");
        mTitles.add("商家");

        adapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        shopCartMain.startAnimation(
                                AnimationUtil.createInAnimation(MerchantDetailActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                    case 1:
                        shopCartMain.startAnimation(
                                AnimationUtil.createOutAnimation(MerchantDetailActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                    case 2:
                        shopCartMain.startAnimation(
                                AnimationUtil.createOutAnimation(MerchantDetailActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void requestMerchantInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        WebUtil.getInstance().requestPOST(this, URLConstant.MERCHANT_DETAIL_INFO, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        merchantInfo = new Gson().fromJson(result.optString("merchant"), MerchantInfoEntity.class);
                        resetMerchantViewData();
                        setViewPager();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    /**
     * 添加收藏
     */
    private void addCollection() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        params.put("consumerId", LoginInfo.getInstance().getLoginInfo().getId());
        WebUtil.getInstance().requestPOST(this, URLConstant.MERCHANT_COLLECTION_ADD, params,true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        ToastUtil.ToastMessage(getApplicationContext(), "收藏成功");
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        ToastUtil.ToastMessage(getApplicationContext(), "收藏成功");
                    }
                });
    }

    /**
     * 取消收藏
     */
    private void delCollection() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        params.put("consumerId", LoginInfo.getInstance().getLoginInfo().getId());
        WebUtil.getInstance().requestPOST(this, URLConstant.MERCHANT_COLLECTION_DEL, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        ToastUtil.ToastMessage(getApplicationContext(), "收藏成功");
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        ToastUtil.ToastMessage(getApplicationContext(), "收藏成功");
                    }
                });
    }

    private void setCollsapsing() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
    /*    collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.bg_header));
        collapsingToolbarLayout.setContentScrim(getResources().getDrawable(R.mipmap.adver_icon_1));*/
    }


    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event != null) {
            if (event.num > 0) {
                shopCartNum.setText(String.valueOf(event.num));
                shopCartNum.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.GONE);
            } else {
                shopCartNum.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                noShop.setVisibility(View.VISIBLE);
            }
            totalPrice.setText("¥" + String.valueOf(event.price));

            Log.v("MerchantDetailActivity", "添加的数量：" + event.goods.size());
        }

    }


    /**
     * 设置动画（点击添加商品）
     *
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        shopCartNum.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化动画图层
     *
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加到动画图层
     *
     * @param parent
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(YmdGoodsEntity goodsEntity) {
        shopCarPopupWindow.addGood(goodsEntity);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsEvent goodsEntity) {
        try {
            double allMoney = 0;
            double disAllMoney = 0;
            buyList.clear();
            int count = 0;
            for (YmdGoodsEntity item : goodsEntity.getGoods()) {
                allMoney = ToolUtil.changeDouble(item.getPrice()) * item.getBuyCount() + allMoney;
                count = count + item.getBuyCount();
                disAllMoney = ((ToolUtil.changeDouble(item.getPrice()) * ToolUtil.changeDouble(merchantInfo.getDiscount())) / 10) * item.getBuyCount() + disAllMoney;
            }
            goodsEntity.setAllMoney(allMoney);
            goodsEntity.setDisAllMoney(disAllMoney);
            productMoneyTv.setText(ToolUtil.double2Point(allMoney) + "元");
            orderMoneyTv.setText("¥" + ToolUtil.double2Point(disAllMoney));
            if (goodsEntity.getGoods() == null || goodsEntity.getGoods().isEmpty()) {
                noShop.setVisibility(View.GONE);
                warnNumTv.setVisibility(View.GONE);
                warnNumTv.setText("0");
            } else {
                warnNumTv.setText(ToolUtil.changeString(count));
                warnNumTv.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.VISIBLE);
                buyList.addAll(goodsEntity.getGoods());
                mainGoods = goodsEntity;
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    private GoodsEvent mainGoods;

    private void submitOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        params.put("payAmt", ToolUtil.changeString(mainGoods.getDisAllMoney()));
        params.put("totalAmt", ToolUtil.changeString(mainGoods.getAllMoney()));
        params.put("uCurrency", "0");
        List<Map<String,Object>> list = new ArrayList<>();
        for (YmdGoodsEntity item : buyList) {
            Map<String,Object> map = new HashMap<>();
            map.put("goodsId", item.getId());
            map.put("goodsNum", item.getBuyCount());
            map.put("goodsType","0");
            list.add(map);
        }
        params.put("goodslist", list);
        WebUtil.getInstance().requestPOST(this, URLConstant.CREATE_ORDER, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        toOrderDetail(result.optString("id"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void toOrderDetail(String orderCode) {
        OrderDetailActivity.startAction(this, ToolUtil.changeLong(orderCode), functionType);
    }
}
