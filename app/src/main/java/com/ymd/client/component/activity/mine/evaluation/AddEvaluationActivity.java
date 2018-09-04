package com.ymd.client.component.activity.mine.evaluation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.ymd.client.component.activity.mine.PersonInfoActivity;
import com.ymd.client.component.widget.RatingView;
import com.ymd.client.component.widget.dialog.CommonDialogs;
import com.ymd.client.component.widget.photo.ChoiceImageCallBack;
import com.ymd.client.component.widget.photo.ChoiceImageUtil;
import com.ymd.client.component.widget.photo.selectphoto.Bimp;
import com.ymd.client.component.widget.photo.selectphoto.FileUtils;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.PermissionUtils;
import com.ymd.client.utils.ScreenUtil;
import com.ymd.client.utils.StatusBarUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 追加评价
 */
public class AddEvaluationActivity extends BaseActivity {

    @BindView(R.id.base_title)
    TextView mTxtTitle;//标题

    @BindView(R.id.add_evaluation_1)
    View mLayout1;
    @BindView(R.id.add_evaluation_2)
    View mLayout2;
    @BindView(R.id.add_evaluation_3)
    View mLayout3;

    @BindView(R.id.add_evaluation_iv1)
    ImageView mIv1;//图片1
    @BindView(R.id.add_evaluation_iv2)
    ImageView mIv2;//图片2
    @BindView(R.id.add_evaluation_iv3)
    ImageView mIv3;//图片3

    @BindView(R.id.add_evaluation_input)
    EditText mEtInput;//评价内容
    @BindView(R.id.add_evaluation_count)
    TextView mTxtCount;//评价内容字数

    ChoiceImageUtil ciutil;//图片选择工具
    int requestCode = -1;//选择图片时的请求码

    @BindView(R.id.add_evaluation_rate)
    RatingView mRv;//评价星级

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evaluation);

        ButterKnife.bind(this);
        setStatusBar(R.color.white);
        mTxtTitle.setText("追加评价");

        init();
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


    /**
     * 设置view的宽高一致
     */
    private void init() {
        int width = (ScreenUtil.getScreenWidthPix(this) - ScreenUtil.dip2px(this, 27)) / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        mLayout3.setLayoutParams(params);

        params.rightMargin = ScreenUtil.dip2px(this, 4);
        mLayout1.setLayoutParams(params);
        mLayout2.setLayoutParams(params);

        ciutil = new ChoiceImageUtil(this);
        /**
         * 先创建文件夹，默认开启权限了
         */
        initDir();
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


    /**
     * 设置输入监听事件
     */
    @OnTextChanged(value = R.id.add_evaluation_input, callback = OnTextChanged.Callback
            .AFTER_TEXT_CHANGED)
    public void afterAmountTextChanged() {
        if (mEtInput.getText().toString().length() == 0) {
            mTxtCount.setText("最多输入150个字");
        } else {
            mTxtCount.setText("剩余可输入" + (150 - mEtInput.getText().toString().length()) + "个字");
        }
    }

    @OnClick({R.id.add_evaluation_1, R.id.add_evaluation_2, R.id.add_evaluation_3})
    void click(View view) {
        switch (view.getId()) {
            case R.id.add_evaluation_1:
                requestCode = Constants.REQUEST_ID_X;
                break;
            case R.id.add_evaluation_2:
                requestCode = Constants.REQUEST_OTHER1;
                break;
            case R.id.add_evaluation_3:
                requestCode = Constants.REQUEST_LICENSE;
                break;
        }
        chooseImg();
    }

    void chooseImg(){
        final String[] items = getResources().getStringArray(R.array.choose_photo);
        String title = "选择图片";
        CommonDialogs.showListDialog(this, title, items,
                new CommonDialogs.DialogItemClickListener() {
                    @Override
                    public void confirm(String result) {
                        if (items[0].equals(result)) {
                            //相册选择
                            ciutil.ChoiceFromAlbum(false);
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
                        ciutil.ChoiceFromCamara(false);
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showReqPermissionsDialog();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestPermission(AddEvaluationActivity.this, Manifest.permission.CAMERA, Constants.REQUEST_CODE_CAMERA);
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
                        PermissionUtils.toAppSetting(AddEvaluationActivity.this);
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
                    ciutil.ChoiceFromCamara(false);
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

                        bindImage(fileUrl);
                    }
                });
    }

    void bindImage(String fileUrl){
        switch (requestCode){
            case Constants.REQUEST_ID_X:
                Picasso.with(AddEvaluationActivity.this).load(new File(fileUrl)).into(mIv1);
                requestCode = -1;

                mIv1.setTag(fileUrl);
                break;
            case Constants.REQUEST_OTHER1:
                Picasso.with(AddEvaluationActivity.this).load(new File(fileUrl)).into(mIv2);
                requestCode = -1;

                mIv2.setTag(fileUrl);
                break;
            case Constants.REQUEST_LICENSE:
                Picasso.with(AddEvaluationActivity.this).load(new File(fileUrl)).into(mIv3);
                requestCode = -1;

                mIv3.setTag(fileUrl);
                break;
        }
    }

    @OnClick(R.id.add_evaluation_submit)
    void toSubmit(){
       float rating = mRv.getRating();
       String content = mEtInput.getText().toString().trim();

       String filePath1 = (String) mIv1.getTag();
       String filePath2 = (String) mIv2.getTag();
       String filePath3 = (String) mIv3.getTag();

       //校验评价是否符合要求,然后提交

    }
}
