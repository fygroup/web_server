package com.jeeplus.modules.test.web.snmp;

import com.alibaba.druid.support.json.JSONUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.http.NSAuthenticator;
import com.jeeplus.modules.update.server.windows.cpu.WindowsCpuByV1V2;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;

public class MainTest {
    private static SAXReader reader = new SAXReader(false);

    public static void main(String[] args) {
        MainTest test = new MainTest();
        String ip = "139.0.31.251";
        String community = "public";
        String port="161";
        String retries="1";
        String oidval="1.3.6.1.2.1.1.2.0";
        SnmpUtil.snmpGetString( ip, community,oidval, port,  "500",retries,1);

        /*
           1.3.6.1.2.1.1.1.0    description
           1.3.6.1.2.1.1.2.0    系统oid
           1.3.6.1.2.1.1.3.0    运行天数
           1.3.6.1.2.1.1.5.0    型号(系统名称)
           1.3.6.1.2.1.1.6.0
           1.3.6.1.2.1.2.1.0   接口数
           1.3.6.1.2.1.2.2.1.2  路由器接口数及名称    snmpWalk
           1.3.6.1.2.1.2.2.1.6  路由器接口地址        snmpWalk

           1.3.6.1.2.1.25  内存及磁盘                snmpWalk

           获取cpu个数、序号及使用率，1.3.6.1.2.1.25.3.3.1.2  snmpWalk 如下：
            1.3.6.1.2.1.25.3.3.1.2.7 = 6
            ==== walk each vlaue :
            1.3.6.1.2.1.25.3.3.1.2.8 = 18
            ==== walk each vlaue :
            1.3.6.1.2.1.25.3.3.1.2.9 = 8
            ==== walk each vlaue :
            1.3.6.1.2.1.25.3.3.1.2.10 = 13
            ==== walk each vlaue :
            1.3.6.1.2.1.25.3.3.1.2.11 = 10
            ==== walk each vlaue :
            1.3.6.1.2.1.25.3.3.1.2.12 = 12
            ==== walk each vlaue :
            1.3.6.1.2.1.25.3.3.1.2.13 = 9

            1.3.6.1.2.1.25.6.3.1.2  snmpWalk  安装软件列表
            1.3.6.1.2.1.25.6.3.1.4 软件类型
             1.3.6.1.2.1.25.6.3.1.5 软件安装时间

         获取 MAC 1.3.6.1.2.1.4.22.1.2.2.+(192.168.1.1)
         1.3.6.1.2.1.4.20.1.3.+(192.168.1.1) snmpWalk 子网掩码
         */

       // DateFormat df2 = DateFormat.getDateTimeInstance();//可以精确到时分秒

       // String[] str=df2.format(new Date()).split(" ")[1].split(":");
      //  System.out.println(str[0]+":"+str[1]);
       // String oidval = "1.3.6.1.4.1.14331.5.5.1.4.5";
       // System.out.println("====== "+SnmpUtil.snmpWalk( ip, community,oidval, port,  timeout,retries,1));
       /*String flag=SnmpUtil.snmpGet( ip, community,oidval, port,  timeout,retries,1).getValue();
        if(flag != null) {
            results2 = SnmpUtils.getSimpleValueByV1("1.3.6.1.4.1.14331.5.5.1.4.3.0", ip, community, port, timeout, retries);
            if(results2 != null && flag.indexOf("3.3.002") == -1) {
                try {
                    results2 = Float.parseFloat(results2) / 10.0F + "";
                } catch (Exception var29) {
                    results = null;
                    logger.warn(var29, var29);
                    var29.printStackTrace();
                }
            }
        }

        if(results2 != null && !results2.trim().equals("")) {
            break;
        }

        flag = SnmpUtils.getSimpleValueByV1("1.3.6.1.4.1.14331.5.5.1.3.5.0", ip, community, port, timeout, retries);
        if(flag != null && flag.indexOf("3.3.005") != -1) {
            results2 = SnmpUtils.getSimpleValueByV1("1.3.6.1.4.1.14331.5.5.1.4.6.0", ip, community, port, timeout, retries);
        }*/




        //System.out.println("====== "+SnmpUtil.snmpGet( ip, community,oidval, port,  timeout,retries,1).getValue());
      /* for(ResourceOidValue resourceOidValue:list){
            System.out.println("====== "+hexToChinese(resourceOidValue.getValue()));
        }*/
      /* Date date=new Date();
       System.out.println(" ccccccccccccc  "+date.getTime());*/



       // Ping("192.168.1.139");
        //Icmp("192.168.1.139");

         // String name="146435345345345bbbb5345345345345621按时打发ss士大夫是的发送";
         // System.out.println(numberTransformation(name));
        //demoDatabaseMetaData();
       // GeneralTomcatIndicator generalTomcatIndicator=new GeneralTomcatIndicator(null);
       // System.out.println(generalTomcatIndicator.getTomcatInfo().getTotalMemory());
       /* String str="http://192.168.1.253:8077/manager/status?XML=true";
        try {
            getXmlRoot(new URL(str),"tomcat","tomcat");
        } catch (MalformedURLException mysql) {
            mysql.printStackTrace();
        }*/


   /*   String   dataString="[{\"id\":\"tmp-1512010041421\",\"name\":\"公司路由\",\"x\":\"559\",\"y\":\"319\",\"type\":\"0\",\"viewId\":\"8079d7f356354af5a1e6033bce30c833\",\"parentId\":\"-1\",\"objectId\":\"a502e6046f4e4784bdc7e3798a602551\",\"objectClass\":\"3003\",\"instanceId\":\"\",\"style\":\"icon:h3c/h3_router_general.svg;icon-width:57;icon-height:41;\",\"options\":\"\",\"url\":\"\",\"alarm\":\"\"},{\"id\":\"tmp-1512010041422\",\"name\":\"公司路由\",\"x\":\"50\",\"y\":\"50\",\"type\":\"0\",\"viewId\":\"8079d7f356354af5a1e6033bce30c833\",\"parentId\":\"-1\",\"objectId\":\"a502e6046f4e4784bdc7e3798a602551\",\"objectClass\":\"3003\",\"instanceId\":\"\",\"style\":\"icon:h3c/h3_router_general.svg;icon-width:57;icon-height:41;\",\"options\":\"\",\"url\":\"\",\"alarm\":\"\"}]";

        List<TopoSymbols> list=new ArrayList<>();
        JSONArray array = new JSONArray(dataString);
        for(int i=0;i<array.length();i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            TopoSymbols topoSymbols=new TopoSymbols();
            topoSymbols.setName(jsonObject.getString("name"));
            topoSymbols.setX(jsonObject.getString("x"));
            topoSymbols.setY(jsonObject.getString("y"));
            topoSymbols.setView(new TopoView(jsonObject.getString("viewId")));
            topoSymbols.setObjectClass(jsonObject.getString("objectClass"));
            list.add(topoSymbols);
        }*/
       // System.out.println(list);


 /*       String transportTxt = "2017-12-21 18:49:35 设备摘要信息：设备厂商为微软，IOS版本为 6.3 (build 16299 multiprocessor free)，设备类型为：服务器，设备具有网络接口2个，设备配置子网1个，设备指定路由条数0条2017-12-21 18:49:35 192.168.0.128由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:36 192.168.0.122由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:36 192.168.0.113由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:36 192.168.0.119由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.197 的类型2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.198 的类型2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.199 的类型2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.200 的类型2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.201 的类型2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.202 的类型2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.203 的类型2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.204 的类型2017-12-21 18:49:36 192.168.0.125由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:36 192.168.0.148由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:36 代理检测：正在检测设备 192.168.0.205 的类型2017-12-21 18:49:36 192.168.0.149由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:36 192.168.0.145由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 192.168.0.138由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 192.168.0.150由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.206 的类型2017-12-21 18:49:37 192.168.0.127由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 192.168.0.137由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.207 的类型2017-12-21 18:49:37 192.168.0.126由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 192.168.0.152由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 192.168.0.129由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.208 的类型2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.209 的类型2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.210 的类型2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.211 的类型2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.212 的类型2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.213 的类型2017-12-21 18:49:37 代理检测：正在检测设备 192.168.0.214 的类型2017-12-21 18:49:38 代理检测：正在检测设备 192.168.0.215 的类型2017-12-21 18:49:38 代理检测：正在检测设备 192.168.0.216 的类型2017-12-21 18:49:38 代理检测：正在检测设备 192.168.0.217 的类型2017-12-21 18:49:38 192.168.0.154由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 代理检测：正在检测设备 192.168.0.218 的类型2017-12-21 18:49:38 192.168.0.142由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 192.168.0.153由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 192.168.0.141由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 代理检测：正在检测设备 192.168.0.219 的类型2017-12-21 18:49:38 192.168.0.155由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 192.168.0.140由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 代理检测：正在检测设备 192.168.0.220 的类型2017-12-21 18:49:38 192.168.0.143由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 192.168.0.146由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 192.168.0.147由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:38 代理检测：正在检测设备 192.168.0.221 的类型2017-12-21 18:49:39 192.168.0.139由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:39 192.168.0.144由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:39 192.168.0.151由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.222 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.223 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.224 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.225 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.226 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.227 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.228 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.229 的类型2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.230 的类型2017-12-21 18:49:39 192.168.0.156由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:39 代理检测：正在检测设备 192.168.0.231 的类型2017-12-21 18:49:39 192.168.0.158由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:39 192.168.0.160由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 192.168.0.157由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 192.168.0.159由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.232 的类型2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.233 的类型2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.234 的类型2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.235 的类型2017-12-21 18:49:40 192.168.0.164由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 192.168.0.162由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 192.168.0.165由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 192.168.0.161由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 192.168.0.163由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.236 的类型2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.237 的类型2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.238 的类型2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.239 的类型2017-12-21 18:49:40 代理检测：正在检测设备 192.168.0.240 的类型2017-12-21 18:49:41 192.168.0.168由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 192.168.0.167由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 192.168.0.166由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 代理检测：正在检测设备 192.168.0.241 的类型2017-12-21 18:49:41 代理检测：正在检测设备 192.168.0.242 的类型2017-12-21 18:49:41 代理检测：正在检测设备 192.168.0.243 的类型2017-12-21 18:49:41 192.168.0.171由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 192.168.0.172由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 192.168.0.170由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 192.168.0.174由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 192.168.0.173由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 192.168.0.169由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 代理检测：正在检测设备 192.168.0.244 的类型2017-12-21 18:49:41 192.168.0.175由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:41 代理检测：正在检测设备 192.168.0.245 的类型2017-12-21 18:49:41 代理检测：正在检测设备 192.168.0.246 的类型2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.247 的类型2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.248 的类型2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.249 的类型2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.250 的类型2017-12-21 18:49:42 192.168.0.181由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:42 192.168.0.178由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:42 192.168.0.176由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.251 的类型2017-12-21 18:49:42 192.168.0.177由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:42 192.168.0.179由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.252 的类型2017-12-21 18:49:42 192.168.0.182由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:42 192.168.0.180由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.253 的类型2017-12-21 18:49:42 代理检测：正在检测设备 192.168.0.254 的类型2017-12-21 18:49:42 192.168.0.184由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.192由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.187由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.190由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.183由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.188由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.186由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.185由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.191由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.189由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.199由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.201由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.202由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.200由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.198由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.197由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:43 192.168.0.196由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.195由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.194由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.203由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.193由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.216由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.207由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.206由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.215由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.214由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.213由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.212由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.205由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.211由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.204由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.208由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:44 192.168.0.217由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.210由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.209由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.228由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.230由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.227由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.223由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.222由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.221由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.220由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.218由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.226由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.225由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.224由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.219由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.229由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:45 192.168.0.231由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.232由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.233由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.235由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.234由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.239由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.236由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.238由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.237由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.243由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.242由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.240由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.241由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.246由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.244由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.248由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:46 192.168.0.250由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 192.168.0.249由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 192.168.0.247由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 192.168.0.245由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 192.168.0.254由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 192.168.0.252由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 192.168.0.251由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 192.168.0.253由于不使用Ping检测且Ip不响应Snmp,无法确定Ip有效性,过滤该Ip解析2017-12-21 18:49:47 设备采集：正在采集设备 192.168.0.107 数据2017-12-21 18:49:47 设备采集：正在采集设备 192.168.0.110 数据2017-12-21 18:49:47 设备采集：正在采集设备 192.168.0.1 数据2017-12-21 18:49:47 设备采集：正在采集设备 192.168.0.2 数据2017-12-21 18:49:47 设备采集：正在采集设备 192.168.0.109 数据2017-12-21 18:49:47 管理IP: 192.168.0.109 OID: 1.3.6.1.4.1.14331.1.4 设备类型: SERVICE_DEVICE 厂商: 天融信 连接类型: SNMPV1 使用共同体: public设备数据采集用时: 0 days, 0:0:0.632017-12-21 18:49:47 管理IP: 192.168.0.2 OID: 1.3.6.1.4.1.311.1.1.3.1.1 设备类型: SERVICE_DEVICE 厂商: 微软 连接类型: SNMPV1 使用共同体: le设备数据采集用时: 0 days, 0:0:0.942017-12-21 18:49:47 管理IP: 192.168.0.110 OID: 1.3.6.1.4.1.8072.3.2.10 设备类型: ROUTE_DEVICE 厂商: XDHY_Fw_10 连接类型: SNMPV1 使用共同体: public设备数据采集用时: 0 days, 0:0:0.2192017-12-21 18:49:47 管理IP: 192.168.0.1 OID: 1.3.6.1.4.1.311.1.1.3.1.1 设备类型: SERVICE_DEVICE 厂商: 微软 连接类型: SNMPV1 使用共同体: le设备数据采集用时: 0 days, 0:0:0.2652017-12-21 18:52:02 设备摘要信息：设备厂商为思科，IOS版本为 12.0(5.2)xu，设备类型为：二层交换机，设备具有网络接口51个，设备配置子网0个，设备指定路由条数0条2017-12-21 18:52:02 发现链路，链路信息：192.168.0.108:2-&gt;192.168.0.107:22017-12-21 18:52:02 管理IP: 192.168.0.107 OID: 1.3.6.1.4.1.9.1.278 设备类型: SWITCH_DEVICE 厂商: 思科 连接类型: SNMPV1 使用共同体: public设备数据采集用时: 0 days, 0:2:26.9602017-12-21 18:52:02 发现链路，链路信息：192.168.0.107:8-&gt;192.168.0.109:22017-12-21 18:52:02 发现链路，链路信息：192.168.0.107:6-&gt;192.168.0.1:22017-12-21 18:52:02 发现链路，链路信息：192.168.0.107:4-&gt;192.168.0.2:6";
        String chinese = "[\u4e00-\u9fa5]";
       String linkTxt=transportTxt;
        List<String> ips=new ArrayList<>();
        List<String> stringList = new ArrayList<String>();
        while (true){
            if (transportTxt.indexOf("管理IP")>0){
                String temp = transportTxt;
                int i = temp.indexOf("使用共同体")+7;
                while (true){
                    String a = temp.substring(i,i+1);
                    if (a.matches(chinese)){
                        break;
                    }else {
                        i++;
                    }
                }
                stringList.add(temp.substring(temp.indexOf("管理IP"),i)) ;
                transportTxt = transportTxt.substring(i,transportTxt.length());
            }else {
                break;
            }
        }
        String result="";
        for (int i = 0; i < stringList.size(); i++) {
            HashMap<String,String> discoveryEntry = new HashMap<String,String>();
            String temp = stringList.get(i);
            discoveryEntry.put(temp.substring(0,temp.indexOf(":")),temp.substring(temp.indexOf(":")+1,temp.indexOf("OID")).trim());
            discoveryEntry.put(temp.substring(temp.indexOf("使用共同体"),temp.indexOf(":",temp.indexOf("使用共同体"))),temp.substring(temp.indexOf(":",temp.indexOf("使用共同体"))+1,temp.length()).trim());
            ips.add(temp.substring(temp.indexOf(":")+1,temp.indexOf("OID")).trim());
            if(i==(stringList.size()-1)){
                result+=JSONUtils.toJSONString(discoveryEntry);
            }else{
                result+=JSONUtils.toJSONString(discoveryEntry)+",";
            }

        }

        System.out.println(ips);
        String linkResult="";
        String itemResult="";
        while (true){
            if(linkTxt.indexOf("发现链路，链路信息：")!=-1) {
                itemResult = linkTxt.substring(linkTxt.indexOf("发现链路，链路信息：")+10);
                if(itemResult.indexOf("发现链路，链路信息：")!=-1) {
                    itemResult = itemResult.substring(itemResult.indexOf("发现链路，链路信息："));
                }else{
                    itemResult=itemResult.substring(1) ;
                }
                linkTxt= linkTxt.substring(linkTxt.indexOf("发现链路，链路信息：")+10);
                int i = 1;
                while (true) {
                    if (i + 1 > linkTxt.length()) {
                        break;
                    }
                    String a = linkTxt.substring(i, i + 1);
                    if (a.matches(chinese)) {
                        break;
                    } else {
                        i++;
                    }
                }
                linkTxt = linkTxt.substring(0, i);
                String[] value = linkTxt.split("-&gt;");
                if(value.length == 2){
                    HashMap<String,String> linkMap = new HashMap<String,String>();
                    String upIp=(value[0]).substring(0,value[0].indexOf(":"));
                    String downIp=(value[1]).substring(0,value[1].indexOf(":"));
                    if(ips.contains(upIp)&&ips.contains(downIp)) {
                        linkMap.put("up", upIp);
                        linkMap.put("down", downIp);
                        linkResult += JSONUtils.toJSONString(linkMap) + ",";
                    }
                }
                linkTxt = itemResult;
            }else{
                break;
            }
        }
        if(linkResult.length()>0){
            linkResult="["+linkResult.substring(0,linkResult.length()-1)+"]";
        }
        System.out.println(linkResult);

        result=result.replaceAll("管理IP","ip").replaceAll("使用共同体","rdcommunity").replaceAll("连接类型","snmpType");
        result="["+result+"]";
        System.out.println(result);*/
    }








