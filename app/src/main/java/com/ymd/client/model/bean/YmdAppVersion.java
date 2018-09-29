package com.ymd.client.model.bean;

/**
 * <p>
 * app版本管理
 * </p>
 *
 * @author hantw
 * @since 2018-08-23
 */
public class YmdAppVersion {

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * app类型
	 */
	private String appType;
	/**
	 * 版本号
	 */
	private int versionNo;
	/**
	 * 版本名称
	 */
	private String versionName;
	/**
	 * 版本描述
	 */
	private String versionDesc;
	/**
	 * 是否强制升级
	 */
	private String upgrade;
	/**
	 * 下载地址
	 */
	private String downloadUrl;
	/**
	 * 用户端型
	 */
	private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	public String getUpgrade() {
		return upgrade;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionName() {
		return versionName;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public String getVersionDesc() {
		return versionDesc;
	}


	public void setUpgrade(String upgrade) {
		this.upgrade = upgrade;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}


	@Override
	public String toString() {
		return "YmdAppVersion{" +
				", appType=" + appType +
				", versionNo=" + versionNo +
				", upgrade=" + upgrade +
				", downloadUrl=" + downloadUrl +
				"}";
	}
}
