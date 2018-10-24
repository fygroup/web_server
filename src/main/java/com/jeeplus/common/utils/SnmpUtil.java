package com.jeeplus.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpUtil {
    public static  int DEFAULT_VERSION = SnmpConstants.version1;
    public static final String DEFAULT_PROTOCOL = "udp";
    public static final int DEFAULT_PORT = 161;
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;
    public static final int DEFAULT_RETRY = 3;

    /**
     * 创建对象communityTarget，用于返回target
     * @return CommunityTarget
     */
    public static CommunityTarget createDefault(String ip, String community,int port,int timeout, int retries,int DEFAULT_VERSION) {
        Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip
                + "/" + (port==0?DEFAULT_PORT:port));
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(address);
        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(timeout==0?DEFAULT_TIMEOUT:timeout); // milliseconds
        target.setRetries(retries==0?DEFAULT_RETRY:retries);
        return target;
    }

    public static CommunityTarget getCommunityTarget(String ip, String community, String port, String timeout, String retries,int version) {
       if(version==2){
            DEFAULT_VERSION=SnmpConstants.version2c;
        }
        CommunityTarget target = createDefault(ip, community, Transformation.null2Integer(port), Transformation.null2Integer(timeout),  Transformation.null2Integer(retries),DEFAULT_VERSION);
        return target;
    }




    /*根据OID，获取单条消息*/
    public static ResourceOidValue snmpGet(String ip, String community, String oid, String port, String timeout, String retries,int version) {

        if(version==2){
            DEFAULT_VERSION=SnmpConstants.version2c;
        }

        ResourceOidValue resourceOidValue=new ResourceOidValue();


        CommunityTarget target = createDefault(ip, community, Transformation.null2Integer(port), Transformation.null2Integer(timeout),  Transformation.null2Integer(retries),DEFAULT_VERSION);
        Snmp snmp = null;
        try {
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid)));

            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            pdu.setType(PDU.GET);
            ResponseEvent respEvent = snmp.send(pdu, target);
            PDU response = respEvent.getResponse();
            if (response == null || response.getErrorStatus() != 0) {
                return resourceOidValue;
            }
            VariableBinding vb =response.get(0);
            if (vb == null) {
                return resourceOidValue;
            }
           // System.out.println("oid:"+vb.getOid()+"  value:"+vb.getVariable());
            resourceOidValue.setOid(vb.getOid().toString());
            resourceOidValue.setValue(vb.getVariable().toString());
            return resourceOidValue;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return resourceOidValue;
    }


    /*根据OID，获取单条消息*/
    public static String snmpGetString(String ip, String community, String oid, String port, String timeout, String retries,int version) {

        if(version==2){
            DEFAULT_VERSION=SnmpConstants.version2c;
        }
        CommunityTarget target = createDefault(ip, community, Transformation.null2Integer(port), Transformation.null2Integer(timeout),  Transformation.null2Integer(retries),DEFAULT_VERSION);
        Snmp snmp = null;
        try {
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid)));

            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            pdu.setType(PDU.GET);
            ResponseEvent respEvent = snmp.send(pdu, target);
            PDU response = respEvent.getResponse();
            if (response == null || response.getErrorStatus() != 0) {
                return "";
            }
            VariableBinding vb =response.get(0);
            if (vb == null) {
                return "";
            }
            System.out.println("oid:"+vb.getOid()+"  value:"+vb.getVariable());
            return vb.getVariable().toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return "";
    }



    /*根据OID，获取下一条单条消息*/
    public static ResourceOidValue snmpGetNext(String ip, String community, String oid,String port,String timeout, String retries,int version) {

        if(version==2){
            DEFAULT_VERSION=SnmpConstants.version2c;
        }
        ResourceOidValue resourceOidValue=new ResourceOidValue();

        CommunityTarget target = createDefault(ip, community,Transformation.null2Integer(port), Transformation.null2Integer(timeout),  Transformation.null2Integer(retries),DEFAULT_VERSION);
        Snmp snmp = null;
        try {
            PDU pdu = new PDU();
            // pdu.add(new VariableBinding(new OID(new int[]
            // {1,3,6,1,2,1,1,2})));
            pdu.add(new VariableBinding(new OID(oid)));

            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            pdu.setType(PDU.GET);
            pdu.setType(PDU.GETNEXT);
            ResponseEvent respEvent = snmp.send(pdu, target);
            PDU response = respEvent.getResponse();
            if (response == null || response.getErrorStatus() != 0) {
                return resourceOidValue;
            }
            VariableBinding vb =response.get(0);
            if (vb == null) {
                return resourceOidValue;
            }
            resourceOidValue.setOid(vb.getOid().toString());
            resourceOidValue.setValue(vb.getVariable().toString());
            return resourceOidValue;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }

        }
        return resourceOidValue;
    }


    /*根据OID列表，一次获取多条OID数据，并且以List形式返回*/
    public static List<ResourceOidValue> snmpGetList(String ip, String community, List<String> oidList,String port,String timeout, String retries,int version) {
        if(version==2){
            DEFAULT_VERSION=SnmpConstants.version2c;
        }
        List<ResourceOidValue> list=new ArrayList<>();
        CommunityTarget target = createDefault(ip, community,Transformation.null2Integer(port), Transformation.null2Integer(timeout),  Transformation.null2Integer(retries),DEFAULT_VERSION);
        Snmp snmp = null;
        try {
            PDU pdu = new PDU();
            for(String oid:oidList)
            {
                pdu.add(new VariableBinding(new OID(oid)));
            }

            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            pdu.setType(PDU.GET);
            ResponseEvent respEvent = snmp.send(pdu, target);
            PDU response = respEvent.getResponse();
            if (response != null) {
                for (int i = 0; i < response.size(); i++) {
                    VariableBinding vb = response.get(i);
                    if (vb != null) {
                        ResourceOidValue resourceOidValue=new ResourceOidValue();
                        resourceOidValue.setOid(vb.getOid().toString());
                        resourceOidValue.setValue(vb.getVariable().toString());
                        list.add(resourceOidValue);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return list;
    }



    /*根据targetOID，获取树形数据*/
    public static List<ResourceOidValue> snmpWalk(String ip, String community, String targetOid, String port, String timeout, String retries, int version) {
        if(version==2){
            DEFAULT_VERSION=SnmpConstants.version2c;
        }
        List<ResourceOidValue> list=new ArrayList<>();

        CommunityTarget target=null;
        try{
            target = createDefault(ip, community, Transformation.null2Integer(port), Transformation.null2Integer(timeout),  Transformation.null2Integer(retries),DEFAULT_VERSION);
        }catch (Exception e){
            return list;
        }

        TransportMapping transport = null;
        Snmp snmp = null;
        try {
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();
            PDU pdu = new PDU();
            OID targetOID = new OID(targetOid);
            pdu.add(new VariableBinding(targetOID));
            boolean finished = false;
            while (!finished) {
                VariableBinding vb = null;
                ResponseEvent respEvent = snmp.getNext(pdu, target);
                PDU response = respEvent.getResponse();

                if (null == response) {
                    finished = true;
                    break;
                } else {
                    vb = response.get(0);
                }
                // check finish
                finished = checkWalkFinished(targetOID, pdu, vb);
                if (!finished) {
                    if (vb != null) {
                        ResourceOidValue resourceOidValue=new ResourceOidValue();
                        resourceOidValue.setOid(vb.getOid().toString());
                        resourceOidValue.setValue(vb.getVariable().toString());
                        list.add(resourceOidValue);
                    }
                    System.out.println(vb.getOid() + " = " + vb.getVariable());
                    // Set up the variable binding for the next entry.
                    pdu.setRequestID(new Integer32(0));
                    pdu.set(0, vb);
                } else {
                    snmp.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return list;
    }




    private static boolean checkWalkFinished(OID targetOID, PDU pdu,VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
           // System.out.println("[true] responsePDU.getErrorStatus() != 0 ");
            //System.out.println(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
           // System.out.println("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
           // System.out.println("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            //System.out.println("[true] targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            //System.out.println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            //System.out.println("[true] Variable received is not "+ "lexicographic successor of requested " + "one:");
            //System.out.println(vb.toString() + " <= " + targetOID);
            finished = true;
        }
        return finished;
    }



}
