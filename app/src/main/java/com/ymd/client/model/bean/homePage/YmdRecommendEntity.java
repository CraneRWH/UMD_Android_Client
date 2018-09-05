package com.ymd.client.model.bean.homePage;


import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 今日推荐
 * </p>
 *
 * @author hantw
 * @since 2018-08-25
 */
public class YmdRecommendEntity  {

    private static final long serialVersionUID = 1L;

    /**
     * 商品图片
     */
	private String photo;
    /**
     * 商品id
     */
	private Long goodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
    /**
     * 商户ID
     */
	private Long merchantId;
	/**
	 * 商户名称
	 */
	private String merchantName;
    /**
     * 上线时间
     */
	private String starttime;
    /**
     * 下线时间
     */
	private String endtime;
    /**
     * 广告费用
     */
	private double cost;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Override
	public String toString() {
		return "YmdRecommend{" +
			", photo=" + photo +
			", goodsId=" + goodsId +
			", merchantId=" + merchantId +
			", starttime=" + starttime +
			", endtime=" + endtime +
			", cost=" + cost +
			"}";
	}
}
