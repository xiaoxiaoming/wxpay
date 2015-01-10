package com.github.xiaoxiaoming.wxpay.v3;

import java.util.TreeMap;

public abstract class WxPayRequest extends WxPayBase {


    /**
     * 请求参数
     */
    protected TreeMap<String,String> params =new TreeMap();

    /**
     * 够着参数
     */
    protected abstract void buildParams();



    public String getParameter(String key){
        return params.get(key);
    }


}
