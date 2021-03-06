package com.ymd.client.component.activity.homePage.search;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.homePage.merchant.MerchantDetailActivity;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.component.adapter.goods.MerchantListAdapter;
import com.ymd.client.component.widget.flowlayout.FlowLayout;
import com.ymd.client.component.widget.flowlayout.TagAdapter;
import com.ymd.client.component.widget.flowlayout.TagFlowLayout;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.component.widget.popupWindow.SearchPopupWindow;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.utils.helper.AndroidAdjustResizeBugFix;
import com.ymd.client.utils.helper.SoftKeyBoardListener;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/28
 * 描述:    搜索界面
 * 修改历史:
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView backIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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
    @BindView(R.id.result_LLT)
    LinearLayout resultLLT;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.mSearchLayout)
    LinearLayout mSearchLayout;
    @BindView(R.id.search_llt)
    LinearLayout searchLlt;

    @BindView(R.id.hot_flt)
    TagFlowLayout hotFlt;
    @BindView(R.id.history_flt)
    TagFlowLayout historyFlt;
    @BindView(R.id.show_item)
    LinearLayout showItem;

    private List<MyChooseItemView> textViewList;

    SearchPopupWindow searchPopupWindow;

    private void showWindow(boolean isShow) {
        if (isShow) {
            showItem.setVisibility(View.VISIBLE);
            resultLLT.setVisibility(View.GONE);
        } else {
            resultLLT.setVisibility(View.VISIBLE);
            showItem.setVisibility(View.GONE);
        }
    }

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent = searchEt.getText().toString().trim();

                chooseItem(chooseStatus);
                requestMerchant(chooseStatus);
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textViewList = new ArrayList<MyChooseItemView>();
        textViewList.add(chooseItem0);
        textViewList.add(chooseItem1);
        textViewList.add(chooseItem2);
        textViewList.add(chooseItem3);

        for (int i = 0; i < textViewList.size(); i++) {
            final int position = i;
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    chooseItem(chooseStatus);
                    requestMerchant(chooseStatus);
                }
            });
        }

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                requestMerchant(chooseStatus);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;

                chooseItem(chooseStatus);
                requestMerchant(chooseStatus);
            }
        });
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                showWindow(true);
            }

            @Override
            public void keyBoardHide(int height) {
                showWindow(false);
            }
        });

        initShowItem();
    }

    public int chooseStatus = 0;

    protected void chooseItem(int position) {
        chooseStatus = position;
        page = 1;
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

    int page = 1;
    private String searchContent;

    /**
     * 根据城市获取商户列表
     *
     * @param type
     */
    private void requestMerchant(int type) {
        if (TextUtils.isEmpty(searchContent)) {
            ToastUtil.ToastMessage(this, "请输入搜索内容");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("county", ToolUtil.changeInteger(LocationInfo.getInstance().getChooseCity().getCountyCode()) > 0 ? LocationInfo.getInstance().getChooseCity().getCountyCode() : "");
        params.put("city", LocationInfo.getInstance().getChooseCity().getCityID() > 0 ? LocationInfo.getInstance().getChooseCity().getCityID() : "");
        params.put("latitude", LocationInfo.getInstance().getLocationInfo().getLatitude());
        params.put("longitude", LocationInfo.getInstance().getLocationInfo().getLongitude());
        params.put("pageNum", page);
        params.put("name", ToolUtil.changeString(searchEt.getText()));
        String method = URLConstant.COMPREHENSIVE_MERCHANT;
        switch (type) {
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
        addHistoryItem(searchContent);
        WebUtil.getInstance().requestPOST(this, method, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        resetMerchantData(resultJson.optString("list"));
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        refreshLayout.finishLoadmore();
                        refreshLayout.finishRefresh();
                    }
                });

    }

    List<MerchantInfoEntity> marchantDatas = new ArrayList<>();

    private void resetMerchantData(String result) {
        List<MerchantInfoEntity> datas = new Gson().fromJson(result, new TypeToken<List<MerchantInfoEntity>>() {
        }.getType());
        if (page == 1) {
            marchantDatas.clear();
        }
        marchantDatas.addAll(datas);
        //     LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
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
        MerchantListAdapter adapter = new MerchantListAdapter(marchantDatas, this);
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                MerchantInfoEntity item = (MerchantInfoEntity) data;
                MerchantDetailActivity.startAction(SearchActivity.this, item, 1);
            }
        });
        recyclerView.setAdapter(adapter);
        page++;
    }

    private List<String> hotStrs = new ArrayList<>();
    private List<String> historyStrs = new ArrayList<>();

    private SearchPopupWindow.ResultListener listener;

    private static final String SEARCH_HISTORY = "searchHistory";
    //布局管理器
    private LayoutInflater mInflater;
    private void initShowItem() {
        mInflater = this.getLayoutInflater();
        historyStrs = new Gson().fromJson(CommonShared.getString(SEARCH_HISTORY, ""), new TypeToken<List<String>>() {
        }.getType());
        if (historyStrs == null) {
            historyStrs = new ArrayList<>();
        }

        //流式布局tag的点击方法
        historyFlt.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                quickSearch(historyStrs.get(position));
                return true;
            }
        });
        hotFlt.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                quickSearch(hotStrs.get(position));
                return true;
            }
        });

        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
        requestHotSearch();
    }

    private void quickSearch(String item) {
        page = 1;
        searchContent = item;

        chooseItem(chooseStatus);
        requestMerchant(chooseStatus);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    hotFlt.setAdapter(new TagAdapter<String>(hotStrs) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.item_search_history_text,
                                    hotFlt, false);
                            tv.setText(s);
                            return tv;
                        }
                    });
                    break;
                case 2:
                    historyFlt.setAdapter(new TagAdapter<String>(historyStrs) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            TextView tv = (TextView) mInflater.inflate(R.layout.item_search_history_text,
                                    historyFlt, false);
                            tv.setText(s);
                            return tv;
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void requestHotSearch() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        WebUtil.getInstance().requestPOST(this, URLConstant.QUEYR_HOT_SEARCH, params, false,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        List<String> hots = new Gson().fromJson(result.optString("list"), new TypeToken<List<String>>(){}.getType());
                        hotStrs.clear();
                        hotStrs.addAll(hots);
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        handler.sendEmptyMessage(1);
                    }
                });
    }

    private void addHistoryItem(String item) {
        if (historyStrs.contains(item)) {
            historyStrs.remove(item);
        }
        historyStrs.add(0,item);

        CommonShared.setString(SEARCH_HISTORY, new Gson().toJson(historyStrs));
        //通知handler更新UI
        handler.sendEmptyMessage(2);
    }
}
