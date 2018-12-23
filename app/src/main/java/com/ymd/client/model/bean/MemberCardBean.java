package com.ymd.client.model.bean;

import java.io.Serializable;

public class MemberCardBean implements Serializable {

    /**
     * id : 1
     * cardType : 10B
     * originalPrice : 2222
     * discountPrice : 11.0
     * createDate : 2018-12-12 11:08:06
     * createBy : 2
     * updateDate : 2018-12-12 11:10:44
     * updateBy : 2
     * remarks : null
     * delFlag : 0
     */
    private int id;
    private String cardType;//10A月卡 10B季卡 10C年卡
    private String originalPrice;
    private String discountPrice;
    private String createDate;
    private int createBy;
    private String updateDate;
    private int updateBy;
    private String remarks;
    private int delFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
