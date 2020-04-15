package com.zhaodf.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * 类：MulticastClient2
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class MulticastClient2 {
    public static void main(String[] args){
        try {
            //跟服务端的组得一样
            InetAddress group = InetAddress.getByName("224.5.6.7");
            MulticastSocket multicastSocket = new MulticastSocket(8888);
            //加到指定的组里面
            multicastSocket.joinGroup(group);
            byte[] buf = new byte[256];
            while (true){
                DatagramPacket packet = new DatagramPacket(buf,buf.length);
                multicastSocket.receive(packet);
                String msg = new String(packet.getData());
                System.out.println("客户端2接收到的消息："+msg);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