    public static Element getXmlRoot(URL url, String name, String password) {

        if(url == null) {
            return null;
        } else {
            HttpURLConnection con = null;
            InputStream in = null;
            Element root = null;

            try {
                if(name != null) {
                    Authenticator authen = new NSAuthenticator(name, password);
                    Authenticator.setDefault(authen);
                    con = (HttpURLConnection)url.openConnection();
                    con.setDoInput(true);
                    in = con.getInputStream();
                    Document doc = null;
                    SAXReader var8 = reader;
                    synchronized(reader) {
                        doc = reader.read(in);
                    }

                    root = doc.getRootElement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(con != null) {
                    con.disconnect();
                    con = null;
                }
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException var18) {
                        var18.printStackTrace();
                    }

                    in = null;
                }

            }

            Element jvmElement = root.element("jvm");
            Element memoryElement = jvmElement.element("memory");
            String free = memoryElement.attributeValue("free");
            String total = memoryElement.attributeValue("total");

            System.out.println(free+" free");
            System.out.println(total+" total");
           boolean success = false;
            String processingTime = null;
            String requestCount = null;
            String bytesReceived = null;
            String bytesSent = null;


            List<Element> connectors = root.elements("connector");
            if(connectors != null) {
                int size = connectors.size();

                for(int i = 0; i < size; ++i) {
                    Element e = (Element)connectors.get(i);
                    String connectorName = e.attributeValue("name").replace("\"", "");
                    if(connectorName.startsWith("http")) {
                        Element requestInfoElement = e.element("requestInfo");
                        processingTime = requestInfoElement.attributeValue("processingTime");
                        requestCount = requestInfoElement.attributeValue("requestCount");
                        bytesReceived = requestInfoElement.attributeValue("bytesReceived");
                        bytesSent = requestInfoElement.attributeValue("bytesSent");
                        success = true;
                        break;
                    }
                }
            }

            if(success) {
                try {
                    System.out.println("CurrentBytesReceived "+Long.parseLong(bytesReceived));
                    System.out.println("CurrentBytesSent "+Long.parseLong(bytesSent));
                            System.out.println("CurrentRequestCount "+Long.parseLong(requestCount));
                                    System.out.println("ProcessingTime "+Long.parseLong(processingTime));
                } catch (Exception var25) {
                    var25.printStackTrace();
                }
            }

            return root;
        }
    }


    public static void demoDatabaseMetaData() {
        try {

            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://192.168.1.253:3306/mysql";    //JDBC的URL
            //调用DriverManager对象的getConnection()方法，获得一个Connection对象
            Connection con = DriverManager.getConnection(url, "user", "123456");

            //
            DatabaseMetaData dmd = con.getMetaData();
            System.out.println("当前数据库是：" + dmd.getDatabaseProductName());
            System.out.println("当前数据库版本：" + dmd.getDatabaseProductVersion());
            System.out.println("当前数据库驱动：" + dmd.getDriverVersion());
            System.out.println("当前数据库URL：" + dmd.getURL());
            System.out.println("当前数据库是否是只读模式？：" + dmd.isReadOnly());
            System.out.println("当前数据库是否支持批量更新？：" + dmd.supportsBatchUpdates());
            System.out.println("当前数据库是否支持结果集的双向移动（数据库数据变动不在ResultSet体现）？："
                    + dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
            System.out.println("当前数据库是否支持结果集的双向移动（数据库数据变动会影响到ResultSet的内容）？："
                    + dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
            //System.out.println("--- "+dmd.toString());

            Statement stat = con.createStatement();
            String sql = "show status like 'Threads_connected'";
            ResultSet queryResult  = stat.executeQuery(sql);
            if(queryResult.next()) {
                System.out.println("--- "+ (Integer.parseInt(queryResult.getString(2))-1));
                //System.out.println("--- "+ getDuration(Long.parseLong(queryResult.getString(2))));

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //计算持续时间
  static   String getDuration(Long diff){
        String result="";
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
      diff=diff*1000;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        if(day>0){
            result+=day + "天";
        }
        if(hour>0){
            result+=hour + "小时";
        }
        if(min>0){
            result+=min + "分钟";
        }

        if(result.length()==0){
            result+="1分钟";
        }

        return  result;
    }




    static String numberTransformation(String pathName){
        String number="";
        Pattern pattern=Pattern.compile("^[0-9]*$");
        Matcher matcher=null;
        try {
            char [] stringArr = pathName.toCharArray();
            for(char c:stringArr){
                matcher=pattern.matcher(String.valueOf(c));
                if(matcher.matches()){
                    number+=String.valueOf(c);
                }else{
                    break;
                }
            }
        }catch (Exception e){
            return pathName;
        }
         return  number;
    }



    public static String parse16ToTime(String value) {
        if (value == null) {
            return null;
        }
        String tmp[] = null;
        if (value.indexOf(" ") >= 0) {
            tmp = value.split(" ");
        } else {
            tmp = new String[1];
            tmp[0] = value;
        }

        StringBuffer sb = new StringBuffer();
        for (int i=1; i<tmp.length-1; i++){
            String item=tmp[i];
            if(i==1){
                 item=tmp[0]+tmp[i];
            }
            sb.append(String.format("%02d", Integer.parseInt(item, 16)));
            if (i < tmp.length - 2) {
                if(i<=2) {
                    sb.append("-");
                }else if(i==3){
                    sb.append(" ");
                }else {
                    sb.append(":");
                }
            }
        }
        return sb.toString();
    }





    public static String  hexToChinese(String value) {
        if(!isHex(value)){
            return value;
        }
        String hexStr = value.replaceAll(":", "");
        byte[] by = HexStringToBytes(hexStr);
        try{
            return new String(by, "GB2312");
        } catch( Exception e){
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 16进制分隔字符
     */
    static final String[] HEX_SPLIT_STRING = {":", " "};
    public static boolean isHex(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }

        for (String splitStr : HEX_SPLIT_STRING) {
            if (value.indexOf(splitStr) >=0) {
                value += splitStr;
                String rex = "([0-9a-fA-F][0-9a-fA-F]["+splitStr+"])+";
                Pattern p = Pattern.compile(rex);
                if (p.matcher(value).matches()) {
                    return true;
                }
            }
        }
        return false;
    }


    public static byte[] HexStringToBytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }



    public static String neatenMac(String mac) {
        if(mac != null && !mac.trim().equals("")) {
            StringBuffer temMac = new StringBuffer();

            for(int i = 0; i < mac.length(); ++i) {
                char ch = mac.charAt(i);
                if(ch >= 48 && ch <= 57 || ch >= 65 && ch <= 70 || ch >= 97 && ch <= 102) {
                    temMac.append(ch);
                }
            }

            if(temMac.length() != 12) {
                return null;
            } else {
                mac = temMac.toString();
                StringBuffer buffer = new StringBuffer();

                for(int i = 0; i < mac.length(); i += 2) {
                    buffer.append(mac.substring(i, i + 2)).append(" ");
                }

                System.out.println(buffer.toString().trim());
                return buffer.toString().trim();
            }
        } else {
            return null;
        }
    }


    public void testGet()
    {
        String ip = "192.168.1.1";
        String community = "asus";
        String port="161";
        String timeout="5000";
        String retries="4";
        /*
           1.3.6.1.2.1.1.1.0    description
           1.3.6.1.2.1.1.2.0    系统oid
           1.3.6.1.2.1.1.3.0    运行天数
           1.3.6.1.2.1.1.5.0    型号
           1.3.6.1.2.1.1.6.0
           1.3.6.1.2.1.2.1.0   接口数
           1.3.6.1.2.1.25.3.3.1.2.X   通用cpu
         */
        String oidval = "1.3.6.1.2.1.7.1";
        SnmpUtil.snmpGet(ip, community, oidval, port, timeout,  retries,1);
    }


    public void testGetList(){
        String ip = "192.168.1.139";
        String community = "le";
        String port="161";
        String timeout="5000";
        String retries="4";
        List<String> oidList=new ArrayList<String>();
        oidList.add("1.3.6.1.2.1.1.1.0");
        oidList.add("1.3.6.1.2.1.1.2.0");
        oidList.add("1.3.6.1.2.1.1.3.0");
        oidList.add("1.3.6.1.2.1.1.5.0");  //型号
        oidList.add("1.3.6.1.2.1.1.6.0");
        oidList.add("1.3.6.1.2.1.2.1.0");
        SnmpUtil.snmpGetList(ip, community, oidList, port, timeout,  retries,1);
    }


    public void testGetAsyList()
    {
        String ip = "192.168.1.139";
        String community = "le";
        String port="161";
        String timeout="5000";
        String retries="4";
        List<String> oidList=new ArrayList<String>();
        oidList.add("1.3.6.1.2.1.1.1.0");
        oidList.add("1.3.6.1.2.1.1.2.0");
        oidList.add("1.3.6.1.2.1.1.3.0");
        oidList.add("1.3.6.1.2.1.1.5.0");  //型号
        oidList.add("1.3.6.1.2.1.1.6.0");
        oidList.add("1.3.6.1.2.1.2.1.0");
        SnmpUtil.snmpGetList(ip, community, oidList, port, timeout,  retries,1);
        System.out.println("i am first!");
    }


    public void testWalk()
    {
        String ip = "192.168.1.139";
        String community = "le";
        String port="161";
        String timeout="5000";
        String retries="4";
        String targetOid = "1.3.6.1.2.1.1.5.0";
        SnmpUtil.snmpWalk(ip, community, targetOid, port, timeout,  retries,1);
    }


    public void testAsyWalk()
    {
        String ip = "192.168.1.139";
        String community = "le";
        String port="161";
        String timeout="5000";
        String retries="4";
        String oidval = "1.3.6.1.2.1.25.4.2.1.2";
        // 异步采集数据
        SnmpUtil.snmpWalk(ip, community, oidval, port, timeout,  retries,1);
    }




    public void testVersion(){
        System.out.println(org.snmp4j.version.VersionInfo.getVersion());
    }






    /**
     * @param host
     */
    public static void ping(String host){
        String line = null;
        try{
            Process pro = Runtime.getRuntime().exec("Ping " + host);
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            while ((line = buf.readLine()) != null){
                System.out.println(line);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param host
     */
    public static void icmp(String host){
        try {
            int timeOut = 0;
            boolean status = InetAddress.getByName(host).isReachable(timeOut);
            System.out.println("shujubao： " + status);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

