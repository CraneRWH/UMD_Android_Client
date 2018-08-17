package com.ymd.client.component.activity;

import android.content.DialogInterface;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.PermissionUtils;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    @Override
    protected Class getPresenterClass() {
        return null;
    }

    @Override
    protected Class getViewClass() {
        return null;
    }

    @Override
    protected void initViews() {
        setAbContentView(R.layout.activity_main);
        setSwipeBackEnable(false);//屏蔽左滑返回
        setTitleBarVisiable(false);//屏蔽标题栏

        applyPermissions();
    }

    private void applyPermissions() {
        PermissionUtils.checkPermission(this, WRITE_EXTERNAL_STORAGE,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showReqPermissionsDialog();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE, Constants.REQUEST_CODE_CAMERA);
                    }
                });
    }

    /**
     * 随便的弹框
     */
    private void showReqPermissionsDialog() {
//        showCommonDialogs("申请权限", "需要读写SD卡权限才能实现功能，请转到应用的设置界面，请开启应用的相关权限。", "前往", "取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                PermissionUtils.toAppSetting(MainActivity.this);
//            }
//        }, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_CAMERA:
                if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
                    // 权限申请成功
                } else {
                    showReqPermissionsDialog();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
