package com.ymd.client.component.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.component.activity.login.LoginByPWActivity;
import com.ymd.client.component.activity.mine.collection.MyCollectionActivity;
import com.ymd.client.component.activity.mine.info.PersonInfoActivity;
import com.ymd.client.component.activity.mine.setting.SettingActivity;
import com.ymd.client.component.activity.mine.ub.MyUbActivity;
import com.ymd.client.component.event.CityShowEvent;
import com.ymd.client.component.event.LoginEvent;
import com.ymd.client.component.event.LoginRefreshEvent;
import com.ymd.client.component.widget.CircleImageView;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.utils.helper.PermissionHelper;
import com.ymd.client.utils.helper.PermissionInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者:rongweihe
 * 日期:2018/8/18
 * 描述:    “我的”选项卡
 * 修改历史:
 */
public class MainMineFragment extends Fragment implements PermissionInterface {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
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
        unbinder = ButterKnife.bind(this, mView);


        EventBus.getDefault().register(this);
        mPermissionHelper = new PermissionHelper(getActivity(), this);
        mPermissionHelper.requestPermissions();
        refreshInfo();
        return mView;
    }

    private void refreshInfo() {
        if (LoginInfo.isLogin) {
            if (!TextUtils.isEmpty(LoginInfo.getInstance().getLoginInfo().getIcon())) {
                Glide.with(getActivity()).load(LoginInfo.getInstance().getLoginInfo().getIcon()).into(mHeadView);
            } else {
                mHeadView.setImageResource(R.mipmap.ic_launcher);
            }
            mNickName.setText(LoginInfo.getInstance().getLoginInfo().getUserName());
            mMyUbCount.setText(ToolUtil.changeString(LoginInfo.getInstance().getLoginInfo().getuNumber()));
        } else {
            mHeadView.setImageResource(R.mipmap.ic_launcher);
            mNickName.setText("未登录");
            mMyUbCount.setText("");
        }

    }

    @OnClick({R.id.fragment_mine_my_ub, R.id.fragment_person_nickname, R.id.fragment_mine_my_collection, R.id.fragment_mine_my_rate,
            R.id.fragment_mine_my_cards, R.id.fragment_mine_links, R.id.fragment_mine_banks,
            R.id.fragment_mine_introduce, R.id.fragment_mine_cooperation, R.id.fragment_setting,
            R.id.fragment_person, R.id.fragment_person_iv})
    void click(View view) {
        switch (view.getId()) {
            case R.id.fragment_setting:
                //设置
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.fragment_person_nickname:
            case R.id.fragment_person_iv:
                //个人信息
            case R.id.fragment_person:
                //个人信息
                if (!LoginInfo.isLogin) {
                    LoginByPWActivity.startAction(getActivity());
                } else {
                    PersonInfoActivity.startAction(getActivity());
                }
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
                //    startActivity(new Intent(getContext(), LinkServiceActivity.class));
                diallPhone("40012012345");
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

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        if (event.isSuccess()) {
            refreshInfo();
        } else {
            mHeadView.setImageResource(R.mipmap.ic_launcher);
            mNickName.setText("未登录");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginRefreshEvent event) {
        if (event.isRefresh()) {
            refreshInfo();
        }
    }

    private PermissionHelper mPermissionHelper;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getPermissionsRequestCode() {        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return 10000;
    }

    @Override
    public String[] getPermissions() {        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE};
    }

    @Override
    public void requestPermissionsSuccess() {        //权限请求用户已经全部允许
        initViews();
    }

    @Override
    public void requestPermissionsFail() {        //权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。
        ToastUtil.ToastMessage(getActivity(), "打电话权限未允许");
    }

    private void initViews() {        //已经拥有所需权限，可以放心操作任何东西了


    }
}
