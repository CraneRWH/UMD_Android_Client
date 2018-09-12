package com.ymd.client.model.bean.homePage;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author hantw
 * @since 2018-09-05
 */
public class YmdGoodsEntity {

    private static final long serialVersionUID = 1L;
	private long id;
    /**
     * 商户id
     */
	private Long merchantId;
    /**
     * 商品分类id
     */
	private Long rangeGoods;
    /**
     * 商品名称
     */
	private String goodsName;
    /**
     * 是否上架(1上架；0下架)
     */
	private String onSale;
    /**
     * 描述
     */
	private String describe;
    /**
     * 价格
     */
	private BigDecimal price;

	private BigDecimal preferentialPrice;

	public BigDecimal getPreferentialPrice() {
		return preferentialPrice;
	}

	public void setPreferentialPrice(BigDecimal preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}

	/**
     * 创建时间
     */
	private String createTime;
    /**
     * 更新时间
     */
	private String updateTime;
    /**
     * 排序(顺序AES)
     */
	private Integer sort;
	//图片url
	private List<String> goodsUrl;
	/**
	 * 月销
	 */
	private String sales;

	private int buyCount;

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public List<String> getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(List<String> goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getRangeGoods() {
		return rangeGoods;
	}

	public void setRangeGoods(Long rangeGoods) {
		this.rangeGoods = rangeGoods;
	}
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getOnSale() {
		return onSale;
	}

	public void setOnSale(String onSale) {
		this.onSale = onSale;
	}
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	@Override
	public String toString() {
		return "YmdGoods{" +
			", merchantId=" + merchantId +
			", rangeGoods=" + rangeGoods +
			", goodsName=" + goodsName +
			", onSale=" + onSale +
			", describe=" + describe +
			", price=" + price +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", sort=" + sort +
			"}";
	}
}
