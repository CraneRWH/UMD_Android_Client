package com.ymd.client.component.activity.homePage.merchant;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.adapter.PictureItemAdapter;
import com.ymd.client.component.widget.dialog.CommonDialogs;
import com.ymd.client.component.widget.photo.ChoiceImageCallBack;
import com.ymd.client.component.widget.photo.ChoiceImageUtil;
import com.ymd.client.component.widget.photo.selectphoto.Bimp;
import com.ymd.client.component.widget.photo.selectphoto.FileUtils;
import com.ymd.client.model.bean.PictureEntity;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.model.info.LoginInfo;
import com.ymd.client.utils.PermissionUtils;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;
import com.ymd.client.web.WebUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/28
 * 描述:    对商家的评论界面
 * 修改历史:
 */
public class CommentSellerActivity extends BaseActivity {

    @BindView(R.id.score_lt)
    LinearLayout scoreLt;
    @BindView(R.id.comment_et)
    EditText commentEt;
    @BindView(R.id.picture_gv)
    RecyclerView pictureGv;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    private int score = 0;

    int photoPosition = 0;
    List<PictureEntity> pictures;
    ChoiceImageUtil ciutil;//图片选择工具
    OrderResultForm orderDetail;
    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context, OrderResultForm order) {
        Intent intent = new Intent(context, CommentSellerActivity.class);
        intent.putExtra("order", order);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_seller);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("评价");
        orderDetail = (OrderResultForm) getIntent().getExtras().getSerializable("order");
        ciutil = new ChoiceImageUtil(this);
        for (int i = 0; i < 5; i ++) {
            final int position = i;
            CheckBox view = new CheckBox(this);
            view.setButtonDrawable(R.drawable.checkbox_star_selector);
            view.setChecked(false);
            view.setPadding(5, 0, 5, 0);
            view.setLayoutParams(new LinearLayout.LayoutParams(30,30));
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    setChoose(scoreLt, position);
                    score = position + 1;
                }
            });
            scoreLt.addView(view);
        }
        pictures = new ArrayList<>();
        PictureEntity picture = new PictureEntity();
        picture.setIcon(R.mipmap.icon_comment_star_camera);
        pictures.add(picture);

        picture = new PictureEntity();
        picture.setIcon(R.mipmap.icon_comment_star_camera);
        pictures.add(picture);

        picture = new PictureEntity();
        picture.setIcon(R.mipmap.icon_comment_star_camera);
        pictures.add(picture);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        pictureGv.setLayoutManager(layoutManager);
        resetPictureGrid();
    }

    private void resetPictureGrid() {
        PictureItemAdapter adapter = new PictureItemAdapter(pictures, this);
        adapter.setListener(new OnUMDItemClickListener() {
            @Override
            public void onClick(Object data, View view, int position) {
                photoPosition = position;
                getPhoto();
            }
        });
        pictureGv.setAdapter(adapter);
    }

    private void setChoose(LinearLayout layout,int position) {
        score = position + 1;
        for(int i = 0 ; i < layout.getChildCount() ; i ++ ) {
            CheckBox view = (CheckBox) layout.getChildAt(i);
            if (i <= position) {
                view.setChecked(true);
            }
            else {
                view.setChecked(false);
            }
        }
    }


    /**
     *
     */
    private void submit() {
        String content = commentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.ToastMessage(this,"请输入评价内容");
            return;
        }
        if (score == 0) {
            ToastUtil.ToastMessage(this,"请选择评价分数");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("content", ToolUtil.changeString(content));
        params.put("dealId", orderDetail.getId());
        params.put("score", score);
        params.put("userName", LoginInfo.getInstance().getLoginInfo().getUserName());
        params.put("userUrl", LoginInfo.getInstance().getLoginInfo().getIcon());
        List<String> fileList = new ArrayList<>();
        for (PictureEntity item :pictures) {
            if (!TextUtils.isEmpty(item.getUrl())) {
                fileList.add(item.getUrl());
            }
        }
        params.put("fileList", fileList);
        WebUtil.getInstance().requestPOST(this, URLConstant.MERCHANT_ADD_EVALUATION, params,true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        finish();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

                    }
                });
    }

    private void getPhoto() {
        final String[] items = getResources().getStringArray(R.array.choose_photo);
        String title = getString(R.string.upload_photo_title);
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

    private void uploadFile(String fileUrl) {
        WebUtil.getInstance().sendParamsPhotoFile(this, new File(fileUrl),
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject resultJson) {
                       /* updateInfo("photo", resultJson.optString("photo"));
                        Picasso.with(PersonInfoActivity.this).load(new File(fileUrl)).into(mIvHead);*/
                        pictures.get(photoPosition).setUrl(resultJson.optString("url"));
                    /*    ((MySimpleAdapter)pictureGv.getAdapter()).notifyDataSetChanged();*/
                        resetPictureGrid();
                    }

                    @Override
                    public void onWebFailed(String errorMsg) {

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
                        PermissionUtils.requestPermission(CommentSellerActivity.this, Manifest.permission.CAMERA, Constants.REQUEST_CODE_CAMERA);
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
                        PermissionUtils.toAppSetting(CommentSellerActivity.this);
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
    }

}
