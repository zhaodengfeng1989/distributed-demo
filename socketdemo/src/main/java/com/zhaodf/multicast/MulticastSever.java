package com.zhaodf.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 类：MulticastSever
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class MulticastSever {
    public static void main(String[] args){
        try {
            //定义组播的地址组
            InetAddress group = InetAddress.getByName("224.5.6.7");
            MulticastSocket multicastSocket = new MulticastSocket();
            for (int i=0;i<10;i++){
                String msg = "hello,zhaodf";
                byte[] bytes = msg.getBytes();
                multicastSocket.send(new DatagramPacket(bytes,bytes.length,group,8888));
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
