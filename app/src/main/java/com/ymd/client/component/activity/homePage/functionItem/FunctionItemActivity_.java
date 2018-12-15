package com.ymd.client.component.activity.homePage.functionItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.homePage.merchant.MerchantDetailActivity;
import com.ymd.client.component.activity.homePage.search.SearchActivity;
import com.ymd.client.component.adapter.TabFragmentAdapter;
import com.ymd.client.component.event.RefreshEndEvent;
import com.ymd.client.component.event.RefreshMerchantListEvent;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.component.widget.viewPager.CustomViewPager;
import com.ymd.client.model.bean.homePage.YmdIndustryEntity;
import com.ymd.client.model.bean.homePage.YmdRecommendEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.FastDoubleClickUtil;
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
 * 作者:rongweihe
 * 日期:2018/8/25
 * 描述:    酒店、爱车、美容美发、电影、生鲜、金融、洗浴/KTV、优币专区、其他分类等
 * 修改历史:
 */
public class FunctionItemActivity_ extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    @BindView(R.id.more_tv)
    TextView moreTv;
    @BindView(R.id.recommendLayout)
    LinearLayout recommendLayout;
    @BindView(R.id.chooseItem0)
    MyChooseItemView chooseItem0;
    @BindView(R.id.chooseItem1)
    MyChooseItemView chooseItem1;
    @BindView(R.id.chooseItem2)
    MyChooseItemView chooseItem2;
    @BindView(R.id.chooseItem3)
    MyChooseItemView chooseItem3;
    @BindView(R.id.rgChannel)
    RadioGroup rgChannel;
    @BindView(R.id.hvChannel)
    HorizontalScrollView hvChannel;
    @BindView(R.id.recommend_llt)
    LinearLayout recommendLlt;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.slidinglayout)
    TabLayout slidinglayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.activity_main)
    CoordinatorLayout activityMain;


    private TabFragmentAdapter adapter = null;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<MyChooseItemView> textViewList;

    private String title = "";
    private int functionType = 1;

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context, int type) {
        Intent intent = new Intent(context, FunctionItemActivity_.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_item_);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {

        functionType = getIntent().getExtras().getInt("type");
        switch (functionType) {
            case 1:
                title = "美食";
                break;
            case 2:
                title = "酒店";
                break;
            case 3:
                title = "爱车";
                break;
            case 4:
                title = "美容美发";
                break;
            case 5:
                title = "电影";
                break;
            case 6:
                title = "生鲜";
                break;
            case 7:
                title = "金融";
                break;
            case 8:
                title = "洗浴/KTV";
                break;
            case 9:
                title = "优币专区";
                break;
            case 10:
                title = "其他分类等";
                break;
        }

        setTitle(title);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FastDoubleClickUtil.isFastDoubleClick()) {
                    SearchActivity.startAction(FunctionItemActivity_.this);
                }
            }
        });
        rgChannel.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        viewPager.setCurrentItem(checkedId);
                    }
                });
        viewPager.setOnPageChangeListener(this);
        //    initTab();//动态产生RadioButton
        textViewList = new ArrayList<MyChooseItemView>();
        textViewList.add(chooseItem0);
        textViewList.add(chooseItem1);
        textViewList.add(chooseItem2);
        textViewList.add(chooseItem3);

        chooseItem(0);
        int i = 0;
        for (MyChooseItemView item : textViewList) {
            final int position = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseItem(position);
                }
            });
            i++;
        }

        requestRecommend();
        requestFoodType();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestRecommend();
                EventBus.getDefault().post(new RefreshMerchantListEvent(true, typeList.get(chooseStatus).getPid(), 1));
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                EventBus.getDefault().post(new RefreshMerchantListEvent(true, typeList.get(chooseStatus).getPid()));
            }
        });
    }

    public int chooseStatus = 0;

    protected void chooseItem(int position) {
        chooseStatus = position;
        if (!fragmentList.isEmpty() && viewPager.getAdapter() != null) {
            ((FunctionItemListFragment_) fragmentList.get(viewPager.getCurrentItem())).refreshData(chooseStatus);
        }
        try {
            for (int i = 0; i < textViewList.size(); i++) {
                if (i == position) {
                    textViewList.get(i).setChoose(true);
                } else {
                    textViewList.get(i).setChoose(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void requestRecommend() {
        Map<String, Object> params = new HashMap<>();
        params.put("countyId", (LocationInfo.getInstance().getChooseCity().getCountyCode() == 0) ? LocationInfo.getInstance().getChooseCity().getCityID() : LocationInfo.getInstance().getChooseCity().getCountyCode());
        params.put("industry", functionType);
        WebUtil.getInstance().requestPOST(this, URLConstant.RECOMMEND_NICE_MERCHANT, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        setRecommendMerchant(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setRecommendMerchant(String resultJson) {
        List<YmdRecommendEntity> list = new Gson().fromJson(resultJson, new TypeToken<List<YmdRecommendEntity>>() {
        }.getType());
        if (list == null || list.isEmpty()) {
            recommendLlt.setVisibility(View.GONE);
            return;
        } else {
            recommendLlt.setVisibility(View.VISIBLE);
        }
        //开始添加数据
        for (int x = 0; x < list.size(); x++) {
            YmdRecommendEntity item = list.get(x);
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(this).inflate(R.layout.item_nice_food_recommend, recommendLayout, false);

            ImageView icon_iv;
            TextView name_tv;
            TextView desc_tv;
            TextView now_price_tv;
            TextView buy_btn;
            TextView old_price_tv;
            icon_iv = (ImageView) view.findViewById(R.id.icon_iv);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            desc_tv = (TextView) view.findViewById(R.id.desc_tv);
            now_price_tv = (TextView) view.findViewById(R.id.now_price_tv);
            buy_btn = (TextView) view.findViewById(R.id.buy_btn);
            old_price_tv = (TextView) view.findViewById(R.id.old_price_tv);
            old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            if (ToolUtil.changeString(item.getPhoto()).length() > 0) {
                Glide.with(this).load(ToolUtil.changeString(item.getPhoto())).into(icon_iv);
            }
            name_tv.setText(ToolUtil.changeString(item.getGoodsName()));
            desc_tv.setText(ToolUtil.changeString(item.getMerchantName()));
            now_price_tv.setText(ToolUtil.changeString(item.getGoodsPrice()));
            //    now_price_tv.setText(ToolUtil.changeString(item.get));
            //将int数组中的数据放到ImageView中
            //    icon_iv.setImageResource(ToolUtil.changeInteger(datas.get(x).get("icon")));
            Glide.with(this).load(item.getPhoto()).into(icon_iv);
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MerchantDetailActivity.startAction(FunctionItemActivity_.this, item.getMerchantId(), functionType);
                }
            });
            recommendLayout.addView(view);
        }
    }

    /**
     * 获取种类
     */
    private void requestFoodType() {
        Map<String, Object> params = new HashMap<>();
        params.put("pid", functionType);
        WebUtil.getInstance().requestPOST(this, URLConstant.QUERY_FOOD_TYPE_FUNCTIONS, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        initTab(resultJson.optString("list"));
                        initViewPager();
                        setTab(0);
                        rgChannel.check(0);
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    List<YmdIndustryEntity> typeList = new ArrayList<>();

    private void initTab(String functionJson) {
        typeList = new Gson().fromJson(functionJson, new TypeToken<List<YmdIndustryEntity>>() {
        }.getType());
        YmdIndustryEntity firstType = new YmdIndustryEntity();
        firstType.setName("全部");
        firstType.setPid(-1l);
        if (typeList == null) {
            typeList = new ArrayList<>();
        }
        typeList.add(0, firstType);
        for (int i = 0; i < typeList.size(); i++) {
            RadioButton rb = (RadioButton) LayoutInflater.from(this).
                    inflate(R.layout.tab_rb, null);
            rb.setId(i);
            rb.setText(typeList.get(i).getName());
            RadioGroup.LayoutParams params = new
                    RadioGroup.LayoutParams((int) getResources().getDimension(R.dimen.mar_pad_len_200px),
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            rgChannel.addView(rb, params);
        }

    }

    //tab名的列表
    private List<String> mTitles = new ArrayList<>();
    private void initViewPager() {
        for (int i = 0; i < typeList.size(); i++) {
            mTitles.add(typeList.get(i).getName());
            FunctionItemListFragment_ frag = FunctionItemListFragment_.newInstance(chooseStatus, typeList.get(i).getPid(), functionType);
      /*      Bundle bundle=new Bundle();
            bundle.putString("weburl", channelList.get(i).getWeburl());
            bundle.putString("name", channelList.get(i).getName());
            frag.setArguments(bundle);  */   //向Fragment传入数据
            fragmentList.add(frag);
        }
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, mTitles);
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(0);
    }

    /**
     * 滑动ViewPager时调整ScroollView的位置以便显示按钮
     *
     * @param idx
     */
    private void setTab(int idx) {

        if (rgChannel.getChildCount() == 0) {
            return;
        }
        for (int i = 0; i < rgChannel.getChildCount(); i++) {
            RadioButton item = (RadioButton) rgChannel.getChildAt(i);

            item.setTextColor(getResources().getColor(R.color.text_gray_dark));
        }
        RadioButton rb = (RadioButton) rgChannel.getChildAt(idx);
        rb.setTextColor(getResources().getColor(R.color.bg_header));
        rb.setChecked(true);
        int left = rb.getLeft();
        int width = rb.getMeasuredWidth();
        DisplayMetrics metrics = new DisplayMetrics();
        super.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int len = left + width / 2 - screenWidth / 2;
        hvChannel.smoothScrollTo(len, 0);//滑动ScroollView
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageSend(RefreshEndEvent endEvent) {
        if (endEvent.isFinish()) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadmore();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
