package com.ymd.client.model.bean.order;

import java.io.Serializable;
import java.util.List;

public class OrderResultForm implements Serializable {
    //订单id
    private Long id;
    //订单编号
    private String orderNo;
    //商户ID
    private Long mId;
    //商户名称
    private String mName;
    //商户头像
    private String mIcon;
    //用餐时间
    private String eatTime;
    //用餐人数
    private Integer eatNumber;
    //包房大厅  0包房  1大厅
    private String room;
    //包房名称
    private String roomId;
    //是否自取 0自取   1到店吃
    private String himself;
    //订单类型  0美食 1非美食
    private String orderType;
    //订单状态
    private String  orderStatus;
    //备注
    private String remarks;
    //订单总价
    private double totalAmt;
    //折扣
    private double discountAmt;
    //使用U币
    private Integer uCurrency;
    //获得u币
    private Integer uObtain;
    //实付金额
    private double payAmt;

    //商品详情
    private List<YmdOrderGoods> ymdOrderGoodsList;
    private String createTime;
    //地址
    private String address;
    //电话
    private String tel;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public Integer getuCurrency() {
        return uCurrency;
    }

    public void setuCurrency(Integer uCurrency) {
        this.uCurrency = uCurrency;
    }

    public Integer getuObtain() {
        return uObtain;
    }

    public void setuObtain(Integer uObtain) {
        this.uObtain = uObtain;
    }

    public double getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(double payAmt) {
        this.payAmt = payAmt;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getEatTime() {
        return eatTime;
    }

    public void setEatTime(String eatTime) {
        this.eatTime = eatTime;
    }

    public Integer getEatNumber() {
        return eatNumber;
    }

    public void setEatNumber(Integer eatNumber) {
        this.eatNumber = eatNumber;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHimself() {
        return himself;
    }

    public void setHimself(String himself) {
        this.himself = himself;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<YmdOrderGoods> getYmdOrderGoodsList() {
        return ymdOrderGoodsList;
    }

    public void setYmdOrderGoodsList(List<YmdOrderGoods> ymdOrderGoodsList) {
        this.ymdOrderGoodsList = ymdOrderGoodsList;
    }
}
