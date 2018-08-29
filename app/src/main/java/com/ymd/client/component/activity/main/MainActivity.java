package com.ymd.client.component.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.ymd.client.R;
import com.ymd.client.UApplication;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.homePage.MainHomePageFragment;
import com.ymd.client.component.activity.mine.MainMineFragment;
import com.ymd.client.component.activity.order.MainOrderFragment;
import com.ymd.client.component.activity.sao.MainSaoFragment;
import com.ymd.client.component.widget.dialog.CommonDialogs;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.PermissionUtils;
import com.ymd.client.utils.helper.BottomNavigationViewHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * 作者:rongweihe
 * 日期:2018/8/18  时间:17:24
 * 描述:    主界面
 * 修改历史:
 */
public class MainActivity extends BaseActivity {


    private BottomNavigationView navigationView;

    private Map<Integer, Fragment> mainFragments = new HashMap<Integer, Fragment>();
    private MainHomePageFragment homePageFragment;
    private MainSaoFragment saoFragment;
    private MainOrderFragment orderFragment;
    private MainMineFragment mineFragment;

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        chooseMainItem(0);
                        break;
                  /*  case R.id.navigation_sao:
                        chooseMainItem(1);
                        break;*/
                    case R.id.navigation_order:
                        chooseMainItem(1);
                        break;
                    case R.id.navigation_mine:
                        chooseMainItem(2);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        chooseMainItem(0);

        //申请文件权限
        applyPermissions();
    }

    private void chooseMainItem(int tag) {
        FragmentManager fragmengManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmengManager.beginTransaction();
        try {
            if (!mainFragments.containsKey(tag)) {
                if (tag == 0) {
                    homePageFragment = new MainHomePageFragment();        //主页选项卡
                    fragmentTransaction.add(R.id.fragmentLayout, homePageFragment, "home");
                    mainFragments.put(0, homePageFragment);
                }
                /*else if (tag == 1) {
                    saoFragment = new MainSaoFragment();		//扫一扫选项卡
                    fragmentTransaction.add(R.id.fragmentLayout, saoFragment, "sao");
                    mainFragments.put(1,saoFragment);
                }*/
                else if (tag == 1) {
                    orderFragment = new MainOrderFragment();        //订单选项卡

                    fragmentTransaction.add(R.id.fragmentLayout, orderFragment, "order");
                    mainFragments.put(1, orderFragment);
                } else if (tag == 2) {
                    mineFragment = new MainMineFragment();        //“我的”选项卡

                    fragmentTransaction.add(R.id.fragmentLayout, mineFragment, "mine");
                    mainFragments.put(2, mineFragment);
                }
            }
            for (int key : mainFragments.keySet()) {
                if (key == tag) {
                    fragmentTransaction.show(mainFragments.get(key));

                } else {
                    fragmentTransaction.hide(mainFragments.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fragmentTransaction.commit();
        }
    }

    /**
     * 申请文件权限
     */
    private void applyPermissions() {
        PermissionUtils.checkPermission(this, WRITE_EXTERNAL_STORAGE,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        initDir();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showReqPermissionsDialog();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE, Constants.REQUEST_CODE_STORE);
                    }
                });
    }

    /**
     * 随便的弹框
     */
    private void showReqPermissionsDialog() {
        CommonDialogs.showSelectDialog(this, "申请权限", "需要读写SD卡权限才能实现功能，请转到应用的设置界面，请开启应用的相关权限。", "前往", "取消", new CommonDialogs.DialogClickListener() {
            @Override
            public void confirm() {
                PermissionUtils.toAppSetting(MainActivity.this);
            }

            @Override
            public void cancel() {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_STORE:
                if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
                    // 权限申请成功
                    initDir();
                } else {
                    showReqPermissionsDialog();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
