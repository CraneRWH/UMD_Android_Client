package com.ymd.client.component.activity.mine.info;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.squareup.picasso.Picasso;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.event.LoginRefreshEvent;
import com.ymd.client.component.widget.dialog.CommonDialogs;
import com.ymd.client.component.widget.photo.ChoiceImageCallBack;
import com.ymd.client.component.widget.photo.ChoiceImageUtil;
import com.ymd.client.component.widget.photo.selectphoto.Bimp;
import com.ymd.client.component.widget.photo.selectphoto.FileUtils;
import com.ymd.client.model.bean.user.UserObject;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.PermissionUtils;
import com.ymd.client.utils.StatusBarUtils;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-个人信息
 */
public class PersonInfoActivity extends BaseActivity {

    @BindView(R.id.person_alter_head_iv)
    ImageView mIvHead;//头像
    @BindView(R.id.person_nickname)
    TextView mTxtNickName;//昵称
    @BindView(R.id.person_phone)
    TextView mTxtPhone;//手机号
    @BindView(R.id.person_sex)
    TextView mTxtSex;//性别
    @BindView(R.id.person_birth)
    TextView mTxtBirth;//出生日期

    ChoiceImageUtil ciutil;//图片选择工具
    private TimePickerView pickerView;//时间选择器

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        setTitle(getResources().getString(R.string.fragment_person));

        ciutil = new ChoiceImageUtil(this);

        initTimePicker();

        initView();
    }

    private void initView(){

        mTxtNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NickNameChangeActivity.startAction(PersonInfoActivity.this);
            }
        });
        mTxtSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberSexChangeActivity.startAction(PersonInfoActivity.this);
            }
        });

        resetUserViewInfo();
    }

    private void resetUserViewInfo() {
        UserObject userObject = LoginInfo.getInstance().getLoginInfo();
        mTxtNickName.setText(userObject.getUserName());
        mTxtPhone.setText(userObject.getPhone());
        mTxtSex.setText(ToolUtil.changeInteger(userObject.getSex()) == 0 ?"男":"女");
        mTxtBirth.setText(userObject.getBirthday());

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


                        uploadFile(fileUrl);
                    }
                });
        if (resultCode == 1) {
            Bundle bundle = mIntent.getExtras();
            updateInfo(bundle.getString("key"), bundle.getString("value"));
        }
    }

    @OnClick(R.id.person_layout_birth)
    void alterBirth() {
        pickerView.show();
    }

    /**
     * 获取时间
     * @param date 选择的时间
     * @return 截取的年月日
     */
    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {
        Calendar endDate = Calendar.getInstance();//结束时间
        Calendar startDate = Calendar.getInstance();//开始时间
        startDate.set(1900, 1, 1);

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(1990, 0, 1);//选中的时间
        pickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTxtBirth.setText(getTime(date));
                updateInfo("birthday", getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();

        Dialog mDialog = pickerView.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private void updateInfo(String key, String value) {
        Map<String,Object> params = new HashMap<>();
        params.put(key, value);
        WebUtil.getInstance().requestPOST(this, URLConstant.UPDATE_USER_INFO, params,true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        LoginInfo.setLoginInfo(resultJson.optString("user"));
                        EventBus.getDefault().post(new LoginRefreshEvent(true));
                        resetUserViewInfo();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void uploadFile(String fileUrl) {
        WebUtil.getInstance().sendParamsPhotoFile(this, new File(fileUrl),
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                        updateInfo("icon", resultJson.optString("url"));
                        Picasso.with(PersonInfoActivity.this).load(new File(fileUrl)).into(mIvHead);
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }
}
