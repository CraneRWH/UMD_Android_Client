package com.ymd.client.component.activity.homePage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.widget.pullRefreshView.PullToRefreshLayout;
import com.ymd.client.component.widget.pullRefreshView.PullableScrollView;
import com.ymd.client.component.widget.recyclerView.MListView;
import com.ymd.client.component.widget.recyclerView.MyGridView;
import com.ymd.client.component.widget.viewPager.MyPosterView;
import com.ymd.client.component.widget.viewPager.MyViewPager;
import com.ymd.client.utils.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者:rongweihe
 * 日期:2018/8/18
 * 描述:    主页选项卡
 * 修改历史:
 */
public class MainHomePageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView locationTv;
    private LinearLayout locationLlt;
    private ImageView saoIv;
    private RelativeLayout searchLayout;
    private ImageView pullIcon;
    private ImageView refreshingIcon;
    private TextView stateTv;
    private ImageView stateIv;
    private RelativeLayout headView;
    private MyViewPager viewPager;
    private LinearLayout imageNumBar;
    private MyPosterView homePoster;
    private LinearLayout layout;
    private MyGridView gridView;
    private MyGridView gridView2;
    private MListView listView;
    private PullableScrollView scrollView;
    private ImageView pullupIcon;
    private ImageView loadingIcon;
    private TextView loadstateTv;
    private ImageView loadstateIv;
    private RelativeLayout loadmoreView;
    private PullToRefreshLayout bigLayout;

    private LinearLayout youhuiServiceLayout;

    public MainHomePageFragment() {
        // Required empty public constructor
    }

    public static MainHomePageFragment newInstance(String param1, String param2) {
        MainHomePageFragment fragment = new MainHomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home_page, container, false);
        initView(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    /*    if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initView(View view) {
        locationTv = (TextView) view.findViewById(R.id.location_tv);
        locationLlt = (LinearLayout) view.findViewById(R.id.location_llt);
        saoIv = (ImageView) view.findViewById(R.id.sao_iv);
        searchLayout = (RelativeLayout) view.findViewById(R.id.search_layout);
        pullIcon = (ImageView) view.findViewById(R.id.pull_icon);
        refreshingIcon = (ImageView) view.findViewById(R.id.refreshing_icon);
        stateTv = (TextView) view.findViewById(R.id.state_tv);
        stateIv = (ImageView) view.findViewById(R.id.state_iv);
        headView = (RelativeLayout) view.findViewById(R.id.head_view);
        viewPager = (MyViewPager) view.findViewById(R.id.viewPager);
        imageNumBar = (LinearLayout) view.findViewById(R.id.imageNumBar);
        homePoster = (MyPosterView) view.findViewById(R.id.homePoster);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        gridView = (MyGridView) view.findViewById(R.id.gridView);
        listView = (MListView) view.findViewById(R.id.listView);
        scrollView = (PullableScrollView) view.findViewById(R.id.scrollView);
        pullupIcon = (ImageView) view.findViewById(R.id.pullup_icon);
        loadingIcon = (ImageView) view.findViewById(R.id.loading_icon);
        loadstateTv = (TextView) view.findViewById(R.id.loadstate_tv);
        loadstateIv = (ImageView) view.findViewById(R.id.loadstate_iv);
        loadmoreView = (RelativeLayout) view.findViewById(R.id.loadmore_view);
        bigLayout = (PullToRefreshLayout) view.findViewById(R.id.bigLayout);

        youhuiServiceLayout = (LinearLayout) view.findViewById(R.id.youhuiServiceLayout);

        bigLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });

        setFunctionItem();
        setYouHuiItem();
    }

    private void setPicture() {

    }

    private void setFunctionItem() {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","美食");
        map.put("icon", R.mipmap.food_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","酒店");
        map.put("icon", R.mipmap.hospital_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","爱车");
        map.put("icon", R.mipmap.car_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","美容美发");
        map.put("icon", R.mipmap.meirong_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","电影");
        map.put("icon", R.mipmap.movie_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","生鲜");
        map.put("icon", R.mipmap.shengxian_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","金融");
        map.put("icon", R.mipmap.jinrong_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","洗浴");
        map.put("icon", R.mipmap.xiyu_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","KTV");
        map.put("icon", R.mipmap.ktv_item_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","其他分类");
        map.put("icon", R.mipmap.other_item_icon);
        list.add(map);

        MySimpleAdapter adapter = new MySimpleAdapter(getActivity(), list, R.layout.function_item,
                new String[]{"name", "icon"}, new int[]{R.id.itemText, R.id.itemImage},
                new MySimpleAdapter.MyViewListener() {
                    @Override
                    public void callBackViewListener(Map<String, Object> data, View view, ViewGroup parent, int position) {

                    }
                });
        gridView.setAdapter(adapter);
    }

    private void setYouHuiItem() {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","美食");
        map.put("icon", R.mipmap.youhui1_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","酒店");
        map.put("icon", R.mipmap.youhui2_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","爱车");
        map.put("icon", R.mipmap.youhui1_icon);
        list.add(map);

        map = new HashMap<>();
        map.put("name","美容美发");
        map.put("icon", R.mipmap.youhui2_icon);
        list.add(map);

        //开始添加数据
        for(int x=0; x<list.size(); x++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.main_preferential_item , youhuiServiceLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.itemImage);
            //实例化TextView控件
         //   TextView tv= (TextView) view.findViewById(R.id.textView);
            //将int数组中的数据放到ImageView中
            img.setImageResource(ToolUtil.changeInteger(map.get("icon")));
            //给TextView添加文字
        //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            youhuiServiceLayout.addView(view);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
