package com.ymd.client.component.activity.order.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;
import com.ymd.client.R;
import com.ymd.client.common.base.BaseActivity;
import com.ymd.client.component.activity.main.MainActivity;
import com.ymd.client.component.activity.order.u_order.UOrderPayResultActivity;
import com.ymd.client.component.event.UEvent;
import com.ymd.client.component.widget.dialog.LoadingDialog;
import com.ymd.client.component.widget.other.MyChooseItemView;
import com.ymd.client.model.bean.order.OrderResultForm;
import com.ymd.client.model.bean.order.YmdOrderGoods;
import com.ymd.client.utils.AlertUtil;
import com.ymd.client.utils.DialogUtil;
import com.ymd.client.utils.QRCodeUtil;
import com.ymd.client.utils.ToolUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/29
 * 描述:    “订单支付结果界面”
 * 修改历史:
 */
public class OrderPayResultActivity extends BaseActivity {

    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.pay_result_tv)
    TextView payResultTv;
    @BindView(R.id.qr_code_iv)
    ImageView qrCodeIv;
    @BindView(R.id.food_code_mcv)
    MyChooseItemView foodCodeMcv;
    @BindView(R.id.phone_iv)
    ImageView phoneIv;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.food_list_lt)
    LinearLayout foodListLt;
    @BindView(R.id.to_main_iv)
    ImageView toMainIv;
/*    @BindView(R.id.more_pay_type_lt)
    LinearLayout morePayTypeLt;
    @BindView(R.id.remark_tv)
    TextView remarkTv;
    @BindView(R.id.order_code_tv)
    TextView orderCodeTv;
    @BindView(R.id.order_time_tv)
    TextView orderTimeTv;
    @BindView(R.id.order_date_tv)
    TextView orderDateTv;
    @BindView(R.id.order_u_tv)
    TextView orderUTv;
    @BindView(R.id.u_dis_tv)
    TextView uDisTv;
    @BindView(R.id.order_all_money_tv)
    TextView orderAllMoneyTv;*/

    private OrderResultForm orderDetail;

    /**
     * 启动
     *
     * @param context
     */
    public static void startAction(Activity context, OrderResultForm orderResultForm) {
        Intent intent = new Intent(context, OrderPayResultActivity.class);
        intent.putExtra("order", orderResultForm);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_result);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (getIntent() != null) {
            orderDetail = (OrderResultForm) getIntent().getExtras().getSerializable("order");
            EventBus.getDefault().post(new UEvent(true));
            setShopData();
        } else {
            finish();
        }
        toMainIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.startAction(OrderPayResultActivity.this);
            }
        });
    }

    private void setShopData() {

        shopNameTv.setText(ToolUtil.changeString(orderDetail.getmName()));
       /* orderCodeTv.setText(ToolUtil.changeString(orderDetail.getOrderNo()));
        orderTimeTv.setText(ToolUtil.changeString(orderDetail.getCreateTime()));
        orderAllMoneyTv.setText(ToolUtil.changeString(orderDetail.getTotalAmt()));
        orderUTv.setText(ToolUtil.changeString(orderDetail.getuCurrency()));
        uDisTv.setText(ToolUtil.changeString(orderDetail.getuObtain()));*/
        orderMoneyTv.setText(ToolUtil.changeString(orderDetail.getPayAmt()));
        //    remarkTv.setText(ToolUtil.changeString(orderDetail.getRemarks()));
        createQRcode();
        /*List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","麻辣烫");
        map.put("icon", R.mipmap.icon_merchant_image_order_1);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);

        map = new HashMap<>();
        map.put("name","蒸饺");
        map.put("icon", R.mipmap.icon_merchant_image_shang);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);

        map = new HashMap<>();
        map.put("name","面条");
        map.put("icon", R.mipmap.icon_merchant_star_image_comment);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);

        map = new HashMap<>();
        map.put("name","炒饼");
        map.put("icon", R.mipmap.icon_merchant_mage);
        map.put("num", 2);
        map.put("price",12);
        list.add(map);*/

        //开始添加数据
        for (int i = 0; i < orderDetail.getYmdOrderGoodsList().size(); i++) {
            YmdOrderGoods item = orderDetail.getYmdOrderGoodsList().get(i);
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view = LayoutInflater.from(this).inflate(R.layout.item_fragment_order_food, foodListLt, false);
            TextView nameTv = (TextView) view.findViewById(R.id.food_name_tv);
            TextView numTv = (TextView) view.findViewById(R.id.food_num_tv);
            TextView priceTv = (TextView) view.findViewById(R.id.food_price_tv);
            ImageView iconIv = (ImageView) view.findViewById(R.id.food_icon_iv);
            if (TextUtils.isEmpty(item.getGoodsIcon())) {
                Glide.with(this).load(ToolUtil.changeString(item.getGoodsIcon())).into(iconIv);
            }
            nameTv.setText(ToolUtil.changeString(item.getGoodsName()));
            numTv.setText("x" + ToolUtil.changeString(item.getGoodsNum()));
            priceTv.setText(ToolUtil.changeString(item.getGoodsAmt()));
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            foodListLt.addView(view);
        }
    }

    private String urlStr;
    private Bitmap qrCodeBitmap;

    private void createQRcode() {
        final String filePath = getFileRoot(this)
                + File.separator + "qr_" + System.currentTimeMillis()
                + ".jpg";
        showLoadingDialog(this);
        // 二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success;
                try {
                    success = QRCodeUtil
                            .createQRImage(ToolUtil.changeString(orderDetail.getId()), 800, 800, null,
                                    filePath);
                    if (success) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                qrCodeBitmap = BitmapFactory
                                        .decodeFile(filePath);
                                qrCodeIv.setImageBitmap(qrCodeBitmap);
                                dialogHandler.sendEmptyMessage(0);
                            }
                        });
                    }
                } catch (WriterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private Handler dialogHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dismissLoadingDialog();
        }
    };

    // 文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "jdic");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            //       ToolUtil.ToastMessage("下载成功", ToolUtil.RIGHT);
            AlertUtil.RightDialog(this, "下载成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    }

    /**
     * 显示进度条对话框
     */
    private int dialogShow = 0;
    private LoadingDialog mLoadingDialog = null;

    public void showLoadingDialog(Context context) {
        dialogShow++;
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.showProgrssDialog(context);
            mLoadingDialog.setMessage("数据加载中...");
            mLoadingDialog.setCancelable(false);
            //    mLoadingDialog.setTitleText("数据加载中...");
        }
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        try {
            if (dialogShow <= 1) {
                dialogShow = 0;
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                mLoadingDialog = null;
            } else {
                dialogShow--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
