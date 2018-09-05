package com.ymd.client.component.activity.homePage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.homePage.city.CityChooseActivity;
import com.ymd.client.component.activity.homePage.food.NiceFoodActivity;
import com.ymd.client.component.activity.homePage.food.seller.SellerDetailActivity;
import com.ymd.client.component.activity.homePage.scan.ScanCodeActivity;
import com.ymd.client.component.activity.homePage.search.SearchActivity;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.adapter.food.FoodListAdapter;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.component.widget.pullRefreshView.PullToRefreshLayout;
import com.ymd.client.component.widget.pullRefreshView.PullableScrollView;
import com.ymd.client.component.widget.recyclerView.MyGridView;
import com.ymd.client.model.bean.city.CityEntity;
import com.ymd.client.model.bean.homePage.DiscountsMerchantEntity;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.bean.homePage.PictureEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONException;
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
    @BindView(R.id.scrollView)
    PullableScrollView scrollView;
    @BindView(R.id.bigLayout)
    PullToRefreshLayout bigLayout;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private List<MyChooseItemView> textViewList;

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
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

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

    private void initView() {
        locationLlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityChooseActivity.startAction(getActivity());
            }
        });
        bigLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                refreshHandler.sendEmptyMessageDelayed(0, 3000);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startAction(getActivity());
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

       /* String picture = "{\"msg\":\"success\",\"code\":0,\"list\":[{\"name\":\"as\",\"url\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536121560488&di=f9cb9a1309058242731c4e3f6cf098c2&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F13%2F49%2F08%2F78bOOOPICe2_1024.jpg\",\"weight\":\"32\",\"status\":\"1\",\"page\":\"156165165\"},{\"name\":\"15\",\"url\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536121560485&di=81badb2a442991adb9193a287c996c47&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F3b87e950352ac65c870094e3f9f2b21193138a1d.jpg\",\"weight\":\"1\",\"status\":\"1\",\"page\":\"1\"},{\"name\":\"阿萨\",\"url\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536121560485&di=76919292ceb4648e94987afcaa4e30e0&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F2cf5e0fe9925bc318d90f62459df8db1ca1370b9.jpg\",\"weight\":\"1\",\"status\":\"1\",\"page\":\"15651561531\"}]}";
        try {
            JSONObject json = new JSONObject(picture);

            setPicture(json.optString("list"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        requestPicture();
        setFunctionItem();

        for (int i = 0 ; i < textViewList.size() ; i ++ ) {
            final int position = i;
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    chooseItem(position);
                }
            });
        }
        chooseItem(0);

        requestYH();
     /*   String youhui = "\n" +
                "{\n" +
                "  \"msg\": \"success\",\n" +
                "  \"code\": 0,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"photo\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536123084815&di=449efea3b987f3f5dda31ac9fd0a3fbe&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fbaike%2Fc0%253Dbaike150%252C5%252C5%252C150%252C50%2Fsign%3De486cd1c78f0f736ccf344536b3cd87c%2F1e30e924b899a90103eaa0551d950a7b0308f5f2.jpg\",\n" +
                "      \"merchantId\": 7,\n" +
                "      \"starttime\": \"2018-09-01 15:39:02\",\n" +
                "      \"endtime\": \"2018-09-15 15:39:06\",\n" +
                "      \"top\": \"\",\n" +
                "      \"state\": \"1\",\n" +
                "      \"weight\": \"20\",\n" +
                "      \"departmentCode\": \"000000010001\",\n" +
                "      \"merchantName\": null,\n" +
                "      \"conclusion\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"photo\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536123084817&di=62dca9c088b963b97dece6f28a0a6be5&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Flvpics%2Fh%3D800%2Fsign%3D0ddbd040b9a1cd111ab67f208912c8b0%2F79f0f736afc37931ff52b259e8c4b74543a91183.jpg\",\n" +
                "      \"merchantId\": 7,\n" +
                "      \"starttime\": \"2018-08-22 14:04:53\",\n" +
                "      \"endtime\": \"2018-09-06 14:04:55\",\n" +
                "      \"top\": \"\",\n" +
                "      \"state\": \"1\",\n" +
                "      \"weight\": \"15\",\n" +
                "      \"departmentCode\": \"000000010001\",\n" +
                "      \"merchantName\": null,\n" +
                "      \"conclusion\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"photo\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536123084819&di=fa8760bc4ee5ee241c75dbbcc67f1320&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fbainuo%2Fwh%3D720%2C436%2Fsign%3D7cf378e033fae6cd0ce1a3663d832310%2Fbd315c6034a85edf666967f44f540923dc547504.jpg\",\n" +
                "      \"merchantId\": 7,\n" +
                "      \"starttime\": \"2018-08-22 14:04:53\",\n" +
                "      \"endtime\": \"2018-09-06 14:04:55\",\n" +
                "      \"top\": \"\",\n" +
                "      \"state\": \"1\",\n" +
                "      \"weight\": \"15\",\n" +
                "      \"departmentCode\": \"000000010001\",\n" +
                "      \"merchantName\": null,\n" +
                "      \"conclusion\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"photo\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536123103491&di=71444619e7473207405cd5edd45d72d2&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D981269532%2C129067962%26fm%3D214%26gp%3D0.jpg\",\n" +
                "      \"merchantId\": 7,\n" +
                "      \"starttime\": \"2018-08-22 14:04:53\",\n" +
                "      \"endtime\": \"2018-09-06 14:04:55\",\n" +
                "      \"top\": \"\",\n" +
                "      \"state\": \"1\",\n" +
                "      \"weight\": \"15\",\n" +
                "      \"departmentCode\": \"000000010001\",\n" +
                "      \"merchantName\": null,\n" +
                "      \"conclusion\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"photo\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536123084825&di=b1b4289d08d13ad74db54b0cc3cb891b&imgtype=0&src=http%3A%2F%2Fimgsa.baidu.com%2Fexp%2Fw%3D500%2Fsign%3D34bf2209227f9e2f70351d082f31e962%2F08f790529822720e99c772157ecb0a46f31fabd0.jpg\",\n" +
                "      \"merchantId\": 7,\n" +
                "      \"starttime\": \"2018-08-22 14:04:53\",\n" +
                "      \"endtime\": \"2018-09-06 14:04:55\",\n" +
                "      \"top\": \"\",\n" +
                "      \"state\": \"1\",\n" +
                "      \"weight\": \"15\",\n" +
                "      \"departmentCode\": \"000000010001\",\n" +
                "      \"merchantName\": null,\n" +
                "      \"conclusion\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            JSONObject json = new JSONObject(youhui);

            setYouHuiItem(json.optString("list"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        onRefresh();
        LocationInfo.getInstance().setChangeListener(new LocationInfo.OnCityChangeListener() {
            @Override
            public void onChange(CityEntity cityEntity) {
                onRefresh();
            }
        });
    }

    public int chooseStatus = 0;

    protected void chooseItem(int position) {
        chooseStatus = position;
        requestMerchant(position);
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

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        //   mRollViewPager.setHintView(new ColorPointHintView(getActivity(), getActivity().getColor(R.color.color_orange),Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        /*mRollViewPager.setHintView(null);
        mRollViewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int position = mRollViewPager.getGravity();
                setHintView(position);
            }
        });
        imageNumBar.removeAllViews();
        for(int i= 0 ; i < advAdapter.getCount(); i ++ ) {
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.main_adv_hint_item , youhuiServiceLayout,false);

            TextView textView = (TextView) view.findViewById(R.id.itemText);

            if (i == mRollViewPager.getGravity()) {
                textView.setBackgroundResource(R.color.bg_header);
            } else {
                textView.setBackgroundResource(R.color.text_gray);
            }
            imageNumBar.addView(textView);
        }*/
    }

    /*
     * 刷新城市名称和车辆信息
     */
    public void onRefresh() {
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
    private void setFunctionItem() {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","美事");
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    NiceFoodActivity.startAction(getActivity());
                }
            }
        });
    }

    private void requestYH() {
        Map<String,Object> params = new HashMap<>();
        params.put("countyId", "130406");
    /*    params.put("county",LocationInfo.getInstance().getChooseCity().getCountyName());
        params.put("city", LocationInfo.getInstance().getChooseCity().getCityID());
        params.put("latitude",LocationInfo.getInstance().getLocationInfo().getLatitude());
        params.put("longitude",LocationInfo.getInstance().getLocationInfo().getLongitude());*/
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

        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.main_preferential_item , youhuiServiceLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.itemImage);
            //实例化TextView控件
            Glide.with(getActivity()).load(list.get(i).getPhoto()).into(img);
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
                bigLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
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
        params.put("county","130406");
        params.put("city", LocationInfo.getInstance().getChooseCity().getCityID());
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
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });

    }

    private void resetMerchantData(String result) {
        List<MerchantInfoEntity> datas = new Gson().fromJson(result, new TypeToken<List<MerchantInfoEntity>>(){}.getType());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        FoodListAdapter adapter = new FoodListAdapter(datas, getContext());
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                SellerDetailActivity.startAction(getActivity());
            }
        });
        recyclerView.setAdapter(adapter);
    }


    private List getData() {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "半天妖烤鱼");
        map.put("distance","253m");
        map.put("point",4);
        map.put("work_time", "9:00~21:30");
        map.put("dis_str","全场");
        map.put("dis_num", "8.6折");
        map.put("price", 86);
        map.put("unit","人");
        List<String> diss = new ArrayList<>();
        diss.add("全场8.9折优惠");
        diss.add("早餐免费领豆浆");
        diss.add("上午9:00至12:00有7折优惠");
        map.put("diss", diss);

        list.add(map);

        map = new HashMap<>();
        map.put("name", "驴肉火烧");
        map.put("distance","253m");
        map.put("point",4.3);
        map.put("work_time", "9:00~21:30");
        map.put("dis_str","全场");
        map.put("dis_num", "8.6折");
        map.put("price", 86);
        map.put("unit","人");
        diss = new ArrayList<>();
        diss.add("全场8.9折优惠");
        diss.add("早餐免费领豆浆");
        diss.add("上午9:00至12:00有7折优惠");
        map.put("diss", diss);

        list.add(map);

        map = new HashMap<>();
        map.put("name", "沙县小吃");
        map.put("distance","253m");
        map.put("point",4.3);
        map.put("work_time", "9:00~21:30");
        map.put("dis_str","全场");
        map.put("dis_num", "8.6折");
        map.put("price", 86);
        map.put("unit","人");
        diss = new ArrayList<>();
        diss.add("全场8.9折优惠");
        diss.add("早餐免费领豆浆");
        diss.add("上午9:00至12:00有7折优惠");
        map.put("diss", diss);

        list.add(map);

        return list;
    }
}