package com.ymd.client.component.activity.homePage.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.other.PinnedSectionListView;
import com.ymd.client.component.widget.other.SideBar;
import com.ymd.client.model.bean.city.CityChooseObject;
import com.ymd.client.model.bean.city.CityEntity;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择城市
 */
public class CityChooseActivity extends BaseActivity {

    private PinnedSectionListView listView;
    private SideBar sideBar;
    private TextView dialogView;

    private ArrayList<CityChooseObject> citys = new ArrayList<CityChooseObject>();

    private List<String> cityUseLetters = new ArrayList<String>();

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, CityChooseActivity.class);
        context.startActivityForResult(intent, 1);
    }
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.city_choose_activity);
        bindWidgetId();
        bindWidgetListener();
    }

    protected void bindWidgetId() {
        listView = (PinnedSectionListView) findViewById(R.id.listView);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialogView = (TextView) findViewById(R.id.dialog);

    }

    protected void bindWidgetListener() {
        setTitle("城市选择");

        sideBar.setTextView(dialogView);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                try {
                    int position = CityChooseObject.letters.get(cityUseLetters.indexOf(s));
                    if(position != -1){
                        listView.setSelection(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //	ToolUtil.ToastMessage("连接失败",ToolUtil.WRONG);
                //    ToastUtil.ToastMessage(CityChooseActivity.this, "连接失败");
                }
            }
        });

        findViewById(R.id.searchLayout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
             //   startActivityForResult(new Intent(CityChooseActivity.this,CitySearchQueryActivity.class), 1);
            }
        });
        receiveData();
        setData();
    }

    @Override
    protected void receiveData() {
        ((TextView)findViewById(R.id.city)).setText(LocationInfo.getInstance().getLocationInfo().getCity());
    }

    @Override
    public void onStart() {
        super.onStart();
        //	setAllData();
    //    queryData();
    }

    private void queryData() {

       /* WebServiceUtil.callWebService(this,
                Services.SHOP_SERVICE,
                ShopService.QUERY_CITYS,
                new WebServiceUtil.WebServiceCallBack() {

                    @Override
                    public void callBack(String result) {
                        Message msg = Message.obtain(successHandler);
                        String content = StringUtil.getContent(result);
                        msg.obj = content;
                        successHandler.sendMessage(msg);
                    }
                });*/
    }

    private Handler successHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String resultStr = ToolUtil.changeString(msg.obj);
            setData();
        }
    };

    @SuppressWarnings("unchecked")
    private void setData(){
        List<CityEntity> citys = /*new Gson().fromJson(data, new TypeToken<List<CityEntity>>(){}.getType());*/
                LocationInfo.getInstance().getAllCitys();
        List<CityChooseObject> citysList = new ArrayList<>();

        cityUseLetters.clear();
        int k = 0;
        for (int i = 0 ;i < citys.size(); i++) {
            CityEntity item = citys.get(i);
            if (i == 0) {
                CityChooseObject.letters.add(k);
                cityUseLetters.add(item.getCityFirst());
                CityChooseObject i1 = new CityChooseObject(CityChooseObject.SECTION, item.getCityFirst());
                citysList.add(i1);
                k ++;
                CityChooseObject i2 = new CityChooseObject(CityChooseObject.ITEM, item.getCityName(),
                        item.getCityID(),item.getCityFirst());
                citysList.add(i2);
                k++;
            } else {
                int j = i - 1;
                CityEntity lastItem = citys.get(j);
                if (item.getCityFirst().equals(lastItem.getCityFirst())) {
                    CityChooseObject i2 = new CityChooseObject(CityChooseObject.ITEM, item.getCityName(),
                            item.getCityID(),item.getCityFirst());
                    citysList.add(i2);
                    k ++;
                } else {
                    CityChooseObject.letters.add(k);
                    cityUseLetters.add(item.getCityFirst());
                    CityChooseObject i1 = new CityChooseObject(CityChooseObject.SECTION, item.getCityFirst());
                    citysList.add(i1);
                    k++;
                    CityChooseObject i2 = new CityChooseObject(CityChooseObject.ITEM, item.getCityName(),
                            item.getCityID(),item.getCityFirst());
                    citysList.add(i2);
                    k++;
                }
            }
        }

        LogUtil.showD(cityUseLetters.toString());

        sideBar.setLetters(cityUseLetters);
        CityListAdapter adapter = new CityListAdapter(this, citysList);
        adapter.setOnItemClick(new CityListAdapter.OnItemClick() {

            @Override
            public void onItemClick(CityChooseObject bean) {
                if (bean.getType() == CityChooseObject.ITEM) {
                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setCityFirst(bean.getCityFirst());
                    cityEntity.setCityID(bean.getCityId());
                    cityEntity.setCityName(bean.getCityName());
                    LocationInfo.getInstance().setChooseCity(cityEntity);
                    finish();
                }
            }
        });
        listView.setAdapter(adapter);
        listView.setCacheColorHint(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                CityChooseObject bean = citysList.get(position);
                if (bean.getType() == CityChooseObject.ITEM) {
                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setCityFirst(bean.getCityFirst());
                    cityEntity.setCityID(bean.getCityId());
                    cityEntity.setCityName(bean.getCityName());
                    LocationInfo.getInstance().setChooseCity(cityEntity);
                    CountyChooseActivity.startAction(CityChooseActivity.this, cityEntity.getCityID());
                    finish();
                }
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