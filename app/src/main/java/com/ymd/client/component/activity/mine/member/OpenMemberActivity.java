package com.ymd.client.component.activity.mine.member;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.login.LoginByPWActivity;
import com.ymd.client.component.adapter.CommonRecyclerAdapter;
import com.ymd.client.component.adapter.OpenMemberAdapter;
import com.ymd.client.component.widget.zrecyclerview.ZRecyclerView;
import com.ymd.client.model.bean.MemberCardBean;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.AbDateUtil;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ScreenUtil;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 开通会员页面
 */
public class OpenMemberActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;//标题

    @BindView(R.id.open_member_money)
    TextView mOpenMoney;//支付金额
    @BindView(R.id.open_member_validity_time)
    TextView mOpenValidity;//有效期

    @BindView(R.id.open_member_submit)
    TextView mTxtSubmit;//开通会员，续费

    @BindView(R.id.open_member_view)
    ZRecyclerView recyclerView;
    @BindView(R.id.open_member_emptyLayout)
    View mEmptyView;

    @BindView(R.id.open_member_iv)
    ImageView mIvTop;
    @BindView(R.id.open_member_iv1)
    ImageView mIvTop1;

    OpenMemberAdapter mAdapter;
    int page = 1;

    List<MemberCardBean> datas;

    int w, h, w1, h1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_member);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_open_member));

        initImageView();

        initMemberView();

        initList();
    }

    private void initImageView() {
        w = ScreenUtil.getScreenWidthPix(this);
        h = (int) (w / 2.3);

        mIvTop.setLayoutParams(new RelativeLayout.LayoutParams(w, h));
        mIvTop.setScaleType(ImageView.ScaleType.FIT_XY);

        w1 = ScreenUtil.getScreenWidthPix(this) - ScreenUtil.dp2px(this, 80);
        h1 = (int) (w1 / 1.6);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w1, h1);
        params.leftMargin = ScreenUtil.dip2px(this, 40);
        params.rightMargin = ScreenUtil.dip2px(this, 40);
        params.topMargin = (int) (h / 2) + ScreenUtil.dp2px(this, 8);
        params.bottomMargin = 0;

        mIvTop1.setLayoutParams(params);
        mIvTop1.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private void initList() {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.default_recyclerview_divider));
        recyclerView.addItemDecoration(divider);

        mAdapter = new OpenMemberAdapter(this, null);
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MemberCardBean checked = datas.get(position - 1);
                mAdapter.refresh(checked);
                setCheckType(checked);
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setPullRefreshEnabled(false);

        getMemberList();
    }

    void setCheckType(MemberCardBean memberCardBean) {
        mOpenMoney.setText("￥" + memberCardBean.getDiscountPrice());
        mTxtSubmit.setTag(memberCardBean);
        //判断是否为续费
        if (LoginInfo.getInstance().getLoginInfo().getMembership() == 0) {
            //0是非会员
            if ("10A".equals(memberCardBean.getCardType())) {
                mOpenValidity.setText("有效期：" + AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getCurrentDateByOffset(AbDateUtil.dateFormatYMD1, Calendar.DATE, 30));
            } else if ("10B".equals(memberCardBean.getCardType())) {
                mOpenValidity.setText("有效期：" + AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getCurrentDateByOffset(AbDateUtil.dateFormatYMD1, Calendar.DATE, 90));
            } else if ("10C".equals(memberCardBean.getCardType())) {
                mOpenValidity.setText("有效期：" + AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getCurrentDateByOffset(AbDateUtil.dateFormatYMD1, Calendar.DATE, 365));
            } else {
                mOpenValidity.setText("有效期：未知");
            }
        } else {
            //1会员

            if ("10A".equals(memberCardBean.getCardType())) {
                mOpenValidity.setText("有效期：" + AbDateUtil.getStringByFormat(LoginInfo.getInstance().getLoginInfo().getEndDate(), AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getStringByFormat(AbDateUtil.getStringByOffset(LoginInfo.getInstance().getLoginInfo().getEndDate(), AbDateUtil.dateFormatYMDHMS, Calendar.DATE, 30), AbDateUtil.dateFormatYMD1));
            } else if ("10B".equals(memberCardBean.getCardType())) {
                mOpenValidity.setText("有效期：" + AbDateUtil.getStringByFormat(LoginInfo.getInstance().getLoginInfo().getEndDate(), AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getStringByFormat(AbDateUtil.getStringByOffset(LoginInfo.getInstance().getLoginInfo().getEndDate(), AbDateUtil.dateFormatYMDHMS, Calendar.DATE, 90), AbDateUtil.dateFormatYMD1));
            } else if ("10C".equals(memberCardBean.getCardType())) {
                mOpenValidity.setText("有效期：" + AbDateUtil.getStringByFormat(LoginInfo.getInstance().getLoginInfo().getEndDate(), AbDateUtil.dateFormatYMD1) + " - "
                        + AbDateUtil.getStringByFormat(AbDateUtil.getStringByOffset(LoginInfo.getInstance().getLoginInfo().getEndDate(), AbDateUtil.dateFormatYMDHMS, Calendar.DATE, 365), AbDateUtil.dateFormatYMD1));
            } else {
                mOpenValidity.setText("有效期：未知");
            }
        }
    }

    private void getMemberList() {
        if (!LoginInfo.isLogin) {
            ToastUtil.ToastMessage(this, "请首先登录");
            refreshList("");
            LoginByPWActivity.startAction(this);
            return;
        }

        WebUtil.getInstance().requestPOST(this, URLConstant.QUERY_MEMBER_LIST, null, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        refreshList(result.optString("cardList"));
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        //获取失败
                        mEmptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                });
    }

    private void refreshList(String resultJson) {
        try {
            datas = new Gson().fromJson(resultJson, new TypeToken<List<MemberCardBean>>() {
            }.getType());

            if (datas == null || datas.size() == 0) {
                if (page == 1) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    ToastUtil.ToastMessage(this, "没有更多的数据了");
                }
            } else {
                mAdapter.addItems(datas);
                setCheckType(datas.get(0));
                mEmptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMemberView() {
        if (LoginInfo.getInstance().getLoginInfo().getMembership() == 0) {
            //0是非会员
            mTxtSubmit.setText("开通会员");
        } else {
            //1会员
            mTxtSubmit.setText("立即续费");
        }
    }

    @OnClick(R.id.base_back)
    void back() {
        finish();
    }

    @Override
    protected void setStatusBar(int resourcesId) {
        super.setStatusBar(resourcesId);
        StatusBarUtils.StatusBarLightMode(this, true);
    }

    @OnClick(R.id.open_member_submit)
    void submit(View view) {
        //支付
        MemberCardBean memberCardBean = (MemberCardBean) view.getTag();
        if (memberCardBean == null) {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("memId", String.valueOf(memberCardBean.getId()));
        WebUtil.getInstance().requestPOST(this, URLConstant.OPEN_MEMBER, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        LogUtil.e(result.toString());
                        String orderNo = result.optString("orderNo");
                        String money = memberCardBean.getDiscountPrice();
                        PayOpenMemberActivity.startAction(OpenMemberActivity.this, orderNo, money);
                        finish();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {
                        //获取失败
                    }
                });
    }

    @OnClick(R.id.open_member_agreement)
    void clickAgree(View view) {
        ToastUtil.ToastMessage(this, "查看服务协议");
    }
}
