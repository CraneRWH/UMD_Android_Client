package com.ymd.client.common.net.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhl
 * @class com.ymaidan.client.common.net
 * @time 2018/6/27 0027 12:58
 * @description 返回的错误码
 */
public class ResponseCode {

    public final static String SUCCESS = "00";
    private volatile static ResponseCode instance;

    private ResponseCode() {
    }

    public static synchronized ResponseCode getInstance() {
        if (instance == null) {
            synchronized (ResponseCode.class) {
                instance = new ResponseCode();
            }
        }
        return instance;
    }


    public Map<String, String> initResponseCode() {
        Map<String, String> responseCodeMap = new HashMap<>();

        responseCodeMap.put("00", "交易成功");
        responseCodeMap.put("01", "请持卡人与发卡银行联系");
        responseCodeMap.put("03", "无效商户");
        responseCodeMap.put("04", "此卡被没收");
        responseCodeMap.put("05", "持卡人认证失败");
        responseCodeMap.put("06", "商户未绑定二维码");
        responseCodeMap.put("07", "二维码获取失败");
        responseCodeMap.put("08", "初始化");
        responseCodeMap.put("09", "支付失败");
        responseCodeMap.put("10", "显示部分批准金额，提示操作员");
        responseCodeMap.put("11", "成功，VIP客户");
        responseCodeMap.put("12", "无效交易");
        responseCodeMap.put("13", "无效金额");
        responseCodeMap.put("14", "无效卡号");
        responseCodeMap.put("15", "此卡无对应发卡方");
        responseCodeMap.put("16", "信用卡已认证");
        responseCodeMap.put("17", "请使用信用卡申请认证");
        responseCodeMap.put("18", "四要素认证失败");
        responseCodeMap.put("21", "该卡未初始化或睡眠卡");
        responseCodeMap.put("22", "操作有误，或超出交易允许天数");
        responseCodeMap.put("25", "没有原始交易，请联系发卡方");
        responseCodeMap.put("30", "请重试");
        responseCodeMap.put("34", "作弊卡,呑卡");
        responseCodeMap.put("38", "密码错误次数超限，请与发卡方联系");
        responseCodeMap.put("40", "发卡方不支持的交易类型");
        responseCodeMap.put("41", "挂失卡，请没收");
        responseCodeMap.put("43", "被窃卡，请没收");
        responseCodeMap.put("45", "芯片卡交易，请插卡操作");
        responseCodeMap.put("51", "可用余额不足");
        responseCodeMap.put("54", "该卡已过期");
        responseCodeMap.put("55", "密码错误");
        responseCodeMap.put("57", "不允许此卡交易");
        responseCodeMap.put("58", "发卡方不允许该卡在本终端进行此交易");
        responseCodeMap.put("59", "卡片校验错");
        responseCodeMap.put("61", "交易金额超限");
        responseCodeMap.put("62", "受限制的卡");
        responseCodeMap.put("64", "交易金额与原交易不匹配");
        responseCodeMap.put("65", "超出消费次数限制");
        responseCodeMap.put("68", "交易超时，请重试");
        responseCodeMap.put("75", "密码错误次数超限");
        responseCodeMap.put("90", "系统日切，请稍后重试");
        responseCodeMap.put("91", "发卡方状态不正常，请稍后重试");
        responseCodeMap.put("92", "发卡方线路异常，请稍后重试");
        responseCodeMap.put("94", "拒绝，重复交易，请稍后重试");
        responseCodeMap.put("96", "拒绝，交换中心异常，请稍后重试");
        responseCodeMap.put("97", "终端未登记");
        responseCodeMap.put("98", "发卡方超时");
        responseCodeMap.put("99", "PIN格式错，请重新签到");
        responseCodeMap.put("A0", "MAC校验错，请重新签到");
        responseCodeMap.put("A1", "转账货币不一致");
        responseCodeMap.put("A2", "交易成功，请向发卡行确认");
        responseCodeMap.put("A3", "账户不正确");
        responseCodeMap.put("A4", "交易成功，请向发卡行确认");
        responseCodeMap.put("A5", "交易成功，请向发卡行确认");
        responseCodeMap.put("A6", "交易成功，请向发卡行确认");
        responseCodeMap.put("A7", "拒绝，交换中心异常，请稍后重试");
        responseCodeMap.put("B1", "不支持该终端");
        responseCodeMap.put("F0", "拒绝，终端初始化失败");
        responseCodeMap.put("W1", "不允许提现");
        responseCodeMap.put("W2", "当前时间不允许提现");
        responseCodeMap.put("W3", "节假日不允许提现");
        responseCodeMap.put("W4", "提现受理失败，小于提现金额");
        responseCodeMap.put("W5", "提现受理失败，超出提现次数");
        responseCodeMap.put("W6", "提现失败，请认证信用卡");
        responseCodeMap.put("W7", "小于终端日限额");
        responseCodeMap.put("W8", "商户资料审核不通过，请重新提交");
        responseCodeMap.put("W9", "提现失败，提现过于频繁");
        responseCodeMap.put("W10", "代理商不存在");
        responseCodeMap.put("W11", "超出交易限额或交易次数");
        responseCodeMap.put("W15", "身份信息已使用");
        responseCodeMap.put("WSM", "请先进入商户信息进行实名认证");
        responseCodeMap.put("ZC", "验证码错误");
        responseCodeMap.put("ZD", "手机号码已注册，请直接登录");
        responseCodeMap.put("ZE", "银行卡实名验证失败");
        responseCodeMap.put("ZV", "请更新到最新版本");
        responseCodeMap.put("ZZ", "操作失败");
        responseCodeMap.put("ZZ0", "版本号为空");
        responseCodeMap.put("ZZ1", "短信验证操作异常");
        responseCodeMap.put("ZZ2", "商户不存在");
        responseCodeMap.put("ZZ3", "验证码为空");
        responseCodeMap.put("ZZ4", "资质上传失败，请重新上传");
        responseCodeMap.put("ZZ5", "签名上传失败，请重新上传");
        responseCodeMap.put("ZZ6", "提现操作异常");
        responseCodeMap.put("ZZ7", "刷卡头检测及绑定操作异常");
        responseCodeMap.put("ZZ8", "实名认证未通过，请核实您的信息后重试");
        responseCodeMap.put("ZZ9", "商户同步操作异常，请重试");
        responseCodeMap.put("ZZ10", "保存APP提额商户异常");
        responseCodeMap.put("ZZ11", "查询APP提额商户异常");
        responseCodeMap.put("ZZ12", "APP提额商户资质上传异常");
        responseCodeMap.put("ZZ13", "APP提额商户已存在");
        responseCodeMap.put("ZZ14", "终端已绑定其它用户，选择正确的用户信息登录");
        responseCodeMap.put("ZZ15", "删除APP提额商户异常");
        responseCodeMap.put("ZZ16", "资质图片不能为空");
        responseCodeMap.put("ZZ17", "获取商户编号异常");
        responseCodeMap.put("ZZ18", "请先刷卡");
        responseCodeMap.put("ZZ19", "您的交易金额小于最低单笔限额");
        responseCodeMap.put("ZZ30", "推荐人手机号与注册手机号相同");
        responseCodeMap.put("ZZ31", "推荐人手机号不存在");
        responseCodeMap.put("T1", "交易失败，正在重新审核");
        responseCodeMap.put("S5", "APP超过交易次数");
        responseCodeMap.put("N1", "提现金额不能为0");
        responseCodeMap.put("N2", "一天只能提现一次");
        responseCodeMap.put("N3", "不允许提现请联系运营人员");
        responseCodeMap.put("N4", "系统配置错误");
        responseCodeMap.put("N5", "可提现金额不足");


        responseCodeMap.put("000000", "处理成功");
        responseCodeMap.put("200000", "MAC校验错误");
        responseCodeMap.put("200001", "版本号不存在");
        responseCodeMap.put("200002", "会员名或者密码不正确");
        responseCodeMap.put("200003", "服务异常");
        responseCodeMap.put("200004", "会员已存在");
        responseCodeMap.put("200005", "验证码超时");
        responseCodeMap.put("200006", "会员不存在");
        responseCodeMap.put("200007", "昵称已存在");
        responseCodeMap.put("200008", "手机号不能为空");
        responseCodeMap.put("200009", "手机号格式错误");
        responseCodeMap.put("200010", "身份证号格式错误");
        responseCodeMap.put("200011", "原始密码错误");
        responseCodeMap.put("200012", "未实名认证");
        responseCodeMap.put("200013", "投标错误信息");
        responseCodeMap.put("200014", "已实名认证");
        responseCodeMap.put("200015", "推荐人不能为自己");
        responseCodeMap.put("200016", "存库不足");
        responseCodeMap.put("200017", "已签到");
        responseCodeMap.put("200018", "已收藏");
        responseCodeMap.put("200019", "金币不足");
        responseCodeMap.put("200020", "订单不存在");
        responseCodeMap.put("200021", "订单支付超时");
        responseCodeMap.put("200022", "订单发货超时");
        responseCodeMap.put("200023", "推荐人不存在");
        responseCodeMap.put("200024", "出价太低");
        responseCodeMap.put("200025", "一口价低于领先价");
        responseCodeMap.put("200026", "商品竞拍中，不能下架");
        responseCodeMap.put("200027", "三级分销商品不能竞拍");
        responseCodeMap.put("200028", "竞拍已结束");
        responseCodeMap.put("200029", "提现超过可提余额");
        responseCodeMap.put("200030", "用户昵称已存在");
        responseCodeMap.put("200031", "包含特殊字符");
        responseCodeMap.put("200032", "暂无提现记录");
        responseCodeMap.put("200033", "验证码输入有误");
        responseCodeMap.put("200034", "支付密码输入有误");
        responseCodeMap.put("200035", "账户余额不足");
        responseCodeMap.put("200036", "企业资质图片不全");
        responseCodeMap.put("200037", "暂无该店铺");
        responseCodeMap.put("200038", "没有昵称和头像不能发布动态");
        responseCodeMap.put("200039", "每月只能修改两次");
        responseCodeMap.put("200040", "优惠卷只能在一个店铺使用");
        responseCodeMap.put("200041", "不符合满减规则");
        responseCodeMap.put("200042", "不符合退款条件");
        responseCodeMap.put("200043", "已换购");
        responseCodeMap.put("200044", "已失效");
        responseCodeMap.put("200045", "已秒杀");
        responseCodeMap.put("200046", "已申请作品");
        responseCodeMap.put("200047", "手机号已存在");
        responseCodeMap.put("200048", "邮箱已存在");
        responseCodeMap.put("200049", "已投过两票");
        responseCodeMap.put("200050", "时间到期");
        responseCodeMap.put("200051", "征集中");
        responseCodeMap.put("200052", "推荐人手机号是领队或者实习领队");
        responseCodeMap.put("200053", "多次购买大礼包");
        responseCodeMap.put("200060", "积分不足");

        return responseCodeMap;
    }
}
