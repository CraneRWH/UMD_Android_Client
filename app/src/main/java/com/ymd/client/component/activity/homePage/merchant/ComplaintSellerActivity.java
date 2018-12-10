package com.ymd.client.component.activity.homePage.merchant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.model.constant.URLConstant;
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
 * 描述:    对商家的投诉界面
 * 修改历史:
 */
public class ComplaintSellerActivity extends BaseActivity {

    @BindView(R.id.complaintLayout)
    LinearLayout complaintLayout;
    @BindView(R.id.comment_et)
    EditText commentEt;
    @BindView(R.id.picture_gv)
    RecyclerView pictureGv;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    ChoiceImageUtil ciutil;//图片选择工具
    MerchantInfoEntity merchantInfo;
    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context,
                                   MerchantInfoEntity merchantInfo) {
        Intent intent = new Intent(context, ComplaintSellerActivity.class);
        intent.putExtra("merchant", merchantInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_seller);
        ButterKnife.bind(this);
        initView();
    //    setComplaintTypeData();
    }

    int photoPosition = 0;
    List<PictureEntity> pictures;
    private void initView() {
        ciutil = new ChoiceImageUtil(this);
        merchantInfo = (MerchantInfoEntity) getIntent().getExtras().getSerializable("merchant");
        pictures = new ArrayList<>();
        PictureEntity picture = new PictureEntity();
        picture.setIcon(R.mipmap.icon_comment_star_camera);
        pictures.add(picture);
/*
        picture = new PictureEntity();
        picture.setIcon(R.mipmap.icon_comment_star_camera);
        pictures.add(picture);

        picture = new PictureEntity();
        picture.setIcon(R.mipmap.icon_comment_star_camera);
        pictures.add(picture);*/

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        pictureGv.setLayoutManager(layoutManager);
        resetPictureGrid();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
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

    List<Map<String, Object>> complaintTypeList = new ArrayList<>();
    private void setComplaintTypeData() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "拒绝服务");
        map.put("isChoose", true);
        complaintTypeList.add(map);

        map = new HashMap<>();
        map.put("name", "卫生差");
        map.put("isChoose", true);
        complaintTypeList.add(map);

        map = new HashMap<>();
        map.put("name", "商家欺诈");
        map.put("isChoose", false);
        complaintTypeList.add(map);


        map = new HashMap<>();
        map.put("name", "未提供优惠");
        map.put("isChoose", false);
        complaintTypeList.add(map);

        refreshData(0);
    }

    private int chooseType = -1;
    @SuppressLint("ResourceAsColor")
    private void refreshData(int p) {

        chooseType = p;

        for (Map<String,Object> item : complaintTypeList) {
            item.put("isChoose", false);
        }
        complaintTypeList.get(p).put("isChoose", true);

        complaintLayout.removeAllViews();
        //开始添加数据
        for (int i = 0; i < complaintTypeList.size(); i++) {
            int position = i;
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(this).inflate(R.layout.item_seller_complaaint_type, complaintLayout, false);
            //实例化TextView控件
            TextView tv = (TextView) view.findViewById(R.id.item_tv);
            //给TextView添加文字
            tv.setText(ToolUtil.changeString(complaintTypeList.get(i).get("name")));
            if (ToolUtil.changeBoolean(complaintTypeList.get(i).get("isChoose"))) {
                tv.setTextColor(Color.WHITE);
                tv.setBackgroundResource(R.drawable.shape_rect_corner_orange);
            } else {
                tv.setTextColor(R.color.text_gray_dark);
                tv.setBackgroundResource(R.drawable.shape_rect_corner_gray_edge);
            }
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshData(position);
                }
            });
            //把行布局放到linear里
            complaintLayout.addView(view);
        }
    }

    /**
     *
     */
    private void submit() {
        String content = commentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.ToastMessage(this,"请输入投诉内容");
            return;
        }
   /*     if (chooseType <0) {
            ToastUtil.ToastMessage(this,"请选择投诉类型");
            return;
        }*/
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        params.put("complaintsContent", content);
        params.put("complaintsPhotoOne", ToolUtil.changeString(pictures.get(0).getUrl()));
        params.put("complaintsPhotoThree", ToolUtil.changeString(pictures.get(2).getUrl()));
        params.put("complaintsPhotoTwo", ToolUtil.changeString(pictures.get(1).getUrl()));
   //     params.put("complaintsType", chooseType);
        WebUtil.getInstance().requestPOST(this, URLConstant.MERCHANT_COMPLAINT, params, true,
                new WebUtil.WebCallBack() {
                    @Override
                    public void onWebSuccess(JSONObject result) {
                        ToastUtil.ToastMessage(ComplaintSellerActivity.this,"投诉成功");
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
                        pictures.get(photoPosition).setUrl(resultJson.optString("url"));
                        if (photoPosition < 2) {
                            PictureEntity picture = new PictureEntity();
                            picture.setIcon(R.mipmap.icon_comment_star_camera);
                            pictures.add(picture);
                        }
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
                        PermissionUtils.requestPermission(ComplaintSellerActivity.this, Manifest.permission.CAMERA, Constants.REQUEST_CODE_CAMERA);
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
                        PermissionUtils.toAppSetting(ComplaintSellerActivity.this);
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
