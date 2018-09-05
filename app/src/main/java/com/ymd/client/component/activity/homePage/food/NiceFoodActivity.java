package com.ymd.client.component.activity.homePage.food;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.bean.TabObject;
import com.ymd.client.component.activity.order.PageFragmentAdapter;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.model.bean.homePage.YmdRecommendEntity;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/25
 * 描述:    美事
 * 修改历史:
 */
public class NiceFoodActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    @BindView(R.id.more_tv)
    TextView moreTv;
    @BindView(R.id.recommendLayout)
    LinearLayout recommendLayout;
    @BindView(R.id.chooseItem0)
    MyChooseItemView chooseItem0;
    @BindView(R.id.chooseItem1)
    MyChooseItemView chooseItem1;
    @BindView(R.id.chooseItem2)
    MyChooseItemView chooseItem2;
    @BindView(R.id.chooseItem3)
    MyChooseItemView chooseItem3;
    @BindView(R.id.businessViewPager)
    ViewPager viewPager;
    @BindView(R.id.rgChannel)
    RadioGroup rgChannel;
    @BindView(R.id.hvChannel)
    HorizontalScrollView hvChannel;

    private PageFragmentAdapter adapter=null;
    private List<Fragment> fragmentList=new ArrayList<Fragment>();

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, NiceFoodActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_food);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("美食");

        rgChannel.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        viewPager.setCurrentItem(checkedId);
                    }
                });
        viewPager.setOnPageChangeListener(this);
        initTab();//动态产生RadioButton
        initViewPager();
        rgChannel.check(0);

        setTab(0);
        requestRecommend();
    }

    private void requestRecommend() {
        Map<String,Object> params = new HashMap<>();
        params.put("countyId","130406");
        WebUtil.getInstance().requestPOST(this, URLConstant.RECOMMEND_NICE_MERCHANT, params,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        setYouHuiItem(result.optString("list"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void setYouHuiItem(String resultJson) {
        List<YmdRecommendEntity> list = new Gson().fromJson(resultJson, new TypeToken<List<YmdRecommendEntity>>(){}.getType());
        /*List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "food_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "hospital_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "car_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);

        map = new HashMap<>();
        map.put("name", "meirong_item_icon");
        map.put("icon", R.mipmap.nice_good_icon1);
        list.add(map);
*/
        //开始添加数据
        for (int x = 0; x < list.size(); x++) {
            YmdRecommendEntity item = list.get(x);
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(this).inflate(R.layout.item_nice_food_recommend, recommendLayout, false);

            ImageView icon_iv;
            TextView name_tv;
            TextView desc_tv;
            TextView now_price_tv;
            TextView buy_btn;
            TextView old_price_tv;
            icon_iv = (ImageView) view.findViewById(R.id.icon_iv);
            name_tv = (TextView) view.findViewById(R.id.name_tv);
            desc_tv = (TextView) view.findViewById(R.id.desc_tv);
            now_price_tv = (TextView) view.findViewById(R.id.now_price_tv);
            buy_btn = (TextView) view.findViewById(R.id.buy_btn);
            old_price_tv = (TextView) view.findViewById(R.id.old_price_tv);
            old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            name_tv.setText(ToolUtil.changeString(item.getGoodsName()));
            desc_tv.setText(ToolUtil.changeString(item.getMerchantName()));
        //    now_price_tv.setText(ToolUtil.changeString(item.get));
            //将int数组中的数据放到ImageView中
        //    icon_iv.setImageResource(ToolUtil.changeInteger(datas.get(x).get("icon")));
            Glide.with(this).load(item.getPhoto()).into(icon_iv);
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            recommendLayout.addView(view);
        }
    }

    private void initTab(){
        List<TabObject> channelList=new ArrayList<>();
        channelList.add(new TabObject("全部"));
        channelList.add(new TabObject("快捷便当"));
        channelList.add(new TabObject("汉堡薯条"));
        channelList.add(new TabObject("意大利面"));
        channelList.add(new TabObject("包子粥店"));
        channelList.add(new TabObject("烧饼店"));
        channelList.add(new TabObject("驴肉火烧"));
        channelList.add(new TabObject("麻辣烫"));
        channelList.add(new TabObject("半天妖"));
        for(int i=0;i<channelList.size();i++){
            RadioButton rb=(RadioButton)LayoutInflater.from(this).
                    inflate(R.layout.tab_rb, null);
            rb.setId(i);
            rb.setText(channelList.get(i).getName());
            RadioGroup.LayoutParams params=new
                    RadioGroup.LayoutParams((int) getResources().getDimension(R.dimen.mar_pad_len_200px),
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            rgChannel.addView(rb,params);
        }

    }

    private void initViewPager(){
        List<TabObject> channelList=new ArrayList<>();
        channelList.add(new TabObject("全部"));
        channelList.add(new TabObject("快捷便当"));
        channelList.add(new TabObject("汉堡薯条"));
        channelList.add(new TabObject("意大利面"));
        channelList.add(new TabObject("包子粥店"));
        channelList.add(new TabObject("烧饼店"));
        channelList.add(new TabObject("驴肉火烧"));
        channelList.add(new TabObject("麻辣烫"));
        channelList.add(new TabObject("半天妖"));
        for(int i=0;i<channelList.size();i++){
            FoodListFragment frag= FoodListFragment.newInstance(i);
      /*      Bundle bundle=new Bundle();
            bundle.putString("weburl", channelList.get(i).getWeburl());
            bundle.putString("name", channelList.get(i).getName());
            frag.setArguments(bundle);  */   //向Fragment传入数据
            fragmentList.add(frag);
        }
        adapter=new PageFragmentAdapter(super.getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(0);
    }

    /**
     * 滑动ViewPager时调整ScroollView的位置以便显示按钮
     * @param idx
     */
    private void setTab(int idx){

        for(int i = 0 ; i < rgChannel.getChildCount(); i ++) {
            RadioButton item=(RadioButton)rgChannel.getChildAt(i);

            item.setTextColor(getResources().getColor(R.color.text_gray_dark));
        }
        RadioButton rb=(RadioButton)rgChannel.getChildAt(idx);
        rb.setTextColor(getResources().getColor(R.color.bg_header));
        rb.setChecked(true);
        int left=rb.getLeft();
        int width=rb.getMeasuredWidth();
        DisplayMetrics metrics=new DisplayMetrics();
        super.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth=metrics.widthPixels;
        int len=left+width/2-screenWidth/2;
        hvChannel.smoothScrollTo(len, 0);//滑动ScroollView
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
