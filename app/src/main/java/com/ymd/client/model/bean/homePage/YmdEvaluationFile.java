package com.ymd.client.model.bean.homePage;


/**
 * <p>
 * 评价附件表
 * </p>
 *
 * @author hantw
 * @since 2018-09-04
 */
public class YmdEvaluationFile  {

    private static final long serialVersionUID = 1L;

    /**
     * 评价ID
     */
	private Long evaluationId;
    /**
     * 图片路径
     */
	private String fileUrl;

	public Long getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(Long evaluationId) {
		this.evaluationId = evaluationId;
	}
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	@Override
	public String toString() {
		return "YmdEvaluationFile{" +
			", evaluationId=" + evaluationId +
			", fileUrl=" + fileUrl +
			"}";
	}
}
