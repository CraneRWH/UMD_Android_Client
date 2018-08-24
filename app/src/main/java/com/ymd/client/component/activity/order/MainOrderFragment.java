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
import com.ymd.client.common.base.fragment.PageFragment;
import com.ymd.client.component.widget.other.MyChooseItemView;

import java.util.ArrayList;

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

    private String mParam1;
    private String mParam2;
    protected MyChooseItemView[] chooseItemViews;
    protected ArrayList<Fragment> pageFragments = new ArrayList<Fragment>();

    public int chooseStatus = 0;
    protected int status;

    private OnFragmentInteractionListener mListener;

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
        View view = inflater.inflate(R.layout.fragment_main_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        txtTitle.setText("订单");
        status = 3;
        chooseItemViews = new MyChooseItemView[status];
        for (int i = 0 ; i < status ; i ++ ) {
            chooseItemViews[i] = (MyChooseItemView)view.findViewById(
                    getResources().getIdentifier("chooseItem" + i, "id", "com.ymd.client")
            );
        }

        for (int i = 0 ; i < chooseItemViews.length ; i ++ ) {
            final int position = i;
            chooseItemViews[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    businessViewPager.setCurrentItem(position);
                    chooseItem(position);
                }
            });
        }


        try {
            chooseItemViews = new MyChooseItemView[4];
            for (int i = 0 ; i < status ; i ++ ) {
                OrderPageFragment fragment = new OrderPageFragment();
            /*    fragment.setFragmentPageWarnNumListener(new MyServicePageFragment.FragmentPageWarnNumListener() {

                    @Override
                    public void setPageWarn(int position, Object num) {
                        chooseItemViews[position].setWarnNum(num);
                    }

                    @Override
                    public void setAllPageWarn(String data) {
                        setCardNum(data);
                    }
                });*/
                pageFragments.add(fragment);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        chooseItem(0);
        viewPagerListener();
    }

    protected void chooseItem(int position) {
        chooseStatus = position;
		/*Bundle bundle = new Bundle();
		bundle.putInt("chooseStatus", chooseStatus);
		pageFragments.get(chooseStatus).setArguments(bundle);*/
        try {
            for (int i = 0 ; i < chooseItemViews.length ; i ++ ) {
                if ( i == position) {
                    chooseItemViews[i].setChoose(true);
                }
                else {
                    chooseItemViews[i].setChoose(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void viewPagerListener() {

        businessViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("chooseStatus", position);
                pageFragments.get(position).setArguments(bundle);
                return pageFragments.get(position);
            }

            @Override
            public int getCount() {
                return pageFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "TAB" + position;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });
        businessViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                chooseItem(position);
                ((PageFragment)pageFragments.get(position)).queryDataStart();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
