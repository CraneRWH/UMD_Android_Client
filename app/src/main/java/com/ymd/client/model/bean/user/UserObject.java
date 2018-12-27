package com.ymd.client.model.bean.user;

import java.math.BigDecimal;

/**
 * 作者:rongweihe
 * 日期:2018/9/3  时间:20:34
 * 描述:  登录返回的用户信息
 * 修改历史:
 */
public class UserObject {
    private Long id;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别 0男1女
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 头像url
     */
    private String icon;
    /**
     * 密码
     */
    private String password;
    /**
     * 手势密码
     */
    private String gesture;
    /**
     * 手势密码错误次数
     */
    private Integer gestureErrorNumber;
    /**
     * u币数量
     */
    private Integer uNumber;

    /*
     *验证码
     */
    private String code;
    /**
     * shiro加密盐
     */
    private String salt;
    /**
     * 创建时间
     */
    private String time;

    /**
     *score 评价总分数
     */
    private BigDecimal score;


    /**
     *evaluate_num 评价总次数
     */
    private int evaluateNum;

    /**
     *trade_num 交易次数
     */
    private int tradeNum;

    /**
     *sale_num 销售数量
     */
    private int saleNum;

    /**
     * 判断是否是会员,0 非会员 1会员
     */
    private int membership;

    private String endTime;

    /**
     *comprehensiveScore 综合评分
     */
    private int comprehensiveScore;

    /**
     * 判断是否是会员,0 非会员 1会员
     */
    private int membership;

    private String endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture = gesture;
    }

    public Integer getGestureErrorNumber() {
        return gestureErrorNumber;
    }

    public void setGestureErrorNumber(Integer gestureErrorNumber) {
        this.gestureErrorNumber = gestureErrorNumber;
    }

    public Integer getuNumber() {
        return uNumber;
    }

    public void setuNumber(Integer uNumber) {
        this.uNumber = uNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public int getEvaluateNum() {
        return evaluateNum;
    }

    public void setEvaluateNum(int evaluateNum) {
        this.evaluateNum = evaluateNum;
    }

    public int getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(int tradeNum) {
        this.tradeNum = tradeNum;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public int getComprehensiveScore() {
        return comprehensiveScore;
    }

    public void setComprehensiveScore(int comprehensiveScore) {
        this.comprehensiveScore = comprehensiveScore;
    }

    public int getMembership() {
        return membership;
    }

    public void setMembership(int membership) {
        this.membership = membership;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
