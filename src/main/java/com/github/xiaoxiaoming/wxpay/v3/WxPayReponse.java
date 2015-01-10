package com.github.xiaoxiaoming.wxpay.v3;

import java.io.InputStream;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class WxPayReponse extends WxPayBase {

    SortedMap treeMap;


    public WxPayReponse() {

    }

    public WxPayReponse(TreeMap treeMap) {
        this.treeMap = treeMap;
    }

    public WxPayReponse(Map treeMap) {
        this.treeMap = new TreeMap(treeMap);
    }

    public WxPayReponse(InputStream inputStream) throws Exception {
        treeMap = new TreeMap(doXMLParse(readPostData(inputStream)));
    }

    /**
     *
     * @param apiKey
     * @return
     */
    public boolean valid(String apiKey){
        return makeSign(treeMap,apiKey).equals(treeMap.get("sign"));
    }

    public boolean isSuccess(){
        return isSuccessResult(treeMap);
    }

    public String getErrMsg(){
        return getErrMsg(treeMap);
    }

    public String getParameter(String key){
        return (String) treeMap.get(key);
    }
}
