package com.ymd.client.component.event;

import com.ymd.client.model.bean.order.OrderResultForm;

public class OrderEvent {
    int type ;
    OrderResultForm orderDetail;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public OrderResultForm getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderResultForm orderDetail) {
        this.orderDetail = orderDetail;
    }
}
