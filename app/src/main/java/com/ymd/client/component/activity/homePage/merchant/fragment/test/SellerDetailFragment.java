package com.ymd.client.component.activity.homePage.merchant.fragment.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.component.activity.homePage.merchant.ComplaintSellerActivity;
import com.ymd.client.component.activity.homePage.merchant.fragment.BaseFragment;
import com.ymd.client.component.adapter.merchant.PersonAdapter;
import com.ymd.client.component.event.GoodsListEvent;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

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
 * 日期:2018/8/28
 * 描述:    商家页面
 * 修改历史:
 */
public class SellerDetailFragment extends BaseFragment implements PersonAdapter.OnShopCartGoodsChangeListener, OnHeaderClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.shopLayout)
    LinearLayout shopLayout;
    @BindView(R.id.manageLayout)
    LinearLayout manageLayout;
    @BindView(R.id.shopSLayout)
    LinearLayout shopSLayout;
    @BindView(R.id.shap_work_time_tv)
    TextView shapWorkTimeTv;
    @BindView(R.id.complaint_btn)
    Button complaintBtn;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    MerchantInfoEntity merchantInfo;

    public SellerDetailFragment() {
        // Required empty public constructor
    }

    public static SellerDetailFragment newInstance(MerchantInfoEntity merchantInfo) {
        SellerDetailFragment fragment = new SellerDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("merchant", merchantInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null) {
            merchantInfo = (MerchantInfoEntity) getArguments().getSerializable("merchant");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
      /*  setShopData();
        setManageData();*/

        requestMerchantPicture();
        setServiceData();
        complaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplaintSellerActivity.startAction(getActivity(),merchantInfo);
            }
        });
        return view;
    }

    private void requestMerchantPicture() {
        Map<String,Object> params = new HashMap<>();
        params.put("merchantId",merchantInfo.getId());
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.MERCHANT_PHOTO_FILE_LIST, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        setShopData(result.optString("mentouzhao"));
                        setManageData(result.optString("zizhizhao"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setShopData(String resultJson) {
        List<String> list = new Gson().fromJson(resultJson, new TypeToken<List<String>>(){}.getType());


        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.item_shop_picture , shopLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.item_iv);
            //实例化TextView控件
            //   TextView tv= (TextView) view.findViewById(R.id.textView);
            //将int数组中的数据放到ImageView中
            Glide.with(getActivity()).load(ToolUtil.changeString(list.get(i))).into(img);
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            shopLayout.addView(view);
        }
    }

    private void setManageData(String resultJson) {
        List<String> list = new Gson().fromJson(resultJson, new TypeToken<List<String>>(){}.getType());

        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.item_shop_picture , manageLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.item_iv);
            //实例化TextView控件
            //   TextView tv= (TextView) view.findViewById(R.id.textView);
            //将int数组中的数据放到ImageView中
            Glide.with(getActivity()).load(ToolUtil.changeString(list.get(i))).into(img);
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            manageLayout.addView(view);
        }
    }
    /**
     * 添加 或者  删除  商品发送的消息处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {
        if(event.buyNums.length>0){
           /* for (int i=0;i<event.buyNums.length;i++){
                goodscatrgoryEntities.get(i).setBugNum(event.buyNums[i]);
            }
            mGoodsCategoryListAdapter.changeData(goodscatrgoryEntities);*/
        }

    }
    private void setServiceData() {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","商家服务");
        map.put("icon", R.mipmap.icon_merchant_serve);
        list.add(map);

        if (merchantInfo.getInvoice() == 1) {
            map = new HashMap<>();
            map.put("name", "提供发票");
            map.put("icon", R.mipmap.icon_merchant_ticket);
            list.add(map);
        }

        map = new HashMap<>();
        map.put("name","到店自取");
        map.put("icon", R.mipmap.icon_merchant_fetch);
        list.add(map);

        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(getActivity()).inflate(R.layout.item_seller_shop_service , shopSLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.icon_iv);
            //实例化TextView控件
            TextView tv= (TextView) view.findViewById(R.id.item_tv);
            //将int数组中的数据放到ImageView中
            img.setImageResource(ToolUtil.changeInteger(list.get(i).get("icon")));
            //给TextView添加文字
            tv.setText(ToolUtil.changeString(list.get(i).get("name")));
            //把行布局放到linear里
            shopSLayout.addView(view);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHeaderClick(View header, long headerId) {

    }

    @Override
    public void onNumChange() {

    }
}
