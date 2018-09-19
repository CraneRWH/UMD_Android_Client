package com.ymd.client.component.activity.homePage.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.flowlayout.FlowLayout;
import com.ymd.client.component.widget.flowlayout.TagAdapter;
import com.ymd.client.component.widget.flowlayout.TagFlowLayout;
import com.ymd.client.model.constant.URLConstant;
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

    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.hot_flt)
    TagFlowLayout hotFlt;
    @BindView(R.id.history_flt)
    TagFlowLayout historyFlt;
    @BindView(R.id.history_lt)
    LinearLayout historyLt;

    private List<String> hotStrs = new ArrayList<>();
    private List<String> historyStrs = new ArrayList<>();
    //布局管理器
    private LayoutInflater mInflater;

    /**
     * 启动
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

        mInflater = LayoutInflater.from(this);
        //流式布局tag的点击方法
        historyFlt.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(SearchActivity.this, historyStrs.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = searchEt.getText().toString().trim();
                if (aa.length() >0) {
                    historyStrs.add(aa);
                    //通知handler更新UI
                    handler.sendEmptyMessageDelayed(1, 0);
                }
            }
        });

        hotStrs.add("土豆");
        hotStrs.add("麻辣烫");
        hotStrs.add("驴肉火烧");
        hotStrs.add("半天妖烤鱼");
        hotStrs.add("张亮麻辣烫");
        hotStrs.add("鱼");
        hotStrs.add("拉面");
        hotStrs.add("土豆");
        hotStrs.add("麻辣烫");
        hotStrs.add("驴肉火烧");
        hotStrs.add("半天妖烤鱼");

        historyStrs.add("张亮麻辣烫");
        historyStrs.add("鱼");
        historyStrs.add("拉面");
        historyStrs.add("土豆");
        historyStrs.add("麻辣烫");
        historyStrs.add("驴肉火烧");
        historyStrs.add("半天妖烤鱼");
        historyStrs.add("张亮麻辣烫");
        historyStrs.add("鱼");
        historyStrs.add("拉面");

        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(2);
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
        WebUtil.getInstance().requestPOST(this, URLConstant.QUEYR_U_LIST, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                    }
                });
    }
}
