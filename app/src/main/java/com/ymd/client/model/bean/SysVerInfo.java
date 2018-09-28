package com.ymd.client.model.bean;

import java.io.Serializable;

public class SysVerInfo implements Serializable {
    private String id;

    private String verNum;

    private String verCode;

    private String verFlag;

    private String terminalType;

    private String apkUrl;

    private String finishTime;

    private String verDesc;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVerNum() {
        return verNum;
    }

    public void setVerNum(String verNum) {
        this.verNum = verNum == null ? null : verNum.trim();
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode == null ? null : verCode.trim();
    }

    public String getVerFlag() {
        return verFlag;
    }

    public void setVerFlag(String verFlag) {
        this.verFlag = verFlag == null ? null : verFlag.trim();
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType == null ? null : terminalType.trim();
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl == null ? null : apkUrl.trim();
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getVerDesc() {
        return verDesc;
    }

    public void setVerDesc(String verDesc) {
        this.verDesc = verDesc == null ? null : verDesc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", verNum=").append(verNum);
        sb.append(", verCode=").append(verCode);
        sb.append(", verFlag=").append(verFlag);
        sb.append(", terminalType=").append(terminalType);
        sb.append(", apkUrl=").append(apkUrl);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", verDesc=").append(verDesc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}