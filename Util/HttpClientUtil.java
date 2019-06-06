package com.zx.Util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auother zhangxing
 * @Date 2019-06-03 16:47
 * @note
 */
public class HttpClientUtil {
    private static String charSet = "UTF-8";
    private CloseableHttpResponse response = null;
    private static CookieStore localCookies= new BasicCookieStore();
    private static CloseableHttpClient localClient;

    private static HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

    private static String cookiePath="E://pageinfo/cookie.txt";
    /**
     * https的post请求
     * @param url
     * @param @jsonstr
     * @return
     */
    public String doHttpsPost(String url, String jsonStr) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = SSLClient.createSSLClientDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");

            StringEntity se = new StringEntity(jsonStr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);

            response = httpClient.execute(httpPost);
            if (response != null) {
                Header[] cookHead= response.getHeaders("Set-Cookie");
                Arrays.asList(cookHead).stream().forEach(item -> {
                    System.out.println(item.getValue());
                    try {
                        AnalyseCookie(item.getValue());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });

                writeCookieToFile(localCookies);
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    return EntityUtils.toString(resEntity, charSet);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * http的post请求(用于key-value格式的参数)
     * @param url
     * @param param
     * @return
     */
    public String doHttpPost(String url, Map<String,String> param){
        CloseableHttpClient httpClient = localClient;
        try {
            //请求发起客户端
            if(httpClient == null){
                //创建设置了Cookie的客户端
                if(localCookies != null){
                    httpClient = httpClientBuilder.setDefaultCookieStore(localCookies).build();
                }else{
                    httpClient = HttpClients.createDefault();
                }
            }
            //参数集合
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            //遍历参数并添加到集合
            for(Map.Entry<String, String> entry:param.entrySet()){
                postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            //通过post方式访问
            HttpPost post = new HttpPost(url);
            HttpEntity paramEntity = new UrlEncodedFormEntity(postParams,charSet);
            post.setEntity(paramEntity);
            System.out.println(httpClient);
            response = httpClient.execute(post);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                Header[] cookHead= response.getHeaders("Set-Cookie");
                Arrays.asList(cookHead).stream().forEach(item -> {
                    System.out.println(item.getValue());
                    try {
                        AnalyseCookie(item.getValue());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });

                writeCookieToFile(localCookies);
                HttpEntity valueEntity = response.getEntity();
                String content = EntityUtils.toString(valueEntity);
                //jsonObject = JSONObject.fromObject(content);
                return content;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            writeCookieToFile(localCookies);
        }
        return null;
    }

    /**
     * http的post请求（用于请求json格式的参数）
     * @param url
     * @return
     */
    public String doHttpPost(String url, String jsonStr) {
        CloseableHttpClient httpClient = localClient;
        try {
            //请求发起客户端
            if(httpClient == null){
                //创建设置了Cookie的客户端
                if(localCookies != null){
                    httpClient = httpClientBuilder.setDefaultCookieStore(localCookies).build();
                }else{
                    httpClient = HttpClients.createDefault();
                }
            }
            // 创建httpPost
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json");

            StringEntity entity = new StringEntity(jsonStr, charSet);
            entity.setContentType("text/json");
            entity.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(entity);
            //发送post请求
            response = httpClient.execute(httpPost);
            Header[] cookHead= response.getHeaders("Set-Cookie");
            Arrays.asList(cookHead).stream().forEach(item -> {
                System.out.println(item.getValue());
                try {
                    AnalyseCookie(item.getValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

            writeCookieToFile(localCookies);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * http的Get请求
     * @param url
     * @param param
     * @return
     */
    public String doHttpGet(String url,Map<String,String> param) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = localClient;
        try {
            //请求发起客户端
            if(httpclient == null){
                //创建设置了Cookie的客户端
                if(localCookies != null){
                    httpclient = httpClientBuilder.setDefaultCookieStore(localCookies).build();
                }else{
                    httpclient = HttpClients.createDefault();
                }
            }
            if(param != null && !param.isEmpty()) {
                //参数集合
                List<NameValuePair> getParams = new ArrayList<NameValuePair>();
                for(Map.Entry<String, String> entry:param.entrySet()){
                    getParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                url +="?"+EntityUtils.toString(new UrlEncodedFormEntity(getParams), "UTF-8");
            }
            //发送gey请求
            HttpGet httpGet = new HttpGet(url);
            response = httpclient.execute(httpGet);
            System.out.println();
            Header[] cookHead= response.getHeaders("Set-Cookie");
            Arrays.asList(cookHead).stream().forEach(item -> {
                System.out.println(item.getValue());
                try {
                    AnalyseCookie(item.getValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

            writeCookieToFile(localCookies);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            if(httpclient != null){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            writeCookieToFile(localCookies);
        }
        return null;
    }

    public String doGetWithCookie(String CookieStr,String url,Map<String,String> param) throws ParseException {
        CookieStore cookieStore = AnalyseCookie(CookieStr);
        System.out.println("cookie is "+cookieStore);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();

        try {
            //请求发起客户端
            if(param != null && !param.isEmpty()) {
                //参数集合
                List<NameValuePair> getParams = new ArrayList<NameValuePair>();
                for(Map.Entry<String, String> entry:param.entrySet()){
                    getParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                url +="?"+EntityUtils.toString(new UrlEncodedFormEntity(getParams), "UTF-8");
            }
            //发送gey请求
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            System.out.println();
            Header[] cookHead= response.getHeaders("Set-Cookie");
            Arrays.asList(cookHead).stream().forEach(item -> {
                System.out.println(item.getValue());
                try {
                    AnalyseCookie(item.getValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

            writeCookieToFile(localCookies);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String doGetWithCookieAndHeader(String CookieStr,Header[] headers,String url,Map<String,String> param) throws ParseException {
        CookieStore cookieStore = AnalyseCookie(CookieStr);
        System.out.println("cookie is "+cookieStore);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();

        try {
            //请求发起客户端
            if(param != null && !param.isEmpty()) {
                //参数集合
                List<NameValuePair> getParams = new ArrayList<NameValuePair>();
                for(Map.Entry<String, String> entry:param.entrySet()){
                    getParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                url +="?"+EntityUtils.toString(new UrlEncodedFormEntity(getParams), "UTF-8");
            }
            //发送gey请求
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeaders(headers);
            response = httpClient.execute(httpGet);
            System.out.println();
            Header[] cookHead= response.getHeaders("Set-Cookie");
            Arrays.asList(cookHead).stream().forEach(item -> {
                System.out.println(item.getValue());
                try {
                    AnalyseCookie(item.getValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

            writeCookieToFile(localCookies);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public CookieStore AnalyseCookie(String CookieStr) throws ParseException {
        CookieStore cookieStore = localCookies;
        if(cookieStore == null){
            cookieStore=new BasicCookieStore();
        }
        String[] cookies = CookieStr.trim().split(";");
        List<BasicClientCookie> cookieList =new ArrayList<>();
        for(int i=0;i<cookies.length;i++){
            String cStr = cookies[i];
            String[] keyValue = cStr.split("=");

            if("domain".equals(keyValue[0].replaceAll(" ","").toLowerCase())){
                cookieList.stream().forEach(x->{x.setDomain(keyValue[1]);});
            } else if("path".equals(keyValue[0].replaceAll(" ","").toLowerCase())){
                cookieList.stream().forEach(x->{x.setPath(keyValue[1]);});
            } else if("expires".equals(keyValue[0].replaceAll(" ","").toLowerCase())){
                cookieList.stream().forEach(x->{
                    try {
                        x.setExpiryDate(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).parse(keyValue[1]));
                    } catch (ParseException e) {
                        try {
                            x.setExpiryDate(new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH).parse(keyValue[1]));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }else{
                BasicClientCookie cookie = new BasicClientCookie(keyValue[0].trim(), keyValue[1].trim());
                cookie.setDomain("");
                cookieList.add(cookie);
            }
        }
        //以新获取的cookie为准 如果有重复值 替换掉之前的cookie值
        HashMap<String,BasicClientCookie> needAddCookie = (HashMap<String, BasicClientCookie>) cookieList.stream().collect(Collectors.toMap(Cookie::getName, Function.identity(),(o, n)->n));
        ArrayList<Cookie> cookieArrayList =new ArrayList<>();
        cookieStore.getCookies().stream().forEach(x->{
            if(!needAddCookie.containsKey(x.getName())){
                cookieArrayList.add(x);
            }
        });
        System.out.println("******************************************");
        System.out.println(needAddCookie.keySet());
        System.out.println(cookieArrayList);
        cookieStore.clear();//清空
        needAddCookie.values().stream().forEach(cookieStore::addCookie);
        cookieArrayList.stream().forEach(cookieStore::addCookie);

        System.out.println("&********CookStore is ***************************************");
        cookieStore.getCookies().forEach(System.out::println);
        localCookies = cookieStore;
        localClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();

        return cookieStore;
    }

    void writeCookieToFile(CookieStore cookieStore){
        System.out.println("写入文件Cookie is ");
        cookieStore.getCookies().stream().forEach(System.out::println);
        BufferedWriter bufferedWriter =null;
        try {
            bufferedWriter =new BufferedWriter(new FileWriter(new File(cookiePath)));

            for (Cookie cookie:cookieStore.getCookies()){
                try {
                    bufferedWriter.write(cookie.getName()+"="+cookie.getValue()+"; domain="+cookie.getDomain()+"; path="+cookie.getPath()+"; expiry="+cookie.getExpiryDate());
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public CookieStore getCookieStore(){
        return localCookies;
    }
}
