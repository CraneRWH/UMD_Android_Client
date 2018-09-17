package com.ymd.client.model.bean.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单商品信息表
 * </p>
 *
 * @author hantw
 * @since 2018-09-07
 */
public class YmdOrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long goodsId;
    /**
     * "商品名称"
     */
    private String goodsName;
    /**
     * "商品图片Url"
     */
    private String goodsIcon;
    /**
     * "订单id"
     */
    private Long orderId;
    /**
     * "商品价格"
     */
    private BigDecimal goodsAmt;
    /**
     * "支付价格"
     */
    private BigDecimal payAmt;
    /**
     * "商品数量"
     */
    private Integer goodsNum;
    /**
     * "商品类型 0 正常  1u币专区"
     */
    private String goodsType;
    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getGoodsAmt() {
        return goodsAmt;
    }

    public void setGoodsAmt(BigDecimal goodsAmt) {
        this.goodsAmt = goodsAmt;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

}
