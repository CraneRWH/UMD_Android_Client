package com.ymd.client.model.bean.user;

import java.util.List;

/**
 * 会员评论
 */
public class YmdEvaluation  {

    private Long id;
    // bigint(20)  订单id,

    /**
     * 订单id
     */
    private Long dealId;

    //timestamp 评价时间,
    private String time;

    //评分,
    private Integer score;

    // varchar(255) DEFAULT NULL COMMENT 评价内容,
    private String content;

    // varchar(255) DEFAULT NULL COMMENT 商家回复,
    private String reply;

    // varchar(255) DEFAULT NULL COMMENT 追加评价内容,
    private String additionalContent;

    // varchar(255) DEFAULT NULL COMMENT 追加商家回复,
    private String additionalReply;

    public String getAdditionalContent() {
        return additionalContent;
    }

    public void setAdditionalContent(String additionalContent) {
        this.additionalContent = additionalContent;
    }

    public String getAdditionalReply() {
        return additionalReply;
    }

    public void setAdditionalReply(String additionalReply) {
        this.additionalReply = additionalReply;
    }

    //评价图片
    private List<String> fileList;

    //评价用户昵称
    private String userName;

    //评价用户头像
    private String userUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
