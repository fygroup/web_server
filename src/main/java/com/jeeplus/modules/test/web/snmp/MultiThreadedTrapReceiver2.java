
package com.jeeplus.modules.test.web.snmp;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;
    public class MultiThreadedTrapReceiver2{
        public static void main(String[] args){

            try{
                //snmp4j通过transportmapping的监听端口接收SNMP信息,所以这里初始化一个
                //transportmapping,
                //注明本机的IP地址及接收trap的端口.
                TransportMapping transport = new DefaultUdpTransportMapping(new UdpAddress("139.0.2.25/163"));
                //创建一个处理消息的snmp实例
                Snmp snmp = new Snmp(transport);
                //CommandResponder是一个listener,用以处理获取的trap消息
                CommandResponder trapPrinter = new CommandResponder(){
                    public synchronized void processPdu(CommandResponderEvent e){
                        PDU command = e.getPDU();
                        if(command!=null){
                            //这里示例输出trap的内容.具体的trap解析等工作在这里进行.
                            System.out.println(command.toString());

                        }
                    }
                };

                //在snmp实例中添加CommandResponder listener
                snmp.addCommandResponder(trapPrinter);

                System.out.println("start listening!");
                //开始启动trap监听.listen()方法内部启动了一个线程,这个线程监听发送到transport中定义的端口
                //的消息.
                transport.listen();
                System.out.println(transport.isListening());//测试监听是否正常




                //等待一段测试时间,在这段时间可以发送trap信息测试.
                Thread.sleep(61*1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }