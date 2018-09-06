package com.ymd.client.model.bean.homePage;

/**
 * <p>
 * 行业信息表
 * </p>
 *
 * @author hantw
 * @since 2018-08-22
 */
public class YmdIndustryEntity  {

    private static final long serialVersionUID = 1L;

    /**
     * 行业名称
     */
	private String name;
    /**
     * 展示图片
     */
	private String imgUrl;

	private String pid;

	/**
	 * pid
	 * @return
	 */
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	@Override
	public String toString() {
		return "YmdIndustry{" +
			", name=" + name +
			", imgUrl=" + imgUrl +
			"}";
	}
}
