package com.ymd.client.component.activity.homePage.food.seller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.widget.dialog.CommonDialogs;
import com.ymd.client.component.widget.photo.ChoiceImageCallBack;
import com.ymd.client.component.widget.photo.ChoiceImageUtil;
import com.ymd.client.component.widget.photo.selectphoto.Bimp;
import com.ymd.client.component.widget.photo.selectphoto.FileUtils;
import com.ymd.client.component.widget.recyclerView.MyGridView;
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
 * 描述:    对商家的评论界面
 * 修改历史:
 */
public class CommentSellerActivity extends BaseActivity {

    @BindView(R.id.score_lt)
    LinearLayout scoreLt;
    @BindView(R.id.comment_et)
    EditText commentEt;
    @BindView(R.id.picture_gv)
    MyGridView pictureGv;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    private int score = 0;

    int photoPosition = 0;
    List<Map<String,Object>> pictures;
    ChoiceImageUtil ciutil;//图片选择工具
    MerchantInfoEntity merchantInfo;
    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, CommentSellerActivity.class);
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
        ciutil = new ChoiceImageUtil(this);
        for (int i = 0; i < 5; i ++) {
            final int position = i;
            CheckBox view = new CheckBox(this);
            view.setButtonDrawable(R.drawable.checkbox_star_selector);
            view.setChecked(false);
            view.setPadding(5, 0, 5, 0);
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
        Map<String,Object> map = new HashMap<>();
        map.put("icon", R.mipmap.icon_comment_star_camera);
        pictures.add(map);

        map = new HashMap<>();
        map.put("icon", R.mipmap.icon_comment_star_camera);
        pictures.add(map);

        map = new HashMap<>();
        map.put("icon", R.mipmap.icon_comment_star_camera);
        pictures.add(map);
        MySimpleAdapter adapter = new MySimpleAdapter(this, pictures, R.layout.item_grid_picture, new String[]{"icon"}, new int[]{R.id.icon_iv},
                new MySimpleAdapter.MyViewListener() {
                    @Override
                    public void callBackViewListener(Map<String, Object> data, View view, ViewGroup parent, int position) {
                        if (ToolUtil.changeString(data.get("url")).length() >0) {
                            ImageView iv = (ImageView)view.findViewById(R.id.icon_iv);
                            Glide.with(getApplicationContext()).load(ToolUtil.changeString(data.get("url"))).into(iv);
                        }
                    }
                });
        pictureGv.setAdapter(adapter);
        pictureGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                photoPosition = position;
                getPhoto();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void setChoose(LinearLayout layout,int position) {
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
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchantInfo.getId());
        params.put("complaintsContent", content);
        params.put("complaintsPhotoOne", ToolUtil.changeString(pictures.get(0).get("url")));
        params.put("complaintsPhotoThree", ToolUtil.changeString(pictures.get(2).get("url")));
        params.put("complaintsPhotoTwo", ToolUtil.changeString(pictures.get(1).get("url")));
        WebUtil.getInstance().requestPOST(this, URLConstant.MERCHANT_COMPLAINT, params,
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
                        Map<String,Object> map = pictures.get(photoPosition);
                        map.put("url", resultJson.optString("fileUrl"));
                        ((MySimpleAdapter)pictureGv.getAdapter()).notifyDataSetChanged();
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
