package com.ymd.client.component.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.component.activity.mine.collection.MyCollectionActivity;
import com.ymd.client.component.activity.mine.info.PersonInfoActivity;
import com.ymd.client.component.activity.mine.setting.SettingActivity;
import com.ymd.client.component.activity.mine.ub.MyUbActivity;
import com.ymd.client.component.widget.CircleImageView;
import com.ymd.client.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:rongweihe
 * 日期:2018/8/18
 * 描述:    “我的”选项卡
 * 修改历史:
 */
public class MainMineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View mView;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.fragment_person_iv)
    CircleImageView mHeadView;//头像
    @BindView(R.id.fragment_person_nickname)
    TextView mNickName;//昵称
    @BindView(R.id.fragment_my_ub)
    TextView mMyUbCount;//我的U币

    public MainMineFragment() {
        // Required empty public constructor
    }

    public static MainMineFragment newInstance(String param1, String param2) {
        MainMineFragment fragment = new MainMineFragment();
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
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        } else {
            mView = inflater.inflate(R.layout.fragment_main_mine, container, false);
        }
        ButterKnife.bind(this, mView);
        return mView;
    }

    @OnClick({R.id.fragment_mine_my_ub, R.id.fragment_mine_my_collection, R.id.fragment_mine_my_rate,
            R.id.fragment_mine_my_cards, R.id.fragment_mine_links, R.id.fragment_mine_banks,
            R.id.fragment_mine_introduce, R.id.fragment_mine_cooperation, R.id.fragment_setting,
            R.id.fragment_person, R.id.fragment_person_iv})
    void click(View view) {
        switch (view.getId()) {
            case R.id.fragment_setting:
                //设置
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.fragment_person_iv:
                //个人信息
            case R.id.fragment_person:
                //个人信息
                PersonInfoActivity.startAction(getActivity());
                break;
            case R.id.fragment_mine_my_ub:
                //我的U币
                startActivity(new Intent(getContext(), MyUbActivity.class));
                break;
            case R.id.fragment_mine_my_collection:
                startActivity(new Intent(getContext(), MyCollectionActivity.class));
                //我的收藏
                break;
            case R.id.fragment_mine_my_rate:
                startActivity(new Intent(getContext(), MyRatesActivity.class));
                //我的评价
                break;
            case R.id.fragment_mine_my_cards:
            //    startActivity(new Intent(getContext(), MyCardsActivity.class));
                ToastUtil.ToastMessage(getActivity(), "功能开发中，敬请期待");
                //我的券包
                break;
            case R.id.fragment_mine_links:
                startActivity(new Intent(getContext(), LinkServiceActivity.class));
                //联系客服
                break;
            case R.id.fragment_mine_banks:
            //    startActivity(new Intent(getContext(), MyBanksActivity.class));

                ToastUtil.ToastMessage(getActivity(), "功能开发中，敬请期待");
                //我的银行卡
                break;
            case R.id.fragment_mine_introduce:
             //   startActivity(new Intent(getContext(), IntroduceActivity.class));

                ToastUtil.ToastMessage(getActivity(), "功能开发中，敬请期待");
                //推荐有礼
                break;
            case R.id.fragment_mine_cooperation:
            //    startActivity(new Intent(getContext(), CooperationActivity.class));

                ToastUtil.ToastMessage(getActivity(), "功能开发中，敬请期待");
                //我要合作
                break;

        }
    }
}
