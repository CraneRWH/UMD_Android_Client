package com.ymd.client.component.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ymd.client.R;
import com.ymd.client.UApplication;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.mine.coo.CustomHelper;
import com.ymd.client.component.widget.dialog.CommonDialogs;
import com.ymd.client.component.widget.takephoto.app.TakePhoto;
import com.ymd.client.component.widget.takephoto.app.TakePhotoImpl;
import com.ymd.client.component.widget.takephoto.model.InvokeParam;
import com.ymd.client.component.widget.takephoto.model.TContextWrap;
import com.ymd.client.component.widget.takephoto.model.TResult;
import com.ymd.client.component.widget.takephoto.permission.InvokeListener;
import com.ymd.client.component.widget.takephoto.permission.PermissionManager;
import com.ymd.client.component.widget.takephoto.permission.TakePhotoInvocationHandler;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.PermissionUtils;
import com.ymd.client.utils.ScreenUtil;
import com.ymd.client.utils.StatusBarUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-我要合作
 */
public class CooperationActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    private static final String TAG = CooperationActivity.class.getName();

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.cooperation_choose_license)
    LinearLayout mLayoutLicense;
    @BindView(R.id.cooperation_choose_identify_x)
    LinearLayout mLayoutIdentifyX;
    @BindView(R.id.cooperation_choose_identify_y)
    LinearLayout mLayoutIdentifyY;

    @BindView(R.id.cooperation_choose_hygiene)
    LinearLayout mLayoutHygiene;
    @BindView(R.id.cooperation_choose_other1)
    LinearLayout mLayoutOther1;
    @BindView(R.id.cooperation_choose_other2)
    LinearLayout mLayoutOther2;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CustomHelper customHelper;

    @BindView(R.id.cooperation_choose_license_iv)
    ImageView mIvLicense;
    @BindView(R.id.cooperation_choose_license_tv)
    TextView mTxtLicense;

    @BindView(R.id.cooperation_choose_id_x)
    ImageView mIvIdX;
    @BindView(R.id.cooperation_choose_id_x_tv)
    TextView mTxtIdX;

    @BindView(R.id.cooperation_choose_id_y)
    ImageView mIvIdY;
    @BindView(R.id.cooperation_choose_id_y_tv)
    TextView mTxtIdY;

    @BindView(R.id.cooperation_choose_hygiene_iv)
    ImageView mIvHygiene;
    @BindView(R.id.cooperation_choose_hygiene_tv)
    TextView mTxtHygiene;

    @BindView(R.id.cooperation_choose_other1_iv)
    ImageView mIvOther1;
    @BindView(R.id.cooperation_choose_other1_tv)
    TextView mTxtOther1;

    @BindView(R.id.cooperation_choose_other2_iv)
    ImageView mIvOther2;
    @BindView(R.id.cooperation_choose_other2_tv)
    TextView mTxtOther2;

    private int tempCode = 0;//拍照请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_coop));
        customHelper = CustomHelper.of();
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void init() {
        int width = (ScreenUtil.getScreenWidthPix(this) - ScreenUtil.dip2px(this, 27)) / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        mLayoutIdentifyY.setLayoutParams(params);

        mLayoutOther2.setLayoutParams(params);

        params.rightMargin = ScreenUtil.dip2px(this, 4);
        mLayoutLicense.setLayoutParams(params);
        mLayoutIdentifyX.setLayoutParams(params);

        mLayoutHygiene.setLayoutParams(params);
        mLayoutOther1.setLayoutParams(params);

        /**
         * 先创建文件夹，默认开启权限了
         */
        initDir();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_CODE_CAMERA:
                if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
                    // 权限申请成功
                    customHelper.onClick(takePhoto);
                } else {
                    tempCode = 0;
                    showReqPermissionsDialog();
                }
                break;
            default:
                PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
                PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
                break;
        }
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
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

    @OnClick(R.id.cooperation_choose_type)
    void chooseType() {
        //选择商家类型

    }

    @OnClick({R.id.cooperation_choose_license, R.id.cooperation_choose_identify_x, R.id.cooperation_choose_identify_y,
            R.id.cooperation_choose_hygiene, R.id.cooperation_choose_other1, R.id.cooperation_choose_other2})
    void click(View view) {
        switch (view.getId()) {
            case R.id.cooperation_choose_license:
                //营业执照
                tempCode = Constants.REQUEST_LICENSE;
                break;
            case R.id.cooperation_choose_identify_x:
                //身份证正面
                tempCode = Constants.REQUEST_ID_X;
                break;
            case R.id.cooperation_choose_identify_y:
                //身份证反面
                tempCode = Constants.REQUEST_ID_Y;
                break;
            case R.id.cooperation_choose_hygiene:
                //卫生许可证
                tempCode = Constants.REQUEST_HYGIENE;
                break;
            case R.id.cooperation_choose_other1:
                //其他证
                tempCode = Constants.REQUEST_OTHER1;
                break;
            case R.id.cooperation_choose_other2:
                //其他证
                tempCode = Constants.REQUEST_OTHER2;
                break;
        }
        /**
         * 先申请权限
         */
        applyPermissions();
    }

    @Override
    public void takeSuccess(TResult result) {
        switch (tempCode) {
            case Constants.REQUEST_LICENSE:
                //营业执照
                handlePhotoReult(mIvLicense, mTxtLicense, result.getImage().getCompressPath());
                break;
            case Constants.REQUEST_ID_X:
                //身份证正面
                handlePhotoReult(mIvIdX, mTxtIdX, result.getImage().getCompressPath());
                break;
            case Constants.REQUEST_ID_Y:
                //身份证反面
                handlePhotoReult(mIvIdY, mTxtIdY, result.getImage().getCompressPath());
                break;
            case Constants.REQUEST_HYGIENE:
                //卫生许可证
                handlePhotoReult(mIvHygiene, mTxtHygiene, result.getImage().getCompressPath());
                break;
            case Constants.REQUEST_OTHER1:
                //其他证
                handlePhotoReult(mIvOther1, mTxtOther1, result.getImage().getCompressPath());
                break;
            case Constants.REQUEST_OTHER2:
                //其他证
                handlePhotoReult(mIvOther2, mTxtOther2, result.getImage().getCompressPath());
                break;
        }
    }

    void handlePhotoReult(ImageView iv, TextView tv, String url) {
        tempCode = 0;
        Picasso.with(this).load(new File(url)).into(iv);
        tv.setVisibility(View.GONE);
        iv.setTag(url);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        tempCode = 0;
    }

    @Override
    public void takeCancel() {
        tempCode = 0;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 申请权限
     */
    private void applyPermissions() {
        PermissionUtils.checkPermission(this, Manifest.permission.CAMERA,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        customHelper.onClick(takePhoto);
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showReqPermissionsDialog();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestPermission(CooperationActivity.this, Manifest.permission.CAMERA, Constants.REQUEST_CODE_CAMERA);
                    }
                });
    }

    /**
     * 随便的弹框
     */
    private void showReqPermissionsDialog() {
        CommonDialogs.showSelectDialog(this, "申请权限", "拍照需要开启相机权限才能实现功能，请转到应用的设置界面，请开启应用的相关权限。", "前往", "取消",
                new CommonDialogs.DialogClickListener() {
                    @Override
                    public void confirm() {
                        PermissionUtils.toAppSetting(CooperationActivity.this);
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }

    /**
     * 创建保存应用数据的包名文件夹
     * temp 拍照临时文件夹
     * cache 裁剪图片的文件夹
     */
    private void initDir() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + UApplication.getGobalApplication().getPackageName());
        File dirFile = new File(file.getAbsolutePath());

        File fileTemp = new File(Environment.getExternalStorageDirectory() + File.separator
                + UApplication.getGobalApplication().getPackageName() + File.separator + "temp");

        File fileCache = new File(Environment.getExternalStorageDirectory() + File.separator
                + UApplication.getGobalApplication().getPackageName() + File.separator + "cache");

        //包名文件夹
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //拍照临时文件夹
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }
        //裁剪图片的文件夹
        if (!fileCache.exists()) {
            fileCache.mkdirs();
        }
    }
}
