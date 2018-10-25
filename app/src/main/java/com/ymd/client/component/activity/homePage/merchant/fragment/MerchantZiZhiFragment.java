package com.ymd.client.component.activity.homePage.merchant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ymd.client.R;
import com.ymd.client.component.adapter.goods.MerchantZiZhiAdapter;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.HashMap;
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
public class MerchantZiZhiFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mParam1;
    private String mParam2;
    MerchantInfoEntity merchantInfo;

    public MerchantZiZhiFragment() {
        // Required empty public constructor
    }

    public static MerchantZiZhiFragment newInstance(MerchantInfoEntity merchantInfo) {
        MerchantZiZhiFragment fragment = new MerchantZiZhiFragment();
        Bundle args = new Bundle();
        args.putSerializable("merchant", merchantInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            merchantInfo = (MerchantInfoEntity) getArguments().getSerializable("merchant");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_zizhi, container, false);
        unbinder = ButterKnife.bind(this, view);
      /*  setShopData();
        setManageData();*/

        requestMerchantPicture();
        return view;
    }

    private void requestMerchantPicture() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        WebUtil.getInstance().requestPOST(getActivity(), URLConstant.MERCHANT_PHOTO_FILE_LIST, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
               /*         setShopData(result.optString("mentouzhao"));
                        setManageData(result.optString("zizhizhao"));*/
                        setData(result.optString("mentouzhao"), result.optString("zizhizhao"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setData(String shopStr, String manageStr) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MerchantZiZhiAdapter adapter = new MerchantZiZhiAdapter(merchantInfo, manageStr, shopStr, getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
