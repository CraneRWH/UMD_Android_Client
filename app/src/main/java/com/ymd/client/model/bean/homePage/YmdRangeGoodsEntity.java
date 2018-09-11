package com.ymd.client.model.bean.homePage;

import java.io.Serializable;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author hantw
 * @since 2018-08-21
 */
public class YmdRangeGoodsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
     * 商品分类名称
     */
	private String variety;
    /**
     * 商品展示权重
     */
	private Integer weight;

	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean choose) {
		isChoose = choose;
	}

	/**
     * 商户主键
     */

	private String merchantId;
	private Integer status;

	private boolean isChoose;



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	@Override
	public String toString() {
		return "YmdRangeGoodsEntity{" +
			", variety=" + variety +
			", weight=" + weight +
			", merchantId=" + merchantId +
			"}";
	}
}
