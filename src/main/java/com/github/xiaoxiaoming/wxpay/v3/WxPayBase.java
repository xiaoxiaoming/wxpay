package com.github.xiaoxiaoming.wxpay.v3;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.*;
import java.util.*;


public abstract class WxPayBase {

    public  final static String DEFAULT_CHARSET = "UTF-8";

    public  final static String DEFAULT_SIGN_TYPE = "MD5";


    public boolean isSuccessResult(Map map){
        return map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS");
    }

    public String getErrMsg(Map map){
        return map.get("err_code")+":"+map.get("err_code_des");
    }


    protected String makeSign(SortedMap params,String key) {

        StringBuffer sb = new StringBuffer();
        Set es = params.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        System.out.println("md5 sb:" + sb);
        String sign = MD5Util.MD5Encode(sb.toString(), DEFAULT_CHARSET)
                .toUpperCase();
        return sign;
    }

    //输出XML
    public String parseXML(SortedMap params) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>\n");
        Set es = params.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v) && !"appkey".equals(k)) {

                if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)||"notify_url".equals(k)) {

                    sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">\n");

                }else {

                    sb.append("<"+k+">"+v+"</"+k+">\n");

                }
            }
        }
        sb.append("</xml>");
        System.out.println(sb.toString());
        return sb.toString();
    }
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return

     */
    public static Map doXMLParse(String strxml) throws Exception {
        System.out.println(strxml);
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        // 关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    public String readPostData(InputStream is){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            //读取HTTP请求内容
            String buffer = null;
            StringBuffer sb = new StringBuffer();
            while ((buffer = br.readLine()) != null) {
                sb.append(buffer);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获得 随机字符串
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }
    /**
     * 获得时间戳
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
