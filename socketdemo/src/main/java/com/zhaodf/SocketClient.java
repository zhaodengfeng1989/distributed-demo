package com.zhaodf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 类：SocketClient
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("localhost",8888);
            //客户端发送数据
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            writer.println(System.currentTimeMillis()+"---->hello,服务端，你收到了吗？");

            //客户端读取数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //读取数据
            System.out.println(reader.readLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
