package com.github.xiaoxiaoming.wxpay.v3;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;


public class WxPayHttpCaller extends WxPayBase {

    private String xmlStr;
    private String url;
    public WxPayHttpCaller(String url, String xmlRequest) {
        this.xmlStr = xmlRequest;
        this.url = url;
    }

    /**
     *  普通的post提交
     * @return
     * @throws Exception
     */
    public WxPayReponse httpPost() throws Exception {
        CloseableHttpClient httpClient= HttpClients.createDefault();
        return new WxPayReponse(doXMLParse(post(httpClient, url, xmlStr)));
    }

    /**
     * https协议下进行post提交
     * @param certFile
     * @param certPwd
     * @return
     * @throws Exception
     */
    public WxPayReponse httpsPost(File certFile,String certPwd) throws Exception{

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(buildSSLSocketFactory(certFile, certPwd))
                .build();
        try {
            return new WxPayReponse(doXMLParse(post(httpclient, url, xmlStr)));
        } finally {

            httpclient.close();
        }

    }

    /**
     * 创建SSL密钥
     * @param certFile
     * @param certPwd
     * @return
     * @throws Exception
     */
    protected  SSLConnectionSocketFactory buildSSLSocketFactory(File certFile,String certPwd) throws Exception {
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(certFile);
        try {
            keyStore.load(instream, certPwd.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPwd.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        return sslsf;
    }

    /**
     * post提交
     * @param httpclient
     * @param url
     * @param data
     * @return
     * @throws java.io.IOException
     */
    protected  String post(CloseableHttpClient httpclient,String url,String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(data, "UTF-8"));
        System.out.println(httpPost.getURI());
        System.out.println("executing request" + httpPost.getRequestLine());
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String xmlStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        response.close();
        return xmlStr;

    }







}
