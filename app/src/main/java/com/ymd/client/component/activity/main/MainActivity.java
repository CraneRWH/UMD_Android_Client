package com.ymd.client.component.activity.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.homePage.MainHomePageFragment;
import com.ymd.client.component.activity.mine.MainMineFragment;
import com.ymd.client.component.activity.order.MainOrderFragment;
import com.ymd.client.component.activity.sao.MainSaoFragment;
import com.ymd.client.model.constant.Constants;
import com.ymd.client.utils.PermissionUtils;
import com.ymd.client.utils.helper.BottomNavigationViewHelper;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 *
 *   作者:rongweihe
 *   日期:2018/8/18  时间:17:24
 *   描述:    主界面
 *   修改历史:
 *
 */
public class MainActivity extends BaseActivity {


    private BottomNavigationView navigationView;

    private Map<Integer,Fragment> mainFragments = new HashMap<Integer,Fragment>();
    private MainHomePageFragment homePageFragment;
    private MainSaoFragment saoFragment;
    private MainOrderFragment orderFragment;
    private MainMineFragment mineFragment;

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
                    case R.id.navigation_sao:
                        chooseMainItem(1);
                        break;
                    case R.id.navigation_order:
                        chooseMainItem(2);
                        break;
                    case R.id.navigation_mine:
                        chooseMainItem(3);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        chooseMainItem(0);
    }

    private void chooseMainItem(int tag) {
        FragmentManager fragmengManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmengManager.beginTransaction();
        try {
            if (!mainFragments.containsKey(tag)) {
                if (tag == 0) {
                    homePageFragment = new MainHomePageFragment();		//主页选项卡
                    fragmentTransaction.add(R.id.fragmentLayout, homePageFragment, "home");
                    mainFragments.put(0,homePageFragment);
                }
                else if (tag == 1) {
                    saoFragment = new MainSaoFragment();		//扫一扫选项卡
                    fragmentTransaction.add(R.id.fragmentLayout, saoFragment, "sao");
                    mainFragments.put(1,saoFragment);
                }
                else if (tag == 2) {
                    orderFragment = new MainOrderFragment();		//订单选项卡

                    fragmentTransaction.add(R.id.fragmentLayout, orderFragment, "order");
                    mainFragments.put(2,orderFragment);
                }
                else if (tag == 3) {
                    mineFragment = new MainMineFragment();		//“我的”选项卡

                    fragmentTransaction.add(R.id.fragmentLayout, mineFragment, "mine");
                    mainFragments.put(3, mineFragment);
                }
            }
            for(int key : mainFragments.keySet()) {
                if (key == tag) {
                    fragmentTransaction.show(mainFragments.get(key));

                }
                else {
                    fragmentTransaction.hide(mainFragments.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            fragmentTransaction.commit();
        }
    }
}
