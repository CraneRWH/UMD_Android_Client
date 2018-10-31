package com.ymd.client.model.bean.order;

/**
 * <p>
 * 
 * </p>
 *"商户包间信息"
 * @author hantw
 * @since 2018-09-10
 */

public class YmdMerchantRooms {

    private long id;

    /**
     * 包间名称
     */
	private String roomsName;
    /**
     * 用餐人数
     */
	private String diningNumber;
    /**
     * 商户id
     */
	private Long merchantId;

	private boolean isChoose;

	private int roomsStatus;

	public int getRoomsStatus() {
		return roomsStatus;
	}

	public void setRoomsStatus(int roomsStatus) {
		this.roomsStatus = roomsStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean choose) {
		isChoose = choose;
	}

	public String getRoomsName() {
		return roomsName;
	}

	public void setRoomsName(String roomsName) {
		this.roomsName = roomsName;
	}
	public String getDiningNumber() {
		return diningNumber;
	}

	public void setDiningNumber(String diningNumber) {
		this.diningNumber = diningNumber;
	}
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}


	@Override
	public String toString() {
		return "YmdMerchantRooms{" +
			", roomsName=" + roomsName +
			", diningNumber=" + diningNumber +
			", merchantId=" + merchantId +
			"}";
	}
}
