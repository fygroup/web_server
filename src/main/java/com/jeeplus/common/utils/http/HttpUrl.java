package com.jeeplus.common.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Le on 2017/12/12.
 */
public class HttpUrl {

    /**
     * 程序中访问http数据接口
     */
    public static String getURLContent(String urlStr) {
        URL url = null;
        HttpURLConnection httpConn = null;
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try{
            url = new URL(urlStr);
            in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );
            String str = null;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {
            return null;
        } finally{
            try{
                if(in!=null) {
                    in.close();
                }
            }catch(IOException ex) {
            }
        }
        return sb.toString();
    }


    /**
        * post方式请求http服务
        * @param urlStr
        * @param params   name=yxd&age=25
        * @return
        * @throws Exception
    */
    public static String getURLByPost(String urlStr,String params)throws Exception{
        URL url=new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        printWriter.write(params);
        printWriter.flush();
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try{
            in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8") );
            String str = null;
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {
            throw ex;
        } finally{
            try{
                conn.disconnect();
                if(in!=null){
                    in.close();
                }
                if(printWriter!=null){
                    printWriter.close();
                }
            }catch(IOException ex) {
                throw ex;
            }
        }
        System.out.println(urlStr+" : "+sb.toString());
        return sb.toString();
    }




    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }



}
