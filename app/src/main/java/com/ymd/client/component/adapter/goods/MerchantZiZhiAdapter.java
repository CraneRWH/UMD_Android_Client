package com.ymd.client.component.adapter.goods;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ymd.client.R;
import com.ymd.client.common.base.OnUMDItemClickListener;
import com.ymd.client.component.activity.homePage.merchant.ComplaintSellerActivity;
import com.ymd.client.model.bean.homePage.MerchantInfoEntity;
import com.ymd.client.utils.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者:rongweihe
 * 日期:2018/8/24  时间:22:52
 * 描述:
 * 修改历史:
 */
public class MerchantZiZhiAdapter extends RecyclerView.Adapter<MerchantZiZhiAdapter.ViewHolder> {


    MerchantInfoEntity merchantInfo;
    String manageStr;
    String shopStr;
    private Activity mContext;

    private OnUMDItemClickListener listener;

    public MerchantZiZhiAdapter(MerchantInfoEntity datas, String m, String s,Activity mContext) {
        this.merchantInfo = datas;
        this.manageStr = m;
        this.shopStr = s;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_zizhi, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            setServiceData(holder);
            setManageData(manageStr,holder);
            setShopData(shopStr,holder);

            holder.shapWorkTimeTv.setText(ToolUtil.changeString(merchantInfo.getStartBusinessTime()) + "-" + ToolUtil.changeString(merchantInfo.getEndBusinessTime()));
            holder.main_lt.setVisibility(View.VISIBLE);
        } else {
            holder.main_lt.setVisibility(View.GONE);
        }
        holder.complaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplaintSellerActivity.startAction(mContext, merchantInfo);
            }
        });

    }

    private void setServiceData(ViewHolder holder) {
        List<Map<String ,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name","商家服务");
        map.put("icon", R.mipmap.icon_merchant_serve);
        list.add(map);

        if (merchantInfo.getInvoice() == 1) {
            map = new HashMap<>();
            map.put("name", "提供发票");
            map.put("icon", R.mipmap.icon_merchant_ticket);
            list.add(map);
        }

        map = new HashMap<>();
        map.put("name","到店自取");
        map.put("icon", R.mipmap.icon_merchant_fetch);
        list.add(map);

        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_seller_shop_service , holder.shopSLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.icon_iv);
            //实例化TextView控件
            TextView tv= (TextView) view.findViewById(R.id.item_tv);
            //将int数组中的数据放到ImageView中
            img.setImageResource(ToolUtil.changeInteger(list.get(i).get("icon")));
            //给TextView添加文字
            tv.setText(ToolUtil.changeString(list.get(i).get("name")));
            //把行布局放到linear里
            holder.shopSLayout.addView(view);
        }
    }

    private void setShopData(String resultJson,ViewHolder holder) {
        List<String> list = new Gson().fromJson(resultJson, new TypeToken<List<String>>(){}.getType());


        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_shop_picture , holder.shopLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.item_iv);
            //实例化TextView控件
            //   TextView tv= (TextView) view.findViewById(R.id.textView);
            //将int数组中的数据放到ImageView中
            Glide.with(mContext).load(ToolUtil.changeString(list.get(i))).into(img);
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            holder.shopLayout.addView(view);
        }
    }

    private void setManageData(String resultJson,ViewHolder holder) {
        List<String> list = new Gson().fromJson(resultJson, new TypeToken<List<String>>(){}.getType());

        //开始添加数据
        for(int i=0; i<list.size(); i++){
            //寻找行布局，第一个参数为行布局ID，第二个参数为这个行布局需要放到那个容器上
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_shop_picture , holder.manageLayout,false);
            //通过View寻找ID实例化控件
            ImageView img= (ImageView) view.findViewById(R.id.item_iv);
            //实例化TextView控件
            //   TextView tv= (TextView) view.findViewById(R.id.textView);
            //将int数组中的数据放到ImageView中
            Glide.with(mContext).load(ToolUtil.changeString(list.get(i))).into(img);
            //给TextView添加文字
            //    tv.setText("第"+(x+1)+"张");
            //把行布局放到linear里
            holder.manageLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public OnUMDItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnUMDItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        @BindView(R.id.shopLayout)
        LinearLayout shopLayout;
        @BindView(R.id.manageLayout)
        LinearLayout manageLayout;
        @BindView(R.id.shopSLayout)
        LinearLayout shopSLayout;
        @BindView(R.id.shap_work_time_tv)
        TextView shapWorkTimeTv;
        @BindView(R.id.complaint_btn)
        Button complaintBtn;
        @BindView(R.id.main_lt)
        LinearLayout main_lt;

        ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }
}
