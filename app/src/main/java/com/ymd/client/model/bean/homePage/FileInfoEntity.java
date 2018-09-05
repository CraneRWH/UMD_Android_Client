package com.ymd.client.model.bean.homePage;

public class FileInfoEntity {
    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 附件地址
     */
    private String url;
    /**
     * 附件类别
     */
    private String flag;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
