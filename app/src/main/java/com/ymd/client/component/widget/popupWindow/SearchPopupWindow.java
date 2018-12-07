package com.ymd.client.component.widget.popupWindow;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.component.activity.homePage.search.SearchActivity;
import com.ymd.client.component.widget.flowlayout.FlowLayout;
import com.ymd.client.component.widget.flowlayout.TagAdapter;
import com.ymd.client.component.widget.flowlayout.TagFlowLayout;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SearchPopupWindow extends PopupWindow {
    TagFlowLayout hotFlt;
    TagFlowLayout historyFlt;
    private View contentView;
    private Activity activity;

    private List<String> hotStrs = new ArrayList<>();
    private List<String> historyStrs = new ArrayList<>();

    private ResultListener listener;

    private static final String SEARCH_HISTORY = "searchHistory";

    //布局管理器
    private LayoutInflater mInflater;

    public SearchPopupWindow(Activity context, ResultListener listener) {
        this.activity = context;
        this.listener = listener;
        onCreate();
    }

    private void onCreate() {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = mInflater.inflate(R.layout.search_popup_window, null);
        this.setContentView(contentView);

        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        //设置弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //实例化一个ColorDrawable颜色为半透明
 //       ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.white));
        //点back键和其他地方使其小时，设置了这个才能出发OnDismissListener,设置其他控件变化等操作
  //      this.setBackgroundDrawable(dw);
        //设置弹出船体动画效果
        this.setAnimationStyle(android.R.style.Animation_Dialog);

        initView();
    }

    private void initView() {
        hotFlt = (TagFlowLayout) contentView.findViewById(R.id.hot_flt);
        historyFlt = (TagFlowLayout) contentView.findViewById(R.id.history_flt);

        historyStrs = new Gson().fromJson(CommonShared.getString(SEARCH_HISTORY, ""), new TypeToken<List<String>>() {
        }.getType());
        if (historyStrs == null) {
            historyStrs = new ArrayList<>();
        }

        //流式布局tag的点击方法
        historyFlt.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (listener != null) {
                    listener.onResult(historyStrs.get(position));
                    dismiss();
                }
                return true;
            }
        });
        hotFlt.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (listener != null) {
                    listener.onResult(hotStrs.get(position));
                    dismiss();
                }
                return true;
            }
        });

        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
        requestHotSearch();
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
        WebUtil.getInstance().requestPOST(activity, URLConstant.QUEYR_HOT_SEARCH, params, false,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {

                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        hotStrs.add("拉面");
                        hotStrs.add("土豆");
                        hotStrs.add("麻辣烫");
                        hotStrs.add("驴肉火烧");
                        hotStrs.add("半天妖烤鱼");
                        handler.sendEmptyMessageDelayed(2, 0);
                    }
                });
    }

    public void addHistoryItem(String item) {
        if (historyStrs.contains(item)) {
            historyStrs.remove(item);
        }
        historyStrs.add(0,item);

        CommonShared.setString(SEARCH_HISTORY, new Gson().toJson(historyStrs));
        //通知handler更新UI
        handler.sendEmptyMessageDelayed(1, 0);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow  
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

    public interface ResultListener {
        public void onResult(String item);
    }

}
