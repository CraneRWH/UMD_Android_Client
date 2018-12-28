package com.ymd.client.component.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.model.bean.IntroBean;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-推荐有礼
 */
public class IntroduceActivity extends BaseActivity {
    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_intro));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
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

    @OnClick(R.id.introduce_share)
    void share(View view) {
        WebUtil.getInstance().requestGET(this, URLConstant.GET_INTRODUCE_CONTENT, null, true, false,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        IntroBean introBean = new Gson().fromJson(resultJson.toString(), IntroBean.class);
                        shareDialog(introBean);
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    void shareDialog(IntroBean introBean) {
        if (introBean == null) {
            return;
        }
        UMImage thumb = new UMImage(this, introBean.getImg());
        UMWeb web = new UMWeb(introBean.getUrl());
        web.setTitle(introBean.getTitle());//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(introBean.getContent());//描述

        new ShareAction(IntroduceActivity.this)
                .withMedia(web)
                .withSubject(introBean.getTitle())
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)//, SHARE_MEDIA.SINA)
                .setCallback(umShareListener).open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(IntroduceActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(IntroduceActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(IntroduceActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
