
package com.jeeplus.modules.test.web.snmp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

public class MultiThreadedTrapReceiver implements CommandResponder {

    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp = null;
    private ThreadPool threadPool;

    public MultiThreadedTrapReceiver() {

    }

    private void init() throws  IOException {

        //创建接收SnmpTrap的线程池，参数： 线程名称及线程数
        threadPool = ThreadPool.create("Traps", 2);
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,
                new MessageDispatcherImpl());
        //监听端的 ip地址 和 监听端口号
        TransportMapping transport = new DefaultUdpTransportMapping(new UdpAddress("139.0.2.25/163"));


        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());


        sendPDU();
        //开启Snmp监听，可以接收来自Trap端的信息。
        snmp.listen();
    }


    public void run() {
        try {
            init();
            snmp.addCommandResponder(this);
            System.out.println("开始监听Trap信息!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 实现CommandResponder的processPdu方法, 用于处理传入的请求、PDU等信息
     * 当接收到trap时，会自动进入这个方法
     *
     * @param respEvnt
     */
    public void processPdu(CommandResponderEvent respEvnt) {
        // 解析Response
        if (respEvnt != null && respEvnt.getPDU() != null) {
            @SuppressWarnings("unchecked")
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getPDU().getVariableBindings();
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());
            }
        }
    }

    //开启监控的main方法。
    public static void main(String[] args) {
        MultiThreadedTrapReceiver multithreadedtrapreceiver = new MultiThreadedTrapReceiver();
        multithreadedtrapreceiver.run();
    }






    public void sendPDU() throws IOException {

        // 设置 target
        CommunityTarget target = new CommunityTarget();
        target.setAddress(new UdpAddress("139.0.2.25/163"));

        // 通信不成功时的重试次数
        target.setRetries(2);
        // 超时时间
        target.setTimeout(1500);
        // snmp版本
        target.setVersion(SnmpConstants.version2c);

        // 创建 PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.19849.1.6"),
                new OctetString("SnmpTrap")));
        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.19849.1.1"),
                new OctetString("JavaEE")));
        pdu.setType(PDU.TRAP);


        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.19849.1.2"),
                new OctetString("mem")));
        pdu.setType(PDU.TRAP);


        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.19849.1.3"),
                new OctetString("yingpamn")));
        pdu.setType(PDU.TRAP);


        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.1.0"),
                new OctetString("desc")));
        pdu.setType(PDU.TRAP);


        // 向Agent发送PDU，并接收Response
        ResponseEvent respEvnt = snmp.send(pdu, target);

        // 解析Response
        if (respEvnt != null && respEvnt.getResponse() != null) {
            Vector<VariableBinding> recVBs = respEvnt.getResponse()
                    .getVariableBindings();
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());
            }
        }
    }

}