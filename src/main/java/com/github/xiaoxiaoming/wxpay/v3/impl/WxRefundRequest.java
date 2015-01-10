package com.github.xiaoxiaoming.wxpay.v3.impl;


import com.github.xiaoxiaoming.wxpay.v3.WxPayRequest;

public class WxRefundRequest extends WxPayRequest {

    public static final String URL="https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 微信商品支付密钥,必选
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
     * 微信订单号,可选
     */
    private String transaction_id;

    /**
     *商户系统内部的订单号,
     transaction_id 、
     out_trade_no 二选一， 如果同
     时 存 在 优 先 级 ：
     transaction_id>
     out_trade_no ，必选
     */
    private String   out_trade_no;

    /**
     * 商户系统内部的退款单号，商
     户系统内部唯一，同一退款单
     号多次请求只退一笔 ,必选
     */
    private String out_refund_no;

    /**
     * 订单总金额，单位为分，不
     能带小数点,必选
     */
    private String total_fee;

    /**
     * 退款总金额， 单位为分,可以做
     部分退款 必选
     */
    private String refund_fee;


    /**
     * 操作员帐号, 默认为商户号 必选
     */
    private String op_user_id;


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




    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public void setOp_user_id(String op_user_id) {
        this.op_user_id = op_user_id;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }





    public void setKey(String key) {
        this.key = key;
    }

    @Override
    protected void buildParams(){
        params.put("appid",this.appid);
        params.put("mch_id",this.mch_id);
        params.put("device_info",this.device_info);
        params.put("nonce_str",this.nonce_str);
        params.put("out_trade_no",this.out_trade_no);
        params.put("total_fee",this.total_fee);
        params.put("out_refund_no",this.out_refund_no);
        params.put("op_user_id",this.op_user_id);
        params.put("refund_fee",this.refund_fee);
        params.put("transaction_id",this.transaction_id);
    }

    public String makeXml(){
        if(shouldSign("sign")){
            buildParams();
            params.put("sign",makeSign(params,key));
        }
        return parseXML(params);
    }

    public static WxRefundRequest start(){
        WxRefundRequest wxUnifiedOrderRequest = new WxRefundRequest();
        wxUnifiedOrderRequest.setNonce_str(getNonceStr());
        return wxUnifiedOrderRequest;

    }
}
