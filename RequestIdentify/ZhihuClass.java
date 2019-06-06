package com.zx.RequestIdentify;

import com.zx.Util.HttpClientUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auother zhangxing
 * @Date 2019-06-03 17:12
 * @note
 */
public class ZhihuClass {

    private static String Hosturl="https://www.zhihu.com";

    private static String LoginUrl="https://www.zhihu.com/signin?next=%2F";

    private static String userName="384116259@qq.com";
    private static String passWord="zhangxing0923";

    private static String filePath="E://pageinfo";

    private static String youCookie="";

    public String execute() throws Exception {
        HttpClientUtil clientUtil =new HttpClientUtil();
//        clientUtil.doHttpGet(LoginUrl,null);
//        Thread.sleep(1000);
//        CookieStore cookieStore = clientUtil.getCookieStore();
//        List<Cookie> list = cookieStore.getCookies().stream().filter(x->"_xsrf".equals(x.getName())).collect(Collectors.toList());
//        HashMap<String,String> hashMap =new HashMap<>();
//        hashMap.put("_xsrf",list.get(0).getValue());
//        hashMap.put("email",userName);
//        hashMap.put("password",passWord);
//        hashMap.put("remember_me","true");
//        String loginStr = clientUtil.doHttpPost(LoginUrl,hashMap);
//        //登陆胡的信息
//        System.out.println(loginStr);
//        Thread.sleep(1000);
        String indexHtml=clientUtil.doGetWithCookie(youCookie,Hosturl,null);
        writeFile(indexHtml,"data.html");
        System.out.println(indexHtml);
        return null;
    }

    void writeFile(String str,String fileName){
        File file =null;
        BufferedWriter writer=null;
        try {
            file =new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            file =new File(file,fileName);
            writer =new BufferedWriter(new FileWriter(file));
            writer.write(str);
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        ZhihuClass zhihuClass =new ZhihuClass();
        try {
            zhihuClass.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
