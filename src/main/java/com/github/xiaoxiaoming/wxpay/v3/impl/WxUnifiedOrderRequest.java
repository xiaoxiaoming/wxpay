package com.github.xiaoxiaoming.wxpay.v3.impl;

import com.github.xiaoxiaoming.wxpay.v3.WxPayRequest;

public class WxUnifiedOrderRequest extends WxPayRequest {

    public static final String URL="https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * for测试
     */
    private String auth_code;
    /**
     * 微信商品支付密钥
     */
    private String key;


    /**
     * 微信分配的公众账号 ID, 必选
     */
    private String appid;

    /**
     * 微信支付分配的商户号,必选
     */
    private String mch_id;

    /**
     * 微信支付分配的终端设备号,可选
     */
    private String device_info;

    /**
     * 随机字符串，不长于 32 位，必选
     */
    private String nonce_str;

    /**
     * 商品描述，必选
     */
    private String body;

    /**
     * 附加数据，可选
     */
    private  String attach;

    /**
     * 商户系统内部的订单号,32
     个字符内、可包含字母,确保
     在商户系统唯一,必选
     */
    private String   out_trade_no;

    /**
     * 订单总金额，单位为分，不
     能带小数点,必选
     */
    private String total_fee;

    /**
     * 订单生成的机器 IP,必选
     */
    private String spbill_create_ip;

    /**
     * 订 单 生 成 时 间 ， 格 式 为
     yyyyMMddHHmmss，如 2009 年
     12 月25日 9点 10分 10 秒表
     示为 20091225091010。时区
     为 GMT+8 beijing。该时间取
     自商户服务器,可选
     */
    private String time_start;
    /**
     * 订 单 失 效 时 间 ， 格 式 为
     yyyyMMddHHmmss，如 2009 年
     12 月27日 9点 10分 10 秒表
     示为 20091227091010。时区
     为 GMT+8 beijing。该时间取
     自商户服务器 可选
     */
    private String time_expire;

    /**
     * 接收微信支付成功通知 必选
     */
    private String notify_url;
    /**
     * JSAPI、NATIVE、APP 必选
     */
    private String trade_type;

    /**
     * 用户在商户 appid 下的唯一
         标识，trade_type 为 JSAPI
         时，此参数必传，获取方式
         见表头说明。可选
     */
    private String openid;


    /**
     * 只在 trade_type 为 NATIVE
     时需要填写。此 id 为二维码
     中包含的商品 ID，商户自行
     维护。可选
     */
    private String product_id;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }


    /**
     * for测试
     * @param auth_code
     */
    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }
    public void setKey(String key) {
        this.key = key;
    }

    protected void buildParams(){

        params.put("appid",this.appid);
        params.put("mch_id",this.mch_id);
        params.put("device_info",this.device_info);
        params.put("nonce_str",this.nonce_str);
        params.put("body",this.body);
        params.put("attach",this.attach);
        params.put("out_trade_no",this.out_trade_no);
        params.put("total_fee",this.total_fee);
        params.put("spbill_create_ip",this.spbill_create_ip);
        params.put("time_start",this.time_start );
        params.put("time_expire",this.time_expire);
        params.put("notify_url",this.notify_url);
        params.put("trade_type",this.trade_type);
        params.put("product_id",this.product_id);
        params.put("openid",this.openid);
        //for测试
        params.put("auth_code",auth_code);
    }
    public String makeXml(){
        if(this.params == null ){
            buildParams();
            params.put("sign",makeSign(params,key));
        }
        return parseXML(params);
    }


    public static WxUnifiedOrderRequest startJsApi(String openid){
        WxUnifiedOrderRequest wxUnifiedOrderRequest = new WxUnifiedOrderRequest();
        wxUnifiedOrderRequest.setTrade_type("JSAPI");
        wxUnifiedOrderRequest.setOpenid(openid);
        wxUnifiedOrderRequest.setNonce_str(getNonceStr());
        return wxUnifiedOrderRequest;

    }
}
