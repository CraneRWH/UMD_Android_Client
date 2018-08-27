package com.ymd.client.component.widget.lock;


import com.ymd.client.R;

/**
 * @author SimpleZhl
 * @for
 * @since 2016/5/25
 */
public enum GestureStatus {

    //默认的状态，刚开始的时候（初始化状态）
    CREATE_DEFAULT(R.string.create_gesture_default, R.color.grey_a5a5a5),
    //第一次记录成功
    CREATE_CORRECT(R.string.create_gesture_correct, R.color.grey_a5a5a5),
    //连接的点数小于4（二次确认的时候就不再提示连接的点数小于4，而是提示确认错误）
    CREATE_LESSERROR(R.string.create_gesture_less_error, R.color.red),
    //二次确认错误
    CREATE_CONFIRMERROR(R.string.create_gesture_confirm_error, R.color.red),
    //二次确认正确
    CREATE_CONFIRMCORRECT(R.string.create_gesture_confirm_correct, R.color.grey_a5a5a5),


    //默认的状态
    LOGIN_DEFAULT(R.string.gesture_default, R.color.grey_a5a5a5),
    //密码输入错误
    LOGIN_ERROR(R.string.gesture_error, R.color.red),
    //密码输入正确
    LOGIN_CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);

    private GestureStatus(int strId, int colorId) {
        this.strId = strId;
        this.colorId = colorId;
    }

    public int strId;
    public int colorId;

}
