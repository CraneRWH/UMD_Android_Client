package com.ymd.client.component.activity.mine.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 会员昵称修改
 *
 * @author 荣维鹤
 */
public class NickNameChangeActivity extends BaseActivity {

    @BindView(R.id.memberName)
    EditText memberName;

    /**
     * 启动
     * @param context
     */
    public static void startAction(Activity context) {
        Intent intent = new Intent(context, NickNameChangeActivity.class);
    //    intent.putExtra("nickName", name);
        context.startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.member_name_change_activity);
        ButterKnife.bind(this);
        bindWidgetListener();
    }

    protected void bindWidgetListener() {
        setTitle("修改昵称");
        setRightBtn("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });
    }

    protected void saveInfo() {
        if (ToolUtil.changeString(memberName.getText()).isEmpty())  {
            ToastUtil.ToastMessage(this, "请输入昵称");
        }
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        bundle.putString("key", "NICKNAME");
        bundle.putString("value", ToolUtil.changeString(memberName.getText()));
        intent.putExtras(bundle);
        setResult(1, intent);
        finish();
    }
}