package com.jeeplus.modules.collect.middleware.tomcat;

import com.jeeplus.common.utils.http.NSAuthenticator;
import com.jeeplus.modules.collect.middleware.tomcat.entity.TomcatInfo;
import com.jeeplus.modules.resource.entity.Resource;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Le on 2017/11/28.
 */
public class GeneralTomcatIndicator {
    static String ip = null;
    static String name = null;
    static String password = null;
    static String port = null;

    private static SAXReader reader = new SAXReader(false);
    public GeneralTomcatIndicator(Resource resource) {
        ip=resource.getIp();
        port = resource.getResourceBaseInfo().getAccessConfigPort();
        name = resource.getResourceBaseInfo().getAccessConfigUserName();
        password = resource.getResourceBaseInfo().getAccessConfigPassword();
    }





    public static TomcatInfo getTomcatInfo() {
        TomcatInfo info = null;
        String location = null;
        URL url = null;


            StringBuffer buffer = new StringBuffer();
            buffer.append(ip);
            buffer.append(":");
            buffer.append(port);
            buffer.append(":");
            buffer.append(name);
            buffer.append(":");
            buffer.append(password);
            info  = new TomcatInfo();
            StringBuffer buffer1 = new StringBuffer();
            buffer1.append("http://");
            buffer1.append(ip);
            buffer1.append(":");
            buffer1.append(port);
            buffer1.append("/manager/status?XML=true");
            location = buffer1.toString();
            try {
                url = new URL(location);
            } catch (Exception e) {
                e.printStackTrace();
            }
           getRealTimeData(info, url, name, password);
          return info;
    }

    private static boolean getRealTimeData(TomcatInfo info, URL url, String name, String password) {
        boolean success = false;
        long startTime = System.currentTimeMillis();
        Element root = getXmlRoot(url, name, password);
        long current;
        if(root != null) {
            current = System.currentTimeMillis();
            info.setResponseTime(current - startTime);
        } else {
            info.setResponseTime(-1L);
        }
        current = System.currentTimeMillis();
        String processingTime = null;
        String requestCount = null;
        String bytesReceived = null;
        String bytesSent = null;
        info.setLastTime(info.getCurrentTime());
        info.setCurrentTime(current);
        info.setLastBytesReceived(info.getCurrentBytesReceived());
        info.setLastBytesSent(info.getCurrentBytesSent());
        info.setLastRequestCount(info.getCurrentRequestCount());
        if(root != null) {
            info.setStatus(true);
            Element jvmElement = root.element("jvm");
            Element memoryElement = jvmElement.element("memory");
            String free = memoryElement.attributeValue("free");
            String total = memoryElement.attributeValue("total");
            try {
                info.setFreeMemory(Long.parseLong(free));
                info.setTotalMemory(Long.parseLong(total));
                info.setUsedMemory(info.getTotalMemory() - info.getFreeMemory());
            } catch (Exception var26) {
                var26.printStackTrace();
            }
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
                    info.setCurrentBytesReceived(Long.parseLong(bytesReceived));
                    info.setCurrentBytesSent(Long.parseLong(bytesSent));
                    info.setCurrentRequestCount(Long.parseLong(requestCount));
                    info.setProcessingTime(Long.parseLong(processingTime));
                } catch (Exception var25) {
                    var25.printStackTrace();
                }
            }
        } else {
            info.setStatus(false);
        }
        if(!success) {
            updateTomcatInfoByNull(info);
        }
        return success;
    }

    private static void updateTomcatInfoByNull(TomcatInfo info) {
        info.setCurrentBytesReceived(-1L);
        info.setCurrentBytesSent(-1L);
        info.setCurrentRequestCount(-1L);
        info.setFreeMemory(-1L);
        info.setTotalMemory(-1L);
        info.setUsedMemory(-1L);
        info.setProcessingTime(-1L);
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
            } catch (Exception var20) {
            } finally {
                if(con != null) {
                    con.disconnect();
                    con = null;
                }
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException var18) {
                    }
                    in = null;
                }
            }
            return root;
        }
    }
    static {
        reader.setValidation(false);
    }
}
