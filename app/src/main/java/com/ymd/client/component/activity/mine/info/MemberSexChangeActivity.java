package com.ymd.client.component.activity.mine.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 会员性别选择
 *
 * @author 荣维鹤
 */
public class MemberSexChangeActivity extends BaseActivity {
    @BindView(R.id.listView)
    ListView listView;

    private int flag = -1;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, MemberSexChangeActivity.class);
        context.startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.member_sex_change_activity);
        ButterKnife.bind(this);
        receiveData();
        bindWidgetListener();
    }

    @Override
    protected void receiveData() {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                flag = bundle.getInt("sex");
            }

            ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sex", "男");
            list.add(map);
            map = new HashMap<String, Object>();
            map.put("sex", "女");
            list.add(map);
            MySimpleAdapter adapter = new MySimpleAdapter(this, list,
                    R.layout.list_item_choose_right, new String[]{"sex"},
                    new int[]{R.id.textItem}, new MySimpleAdapter.MyViewListener() {

                @Override
                public void callBackViewListener(
                        Map<String, Object> data, View view,
                        ViewGroup parent, int position) {
                    if (position == flag) {
                        view.findViewById(R.id.itemImage)
                                .setVisibility(View.VISIBLE);
                    } else {
                        view.findViewById(R.id.itemImage)
                                .setVisibility(View.GONE);
                    }
                }
            });
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    flag = position;
                    ((MySimpleAdapter) listView.getAdapter())
                            .notifyDataSetChanged();
                }
            });
    }


    protected void bindWidgetListener() {
        setTitle("修改性别");
        setRightBtn("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });

    }

    protected void saveInfo() {
        if (flag >= 0) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent();
            bundle.putString("key", "sex");
            bundle.putString("value", ToolUtil.changeString(flag));
            intent.putExtras(bundle);
            setResult(1, intent);
            finish();
        } else {
            // ToolUtil.ToastMessage("请选择性别", ToolUtil.WRONG);
            ToastUtil.ToastMessage(this, "请选择性别");
        }
    }

}
