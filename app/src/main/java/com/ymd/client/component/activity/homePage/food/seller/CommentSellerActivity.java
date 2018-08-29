package com.ymd.client.component.activity.homePage.food.seller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.widget.recyclerView.MyGridView;

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

    private int score = 0;

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
}
