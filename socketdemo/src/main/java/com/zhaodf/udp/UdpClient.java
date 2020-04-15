package com.zhaodf.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 类：UdpClient
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class UdpClient {
    public static void main(String[] args) throws IOException {
        //=======================给服务端发送数据==========================
        //1、要发送的数据
        byte [] data = "用户名：ck，密码：1314".getBytes();
        //2、创建数据报
        //参数解释：1、要发送的数据的byte数组；2、从数组的指定位置开始发送；3、要发送数组的长度；4、目的地的address；5、目的地的监听端口号
        DatagramPacket datagramPacket = new DatagramPacket(data, 0, data.length, InetAddress.getByName("localhost"), 8888);
        DatagramSocket socket = new DatagramSocket();
        //3、给服务端发送数据报文
        socket.send(datagramPacket);

        //=======================接收服务端发送的数据=======================
        //1、创建数据报，接收来自客户端的请求
        byte [] data1 = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data1,data1.length);
        //2、接收数据报
        socket.receive(packet);
        String rcv = new String(packet.getData());
        System.out.println("我是客户端，接受到服务端的数据=" + rcv);
    }
}
