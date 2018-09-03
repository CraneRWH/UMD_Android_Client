package com.ymd.client.component.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.widget.dialog.CommonDialogs;
import com.ymd.client.component.widget.photo.ChoiceImageCallBack;
import com.ymd.client.component.widget.photo.ChoiceImageUtil;
import com.ymd.client.component.widget.photo.selectphoto.Bimp;
import com.ymd.client.component.widget.photo.selectphoto.FileUtils;
import com.ymd.client.component.widget.takephoto.permission.PermissionManager;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.PermissionUtils;
import com.ymd.client.utils.StatusBarUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-个人信息
 */
public class PersonInfoActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;

    @BindView(R.id.person_alter_head_iv)
    ImageView mIvHead;//头像

    ChoiceImageUtil ciutil;
    String chooseImgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText(getResources().getString(R.string.fragment_person));

        ciutil = new ChoiceImageUtil(this);
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

    @OnClick(R.id.person_alter_head)
    void alterHeadImg() {
        final String[] items = getResources().getStringArray(R.array.choose_photo);
        String title = getString(R.string.choose_photo_title);
        CommonDialogs.showListDialog(this, title, items,
                new CommonDialogs.DialogItemClickListener() {
                    @Override
                    public void confirm(String result) {
                        if (items[0].equals(result)) {
                            //相册选择
                            ciutil.ChoiceFromAlbum(true);
                        } else if (items[1].equals(result)) {
                            //检查权限
                            applyPermissions();
                        }
                    }
                });
    }
    /**
     * 申请权限
     */
    private void applyPermissions() {
        PermissionUtils.checkPermission(this, Manifest.permission.CAMERA,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        ciutil.ChoiceFromCamara(true);
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showReqPermissionsDialog();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestPermission(PersonInfoActivity.this, Manifest.permission.CAMERA, Constants.REQUEST_CODE_CAMERA);
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
                        PermissionUtils.toAppSetting(PersonInfoActivity.this);
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_CODE_CAMERA:
                if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
                    // 权限申请成功
                    ciutil.ChoiceFromCamara(true);
                } else {
                    showReqPermissionsDialog();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
        super.onActivityResult(requestCode, resultCode, mIntent);
        ciutil.onActivityResult(requestCode, resultCode, mIntent,
                new ChoiceImageCallBack() {

                    @Override
                    public void onChoiceImage(String path, boolean falg) {
                        super.onChoiceImage(path, falg);
                        //TODO showProressview();
                        Bitmap bm = null;
                        try {
                            // 压缩原照片
                            bm = Bimp.revitionImageSize(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 获取文件名称
                        String newStr = path.substring(
                                path.lastIndexOf("/") + 1,
                                path.lastIndexOf("."));
                        // 保存压缩好的图片到临时文件夹
                        FileUtils.saveBitmap(bm, "" + newStr);
                        // 压缩好后照片的地址
                        String fileUrl = FileUtils.SDPATH + newStr + ".jpeg";


                        Picasso.with(PersonInfoActivity.this).load(new File(fileUrl)).into(mIvHead);
                    }
                });
    }

}
