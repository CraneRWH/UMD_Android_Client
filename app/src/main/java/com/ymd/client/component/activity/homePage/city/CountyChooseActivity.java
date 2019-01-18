package com.ymd.client.component.activity.homePage.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.model.bean.city.CityChooseObject;
import com.ymd.client.model.bean.city.CityEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择县级市
 */
public class CountyChooseActivity extends BaseActivity {

    private RecyclerView countyRv;

    private ArrayList<CityChooseObject> citys = new ArrayList<CityChooseObject>();

    private List<String> cityUseLetters = new ArrayList<String>();

    private long cityCode;
    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context,long cityCode) {
        Intent intent = new Intent(context, CountyChooseActivity.class);
        intent.putExtra("cityCode", cityCode);
        context.startActivityForResult(intent, 1);
    }
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.county_choose_activity);
        bindWidgetId();
        bindWidgetListener();
    }

    protected void bindWidgetId() {
        countyRv = (RecyclerView) findViewById(R.id.county_rv);
        setStatusBar(R.color.bg_header);
    }

    protected void bindWidgetListener() {
        setTitle("城市选择");
        cityCode = getIntent().getExtras().getLong("cityCode");
        queryData();
    }

    private void queryData() {

        Map<String,Object> params = new HashMap<>();
        params.put("cityID", cityCode);
        WebUtil.getInstance().requestPOST(this, URLConstant.QUERY_COUTY_DATA, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        setData(resultJson.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setData(String resultJson){
        List<CityEntity> citys = new Gson().fromJson(resultJson, new TypeToken<List<CityEntity>>(){}.getType());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        countyRv.setLayoutManager(layoutManager);
        CountyListAdapter adapter = new CountyListAdapter(citys);
        countyRv.setAdapter(adapter);
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                CityEntity cityEntity = LocationInfo.getInstance().getChooseCity();
                CityEntity item = (CityEntity) data;
                cityEntity.setCountyCode(item.getCityID());
                cityEntity.setCountyName(item.getCityName());
                LocationInfo.getInstance().setChooseCity(cityEntity);
                finish();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            finish();
        }
    }

}