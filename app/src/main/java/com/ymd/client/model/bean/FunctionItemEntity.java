package com.ymd.client.model.bean;

/**
 * @ClassName
 * @PackageName com.ymd.client.model.bean
 * @CreateTime 2019/1/18 11:13
 * @Author rongweihe
 * @Description
 */
public class FunctionItemEntity {
    private int pid;
    private String functionName;
    private int functionIcon;

    public FunctionItemEntity(int pid, String functionName, int functionIcon) {
        this.pid = pid;
        this.functionName = functionName;
        this.functionIcon = functionIcon;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getFunctionIcon() {
        return functionIcon;
    }

    public void setFunctionIcon(int functionIcon) {
        this.functionIcon = functionIcon;
    }
}
