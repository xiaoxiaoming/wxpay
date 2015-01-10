package com.github.xiaoxiaoming.wxpay.v3.impl;

import com.alibaba.fastjson.JSON;
import com.github.xiaoxiaoming.wxpay.v3.WxPayRequest;

public class WxBrandWCPayRequest extends WxPayRequest {

    private String key;
    /**
     * 商户注册具有支付权限的公众号成功后即可获得
     */
    private String  appId;

    /**
     * 商户生成，从 1970 年 1 月 1日 00：00：00 至今的秒数，即当前的时间，且最终需要转换为字符串形式；
     */
    private String timeStamp;

    /**
     * 随机字符串
     */
    private String nonceStr;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 订单详情扩展字符串
     */
    private String packageValue;


    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }
    public void setKey(String key) {
        this.key = key;
    }


    public String makeJson(){
        if(this.params == null  || !params.containsKey("paySign") || params.containsKey("package")){
            buildParams();
            params.put("paySign",makeSign(params,key));
        }
        return JSON.toJSONString(params);
    }
    /**
     * 组装参数
     * @return
     * @throws Exception
     */
    @Override
    protected void buildParams(){
        params.put("appId", appId);
        params.put("timeStamp", timeStamp);
        params.put("nonceStr",nonceStr);
        params.put("package",packageValue);
        params.put("signType",signType);
    }


    /**
     * 启动一个请求
     * @return
     */
    public static WxBrandWCPayRequest startRequest(String appId,String key,String packageValue){
        WxBrandWCPayRequest request = new WxBrandWCPayRequest();
        request.setSignType(DEFAULT_SIGN_TYPE);
        request.setNonceStr(getNonceStr());
        request.setTimeStamp(getTimeStamp());
        request.setAppId(appId);
        request.setKey(key);
        request.setPackageValue("prepay_id="+packageValue);
        return request;

    }
}