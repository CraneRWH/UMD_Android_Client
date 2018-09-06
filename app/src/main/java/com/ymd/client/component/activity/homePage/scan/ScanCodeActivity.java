package com.ymd.client.component.activity.homePage.scan;

import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.qrcode.ZXingView;
import com.ymd.client.component.widget.qrcode.core.QRCodeView;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanCodeActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.scan_code_zxing)
    ZXingView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText("扫描二维码");

        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
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

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        mQRCodeView.stopCamera();
        //校验扫码框内容是否为8位
        if (!TextUtils.isEmpty(result)) {
            //presenter.vertity(result);
            ToastUtil.ToastMessage(this,result);
        } else {
            ToastUtil.ToastMessage(this,"请扫描正确二维码");
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    /**
     * 重新扫码
     */
    private void reStartToScan() {
        mQRCodeView.startSpotAndShowRect();
        mQRCodeView.startSpot();
    }

    /**
     * 关闭扫码
     */
    private void stopToScan() {
        mQRCodeView.stopSpotAndHiddenRect();
        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
    }
}
