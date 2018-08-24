package com.ymd.client.component.activity.order;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ymd.client.R;
import com.ymd.client.common.base.fragment.PageFragment;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.utils.ToolUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.ymd.client.component.activity.order
 * 类名:
 * 时间:2018/8/24 0024Time:11:18
 * 作者:荣维鹤
 * 功能简介:
 * 修改历史:
 */
public class OrderPageFragment extends PageFragment {

    @Override
    protected String getMethod() {
        return "";
    }

    @Override
    protected Map<String, String> getParams() {
        return null;
    }

    @Override
    protected int[] getItemLayouts() {
        return new int[]{
            R.layout.item_fragment_order_page,
                R.layout.item_fragment_order_page,
                R.layout.item_fragment_order_page
        };
    }

    @Override
    protected String[] getEmptyAlarts() {
        return new String[]{
                "无",
                "无",
                "无"
        };
    }

    @Override
    protected String getDataKey() {
        return "";
    }

    @Override
    protected String[] getFrom() {
        return new String[]{
                "name",
                "statusName",
        //        "product_list",
                "u_money",
                "all_num",
                "money"
        };
    }

    @Override
    protected int[] getTo() {
        return new int[]{
                R.id.name_tv,
                R.id.status_name_tv,
        //        R.id.product_list_lt,
                R.id.u_money_tv,
                R.id.all_product_num_tv,
                R.id.pay_money_tv
        };
    }

    @Override
    protected List<Map<String, Object>> getDataList() {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> productList = new ArrayList<>();
        Map<String,Object> product = new HashMap<>();
        product.put("name", "麻辣烫");
        product.put("num", 2);
        productList.add(product);
        map.put("name","麻辣烫");
        map.put("statusName", "订单已完成");
        map.put("status",3);
        map.put("u_money", 20);
        map.put("product_list", productList);
        map.put("all_num", 2);
        map.put("money", 30);
        list.add(map);

        map = new HashMap<>();
        productList = new ArrayList<>();
        product = new HashMap<>();
        product.put("name", "麻辣烫");
        product.put("num", 2);
        productList.add(product);
        product.put("name", "麻辣烫");
        product.put("num", 2);
        productList.add(product);
        map.put("name","朝鲜面");
        map.put("statusName", "订单已提交");
        map.put("status",1);
        map.put("u_money", 20);
        map.put("product_list", productList);
        map.put("all_num", 4);
        map.put("money", 30);
        list.add(map);
        return list;
    }

    @Override
    protected void setFormat() {
        getAdapter().setViewListener(new MySimpleAdapter.MyViewListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void callBackViewListener(Map<String, Object> data, View view, ViewGroup parent, int position) {
                if (ToolUtil.changeInteger(data.get("status")) >2) {
                    ((TextView)view.findViewById(R.id.status_name_tv)).setTextColor(getActivity().getColor(R.color.text_gray_dark));
                    view.findViewById(R.id.btn1).setVisibility(View.VISIBLE);
                } else {
                    ((TextView)view.findViewById(R.id.status_name_tv)).setTextColor(getActivity().getColor(R.color.bg_header));
                    view.findViewById(R.id.btn1).setVisibility(View.GONE);
                }
                List<Map<String,Object>> products = (List<Map<String, Object>>) data.get("product_list");
                LinearLayout linearLayout = view.findViewById(R.id.product_list_lt);
                linearLayout.removeAllViews();
                for (Map<String,Object> item : products) {
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_order_page_product, null);
                    TextView nameView = v.findViewById(R.id.product_name_tv);
                    TextView numView = v.findViewById(R.id.product_num_tv);
                    nameView.setText(ToolUtil.changeString(item.get("name")));
                    numView.setText("x" + item.get("num"));
                    linearLayout.addView(view);
                }

            }
        });
    }
}
