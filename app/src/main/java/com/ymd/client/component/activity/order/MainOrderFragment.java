package com.ymd.client.component.activity.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.adapter.AppFragmentPageAdapter;
import com.ymd.client.component.widget.other.MyChooseItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者:rongweihe
 * 日期:2018/8/18
 * 描述:    “订单”选项卡
 * 修改历史:
 */
public class MainOrderFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.businessView)
    LinearLayout businessView;
    @BindView(R.id.businessViewPager)
    ViewPager businessViewPager;
    Unbinder unbinder;
    @BindView(R.id.chooseItem0)
    MyChooseItemView chooseItem0;
    @BindView(R.id.chooseItem1)
    MyChooseItemView chooseItem1;
    @BindView(R.id.chooseItem2)
    MyChooseItemView chooseItem2;


    private List<Fragment> fragmentList;

    private List<MyChooseItemView> textViewList;

    public int chooseStatus = 0;
    protected int status;

//    private OnFragmentInteractionListener mListener;

    public MainOrderFragment() {
        // Required empty public constructor
    }

    public static MainOrderFragment newInstance(String param1, String param2) {
        MainOrderFragment fragment = new MainOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.iv_back).setVisibility(View.GONE);
        txtTitle.setText("订单");
        status = 3;
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new OrderPageFragment());
        fragmentList.add(new OrderPageFragment());
        fragmentList.add(new OrderPageFragment());

        textViewList=new ArrayList<MyChooseItemView>();
        textViewList.add(chooseItem0);
        textViewList.add(chooseItem1);
        textViewList.add(chooseItem2);
        viewPagerListener();
        chooseItem(0);
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

    protected void viewPagerListener() {
        for (int i = 0 ; i < textViewList.size() ; i ++ ) {
            final int position = i;
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    businessViewPager.setCurrentItem(position);
                    chooseItem(position);
                }
            });
        }
        businessViewPager.setAdapter(new AppFragmentPageAdapter(getChildFragmentManager(),fragmentList));
        businessViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
/*

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
