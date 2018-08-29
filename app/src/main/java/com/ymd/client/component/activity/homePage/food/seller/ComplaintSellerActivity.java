package com.ymd.client.component.activity.homePage.food.seller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.widget.recyclerView.MyGridView;
import com.ymd.client.utils.ToolUtil;

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
    MyGridView pictureGv;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, ComplaintSellerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_seller);
        ButterKnife.bind(this);
        initView();
        setComplaintTypeData();
    }

    private void initView() {
        List<Map<String,Object>> pictures = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("icon", R.mipmap.icon_comment_star_camera);
        pictures.add(map);

        map = new HashMap<>();
        map.put("icon", R.mipmap.icon_comment_star_camera);
        pictures.add(map);

        map = new HashMap<>();
        map.put("icon", R.mipmap.icon_comment_star_camera);
        pictures.add(map);
        MySimpleAdapter adapter = new MySimpleAdapter(this,pictures, R.layout.item_grid_picture, new String[]{"icon"},new int[]{R.id.icon_iv});
        pictureGv.setAdapter(adapter);
    }
    List<Map<String, Object>> complaintTypeList = new ArrayList<>();
    private void setComplaintTypeData() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "商家服务");
        map.put("isChoose", true);
        complaintTypeList.add(map);

        map = new HashMap<>();
        map.put("name", "提供发票");
        map.put("isChoose", true);
        complaintTypeList.add(map);

        map = new HashMap<>();
        map.put("name", "到店自取");
        map.put("isChoose", false);
        complaintTypeList.add(map);


        map = new HashMap<>();
        map.put("name", "到店自取");
        map.put("isChoose", false);
        complaintTypeList.add(map);


        map = new HashMap<>();
        map.put("name", "到店自取");
        map.put("isChoose", false);
        complaintTypeList.add(map);

        //开始添加数据
        for (int i = 0; i < complaintTypeList.size(); i++) {
            int position = i;
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(this).inflate(R.layout.item_seller_complaaint_type, complaintLayout, false);
            //实例化TextView控件
            TextView tv = (TextView) view.findViewById(R.id.item_tv);
            //给TextView添加文字
            tv.setText(ToolUtil.changeString(complaintTypeList.get(i).get("name")));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    complaintTypeList.get(position).put("isChoose", !ToolUtil.changeBoolean(complaintTypeList.get(position).get("isChoose")));
                    refreshData(tv, position);
                }
            });
            refreshData(tv,i);
            //把行布局放到linear里
            complaintLayout.addView(view);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void refreshData(TextView tv, int position) {

        if (ToolUtil.changeBoolean(complaintTypeList.get(position).get("isChoose"))) {
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundResource(R.drawable.shape_rect_corner_green);
        } else {
            tv.setTextColor(R.color.text_gray_dark);
            tv.setBackgroundResource(R.drawable.shape_rect_corner_gray_edge);
        }
    }
}
