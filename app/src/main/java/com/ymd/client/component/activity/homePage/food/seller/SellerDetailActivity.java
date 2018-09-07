package com.ymd.client.component.activity.homePage.food.seller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.homePage.food.seller.fragment.ChooseDishesFragment;
import com.ymd.client.component.activity.homePage.food.seller.fragment.EvaluateSellerFragment;
import com.ymd.client.component.activity.homePage.food.seller.fragment.SellerDetailFragment;
import com.ymd.client.component.adapter.AppFragmentPageAdapter;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.component.widget.viewPager.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/28
 * 描述:    商家页面
 * 修改历史:
 */
public class SellerDetailActivity extends BaseActivity {

    @BindView(R.id.phone_iv)
    ImageView phoneIv;
    @BindView(R.id.collection_iv)
    ImageView collectionIv;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    @BindView(R.id.icon_iv)
    ImageView iconIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.scoreBarView)
    RatingBar scoreBarView;
    @BindView(R.id.work_time_tv)
    TextView workTimeTv;
    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.chooseItem0)
    MyChooseItemView chooseItem0;
    @BindView(R.id.chooseItem1)
    MyChooseItemView chooseItem1;
    @BindView(R.id.chooseItem2)
    MyChooseItemView chooseItem2;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
/*
    @BindView(R.id.main_sv)
    ScrollView mainSv;*/

    private List<Fragment> fragmentList;

    private List<MyChooseItemView> textViewList;

    public int chooseStatus = 0;
    protected int status;

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, NewTabActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        scoreBarView.setRating(4);

        chooseItem1.setText("点评（3）");
        status = 3;
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new ChooseDishesFragment());
        fragmentList.add(new EvaluateSellerFragment());
        fragmentList.add(new SellerDetailFragment());

        textViewList=new ArrayList<MyChooseItemView>();
        textViewList.add(chooseItem0);
        textViewList.add(chooseItem1);
        textViewList.add(chooseItem2);
        viewPagerListener();
        chooseItem(0);

    }
    protected void viewPagerListener() {
        for (int i = 0 ; i < textViewList.size() ; i ++ ) {
            final int position = i;
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    viewPager.setCurrentItem(position);
                    chooseItem(position);
                }
            });
        }
        viewPager.setAdapter(new AppFragmentPageAdapter(getSupportFragmentManager(),fragmentList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Do Nothing
            }

            @Override
            public void onPageSelected(int position) {
                chooseItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Do Nothing
            }
        });
    }

    protected void chooseItem(int position) {
        chooseStatus = position;
/*        Bundle bundle = new Bundle();
        bundle.putInt("chooseStatus", chooseStatus);
        fragmentList.get(chooseStatus).setArguments(bundle);*/
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
}
