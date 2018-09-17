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

    private static final long serialVersionUID = 1L;

    private long roomId;

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

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
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
