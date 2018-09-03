package com.ymd.client.component.widget.photo;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.AbStrUtil;

import java.io.File;

public class ChoiceImageUtil {
    /* 用来标识请求照相功能的activity */
    private static final int CAMERA_WITH_DATA = 3023;
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    /* 用来标识请求裁剪图片后的activity */
    private static final int CAMERA_CROP_DATA = 3022;

    /* 拍照的照片存储位置 */
    private File PHOTO_DIR = null;
    // 照相机拍照得到的图片
    private File mCurrentPhotoFile;
    private String mFileName;

    BaseActivity activity;

    String imageDownloadDir;

    public boolean needcrop = false;
    public static final String TAG = "ChoiceImageUtil";

    public ChoiceImageUtil(BaseActivity activity) {
        // 初始化图片保存路径
        this.activity = activity;
        String photo_dir = getImageDownloadDir();
        if (AbStrUtil.isEmpty(photo_dir)) {
            Toast.makeText(activity, activity.getString(R.string.choose_photo_no_card), Toast.LENGTH_SHORT).show();
        } else {
            PHOTO_DIR = new File(photo_dir);
        }

    }

    private String getImageDownloadDir() {
        if (imageDownloadDir == null) {
            File root = Environment.getExternalStorageDirectory();
            // 默认下载文件根目录.
            String downloadRootPath = File.separator + Constants.ROOT_PATH + File.separator
                    + activity.getPackageName() + File.separator;
            // 默认下载图片文件目录.
            String imageDownloadPath = downloadRootPath + Constants.IMAGE_PATH + File.separator;

            File imageDownloadDirFile = new File(root.getAbsolutePath()
                    + imageDownloadPath);
            if (!imageDownloadDirFile.exists()) {
                imageDownloadDirFile.mkdirs();
            }
            imageDownloadDir = imageDownloadDirFile.getPath();
        }
        return imageDownloadDir;

    }

    /**
     * 从相册获取
     *
     * @param
     */
    public void ChoiceFromAlbum(boolean needCrop) {
        this.needcrop = needCrop;
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            activity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, activity.getString(R.string.choose_photo_no_photo), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从照相机获取
     */
    public void ChoiceFromCamara(boolean needCrop) {
        this.needcrop = needCrop;
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            doTakePhoto();
        } else {
            Toast.makeText(activity, activity.getString(R.string.choose_photo_no_avaiable_photo), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            mFileName = System.currentTimeMillis() + ".jpg";
            mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //24以上使用FileProvider
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(activity, "com.ymd.client.fileProvider", mCurrentPhotoFile));
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(mCurrentPhotoFile));
            }
            activity.startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.choose_photo_no_program), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况, 他们启动时是这样的startActivityForResult
     */
    public void onActivityResult(int requestCode, int resultCode, Intent mIntent, ChoiceImageCallBack callBack) {
        if (resultCode != activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA:
                Uri uri = mIntent.getData();

                String currentFilePath = getPath(uri);
                if (!AbStrUtil.isEmpty(currentFilePath)) {
                    if (needcrop) {
                        Intent intent1 = new Intent(activity, CropImageActivity.class);
                        intent1.putExtra("PATH", currentFilePath);
                        activity.startActivityForResult(intent1, CAMERA_CROP_DATA);
                    } else {
                        callBack.onChoiceImage(currentFilePath, false);
                    }

                } else {
                    Toast.makeText(activity, activity.getString(R.string.choose_photo_no_file_found), Toast.LENGTH_SHORT).show();
                }
                break;
            case CAMERA_WITH_DATA:
                Log.d(TAG, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
                String currentFilePath2 = mCurrentPhotoFile.getPath();
                if (needcrop) {
                    Intent intent2 = new Intent(activity, CropImageActivity.class);
                    intent2.putExtra("PATH", currentFilePath2);
                    activity.startActivityForResult(intent2, CAMERA_CROP_DATA);
                } else {
                    callBack.onChoiceImage(currentFilePath2, false);
                }

                break;
            case CAMERA_CROP_DATA:
                String path = mIntent.getStringExtra("PATH");
                Log.d(TAG, "裁剪后得到的图片的路径是 = " + path);
                callBack.onChoiceImage(path, true);
                break;
        }
    }


    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public String getPath(Uri uri) {
        if (AbStrUtil.isEmpty(uri.getAuthority())) {
            if (!AbStrUtil.isEmpty(uri.getPath())) {
                return uri.getPath();
            } else {
                return null;
            }
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity
                    .managedQuery(uri, projection, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);

            return path;
        }
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     * @param intent
     * @return
     */
    public Uri geturi(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = activity.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

}
