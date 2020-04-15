package com.zhaodf.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 类：UdpServer
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class UdpServer {
    public static void main(String[] args) throws IOException {
        //1、创建指定端口号的DatagramSocket
        DatagramSocket socket = null;
        try {
            //=================从客户端接收数据==========================
            socket = new DatagramSocket(8888);
            //2、创建数据报，接收来自客户端的请求
            byte [] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            socket.receive(packet);
            String rcv = new String(packet.getData());
            System.out.println("我是服务端，接受到客户端的数据=" + rcv);

            //=================给客户端发送数据==========================
            //1、获取inetAddress、端口号
            InetAddress inetAddress = packet.getAddress();
            int port = packet.getPort();
            //2、给客户端发送数据
            byte [] data1 = "欢迎你".getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(data1, 0, data1.length, inetAddress,port);
            //3、发送
            socket.send(datagramPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}