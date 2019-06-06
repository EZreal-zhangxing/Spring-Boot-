package com.zx.Util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

/**
 * @Auother zhangxing
 * @Date 2019-06-03 15:40
 * @note
 */
@Slf4j
public class RequestUtil {

    private static Logger logger= LoggerFactory.getLogger(RequestUtil.class);

    public static String GEThttpRequestToUrl(String requestUrl){
        StringBuffer stringBuffer= new StringBuffer();
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpGet get=new HttpGet();
            URI url =new URI(requestUrl);
            get.setURI(url);
            get.setHeader("Content-Type","text/html;charset=utf-8");
            get.setHeader("Accept","*/*");
            get.setHeader("Host",url.getHost());
            get.setHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 12_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Mobile/15E148 Safari/604.1");
            get.setHeader("Connection","keep-alive");
            //发送get请求
            response = client.execute(get);
            HttpEntity resEntity = response.getEntity();
            //获取请求数据
            String responseStr = EntityUtils.toString(resEntity);
            stringBuffer.append(responseStr);
            //销毁请求
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("request error requestUrl is [{}]",requestUrl);
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }

    public static JSONObject POSThttpRequestToUrl(String requestUrl, JSONObject requestJson){
        JSONObject jsonObject= null;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpPost post =new HttpPost();
            URI url =new URI(requestUrl);
            post.setURI(url);
            post.setHeader("Content-Type","application/json");
            StringEntity entity =new StringEntity(requestJson.toString());
            post.setEntity(entity);
            //发送post请求
            response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            //获取请求数据
            String responseStr = EntityUtils.toString(resEntity);
            jsonObject =JSONObject.parseObject(responseStr);
            //销毁请求
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            logger.error("request error requestUrl is [{}] requestJson : [{}]",requestUrl,requestJson);
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    public static JSONObject POSThttpRequestToUrl(String requestUrl){
        JSONObject jsonObject= null;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpPost post =new HttpPost();
            URI url =new URI(requestUrl);
            post.setURI(url);
            post.setHeader("Content-Type","application/json");
            //发送post请求
            response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            //获取请求数据
            String responseStr = EntityUtils.toString(resEntity);
            jsonObject =JSONObject.parseObject(responseStr);
            //销毁请求
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            logger.error("request error requestUrl is [{}]",requestUrl);
        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

}
