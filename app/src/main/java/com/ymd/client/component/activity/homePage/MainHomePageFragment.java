package com.ymd.client.component.activity.homePage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.common.helper.UmdClassicsHeader;
import com.ymd.client.component.activity.homePage.city.CityChooseActivity;
import com.ymd.client.component.activity.homePage.merchant.MerchantDetailActivity;
import com.ymd.client.component.activity.homePage.functionItem.FunctionItemActivity;
import com.ymd.client.component.activity.homePage.scan.ScanCodeActivity;
import com.ymd.client.component.activity.homePage.search.SearchActivity;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.adapter.goods.MerchantListAdapter;
import com.ymd.client.component.event.CityShowEvent;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.component.widget.pullRefreshView.PullToRefreshLayout;
import com.ymd.client.component.widget.pullRefreshView.PullableScrollView;
import com.ymd.client.component.widget.recyclerView.MyGridView;
import com.ymd.client.model.bean.homePage.DiscountsMerchantEntity;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.PictureEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.DataUtils;
import com.ymd.client.utils.DeviceUtil;
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
import butterknife.Unbinder;

/**
 * 作者:rongweihe
 * 日期:2018/8/18
 * 描述:    主页选项卡
 * 修改历史:
 */
public class MainHomePageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.head_bar_llt)
    LinearLayout headBarLlt;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.location_llt)
    LinearLayout locationLlt;
    @BindView(R.id.sao_iv)
    ImageView saoIv;
    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    @BindView(R.id.rollPagerView)
    RollPagerView rollPagerView;
    @BindView(R.id.imageNumBar)
    LinearLayout imageNumBar;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.gridView)
    MyGridView gridView;
    @BindView(R.id.youhuiServiceLayout)
    LinearLayout youhuiServiceLayout;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.chooseItem0)
    MyChooseItemView chooseItem0;
    @BindView(R.id.chooseItem1)
    MyChooseItemView chooseItem1;
    @BindView(R.id.chooseItem2)
    MyChooseItemView chooseItem2;
    @BindView(R.id.chooseItem3)
    MyChooseItemView chooseItem3;
    @BindView(R.id.businessView)
    LinearLayout businessView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bigLayout)
    SmartRefreshLayout bigLayout;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    int mAlpha = 0;

    private List<MyChooseItemView> textViewList;

    public static MainHomePageFragment newInstance(String param1, String param2) {
        MainHomePageFragment fragment = new MainHomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        locationLlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityChooseActivity.startAction(getActivity());
            }
        });
        bigLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                onRefreshData();
            }
        });
        bigLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page ++;
                requestMerchant(chooseStatus);
            }
        });
        UmdClassicsHeader classicsHeader = new UmdClassicsHeader(getContext());
        classicsHeader.setStartRefreshListener(new UmdClassicsHeader.StartRefreshListener() {
            @Override
            public void onStart() {
                headBarLlt.setVisibility(View.GONE);
            }

            @Override
            public void onEnd() {
                headBarLlt.setVisibility(View.VISIBLE);
            }
        });
        bigLayout.setRefreshHeader(classicsHeader);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                /**  ScrollView 滚动动态改变标题栏 */
                // 滑动的最小距离（自行定义，you happy jiu ok）
                int minHeight = 50;
                // 滑动的最大距离（自行定义，you happy jiu ok）
                int maxHeight = 400;
                // 滑动距离小于定义得最小距离
                if (v.getScrollY() <= minHeight) {
                    mAlpha = 0;
                }
                // 滑动距离大于定义得最大距离
                else if (v.getScrollY() > maxHeight) {
                    mAlpha = 255;
                }
                // 滑动距离处于最小和最大距离之间
                else {
                    // （滑动距离 - 开始变化距离）：最大限制距离 = mAlpha ：255
                    mAlpha = (v.getScrollY() - minHeight) * 255 / (maxHeight - minHeight);
                }
                // 初始状态 标题栏/导航栏透明等
                if (mAlpha <= 0) {
                    setViewBackgroundAlpha(headBarLlt, 0);
                }
                //  终止状态：标题栏/导航栏 不在进行变化
                else if (mAlpha >= 255) {
                    setViewBackgroundAlpha(headBarLlt, 255);
                }
                // 变化中状态：标题栏/导航栏随ScrollView 的滑动而产生相应变化
                else {
                    setViewBackgroundAlpha(headBarLlt, mAlpha);
                }
            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FastDoubleClickUtil.isFastDoubleClick()) {
                    SearchActivity.startAction(getActivity());
                }
            }
        });
        saoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), ScanCodeActivity.class));
            }
        });

        textViewList = new ArrayList<MyChooseItemView>();
        textViewList.add(chooseItem0);
        textViewList.add(chooseItem1);
        textViewList.add(chooseItem2);
        textViewList.add(chooseItem3);


        for (int i = 0 ; i < textViewList.size() ; i ++ ) {
            final int position = i;
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    chooseItem(position);
                }
            });
        }

        ViewGroup.LayoutParams layoutParams = rollPagerView.getLayoutParams();
        layoutParams.height = (int)(DeviceUtil.getWidth(getActivity()) * 3 / 5);
        rollPagerView.setLayoutParams(layoutParams);

        setViewBackgroundAlpha(headBarLlt, 0);

        onRefreshData();
    }

    /**
     * 设置View的背景透明度
     *
     * @param view
     * @param alpha
     */
    public void setViewBackgroundAlpha(View view, int alpha) {
        if (view == null) return;
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
    }

    /**
     * 刷新页面数据
     */
    private void onRefreshData() {

        requestPicture();
        requestYH();
        chooseItem(0);
        onRefreshCityName();
        //   requestFunctions();
        setFunctionItem();
    }


    public int chooseStatus = 0;

    protected void chooseItem(int position) {
        chooseStatus = position;
        page = 1;
        requestMerchant(position);
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

    private void requestPicture() {

        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.UMD_PIC, null, new WebUtil.WebCallBack() {
            @Override
            public void onWebSuccess(JSONObject resultJson) {
                setPicture(resultJson.optString("list"));
            }

            @Override
            public void onWebFailed(String errorMsg) {

            }
        });
    }

    /**
     * 设置广告图片
     */
    private void setPicture(String picturesJson) {
        List<PictureEntity> list = new Gson().fromJson(picturesJson, new TypeToken<List<PictureEntity>>(){}.getType());
        //设置播放时间间隔
        rollPagerView.setPlayDelay(3000);
        //设置透明度
        rollPagerView.setAnimationDurtion(500);
        TestNormalAdapter advAdapter = new TestNormalAdapter(list);
        //设置适配器
        rollPagerView.setAdapter(advAdapter);

    }

    /*
     * 刷新城市名称和车辆信息
     */
    public void onRefreshCityName() {

        if (ToolUtil.changeString(LocationInfo.getInstance().getChooseCity().getCountyName()).length() > 0) {
            locationTv.setText(LocationInfo.getInstance().getChooseCity().getCountyName());
        } else {
            locationTv.setText(LocationInfo.getInstance().getChooseCity().getCityName());
        }

    }

    /**
     * 设置广告导航的颜色
     *
     * @param position
     */
    private void setHintView(int position) {
        for (int i = 0; i < imageNumBar.getChildCount(); i++) {
            if (position == i) {
                imageNumBar.getChildAt(i).setBackgroundResource(R.color.bg_header);
            } else {
                imageNumBar.getChildAt(i).setBackgroundResource(R.color.text_gray);
            }
        }
    }

    /**
     * 设置功能选项
     */
    private void setFunctionItem(/*String functionJson*/) {
        List<Map<String ,Object>> list = new ArrayList<>();
        list.addAll(DataUtils.getFunctionsData());

        MySimpleAdapter adapter = new MySimpleAdapter(getActivity(), list, R.layout.function_item,
                new String[]{"name", "icon"}, new int[]{R.id.itemText, R.id.itemImage},
                new MySimpleAdapter.MyViewListener() {
                    @Override
                    public void callBackViewListener(Map<String, Object> data, View view, ViewGroup parent, int position) {
                        ImageView img = (ImageView) view.findViewById(R.id.itemImage);
                        //       Glide.with(getActivity()).load(itemList.get(position).getImgUrl()).into(img);
                    }
                });
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FunctionItemActivity.startAction(getActivity(), i+1);
            }
        });
    }

    private void requestYH() {
        Map<String,Object> params = new HashMap<>();
        params.put("countyId",LocationInfo.getInstance().getChooseCity().getCountyCode() > 0 ? LocationInfo.getInstance().getChooseCity().getCountyCode() : LocationInfo.getInstance().getChooseCity().getCityID());
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.UMD_UH_PIC, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        setYouHuiItem(resultJson.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setYouHuiItem(String datas) {
        List<DiscountsMerchantEntity> list = new Gson().fromJson(datas, new TypeToken<List<DiscountsMerchantEntity>>(){}.getType());

        youhuiServiceLayout.removeAllViews();
        //开始添加数据
        for(int i=0; i<list.size(); i++){
            final int position = i;
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.main_preferential_item , youhuiServiceLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.itemImage);
            //实例化TextView控件
            Glide.with(getActivity()).load(list.get(i).getPhoto()).into(img);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MerchantDetailActivity.startAction(getActivity(), list.get(position).getMerchantId(), 1);
                }
            });
            //把行布局放到linear里
            youhuiServiceLayout.addView(view);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private List<PictureEntity> datas;

        public TestNormalAdapter(List<PictureEntity> datas) {
            this.datas = datas;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            //    view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getActivity()).load(datas.get(position).getUrl()).into(view);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 千万别忘了告诉控件刷新完毕了哦！
            try {
                bigLayout.finishRefresh();
                bigLayout.finishLoadmore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     *
     */
    int page = 1;

    /**
     * 根据城市获取商户列表
     * @param type
     */
    private void requestMerchant(int type){
        Map<String,Object> params = new HashMap<>();
        params.put("county",ToolUtil.changeInteger(LocationInfo.getInstance().getChooseCity().getCountyCode()) > 0 ? LocationInfo.getInstance().getChooseCity().getCountyCode() : "");
        params.put("city", LocationInfo.getInstance().getChooseCity().getCityID() > 0 ? LocationInfo.getInstance().getChooseCity().getCityID() : "");
        params.put("latitude",LocationInfo.getInstance().getLocationInfo().getLatitude());
        params.put("longitude",LocationInfo.getInstance().getLocationInfo().getLongitude());
        params.put("pageNum", page);
        String method = URLConstant.COMPREHENSIVE_MERCHANT;
        switch (type){
            case 0:
                method = URLConstant.COMPREHENSIVE_MERCHANT;
                break;
            case 1:
                method = URLConstant.SALES_MERCHANT;
                break;
            case 2:
                method = URLConstant.PRAISE_MERCHANT;
                break;
            case 3:
                method = URLConstant.NEAR_MERCHANT;
                break;
        }
        WebUtil.getInstance().requestPOST(getActivity(), method, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        resetMerchantData(resultJson.optString("list"));
                        refreshHandler.sendEmptyMessageDelayed(0, 0);
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        refreshHandler.sendEmptyMessageDelayed(0, 0);
                    }
                });

    }

    List<MerchantInfoEntity> marchantDatas = new ArrayList<>();
    private void resetMerchantData(String result) {
        List<MerchantInfoEntity> datas = new Gson().fromJson(result, new TypeToken<List<MerchantInfoEntity>>(){}.getType());
        if (page == 1) {
            marchantDatas.clear();
        }
        if (datas != null && !datas.isEmpty()) {
            page ++;
        }
        marchantDatas.addAll(datas);
        //     LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        MerchantListAdapter adapter = new MerchantListAdapter(marchantDatas, getContext());
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                MerchantInfoEntity item = (MerchantInfoEntity) data;
                MerchantDetailActivity.startAction(getActivity(), item, 1);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void requestFunctions() {
        Map<String,Object> params = new HashMap<>();
        params.put("pid",null);
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.QUERY_HOME_FUNCTIONS, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        //    setFunctionItem(resultJson.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CityShowEvent event) {
        if (event.isRefresh()) {
            onRefreshCityName();
            onRefreshData();
        }
    }
}