package com.zhaodf;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 类：SocketServer
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(8888);
            //接收请求
            Socket socket = serverSocket.accept();
            //使用bufferReader读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //读取数据
            System.out.println(reader.readLine());

            //发送数据
            PrintWriter writer =
                    new PrintWriter(new OutputStreamWriter( socket.getOutputStream()), true);
            writer.println(System.currentTimeMillis()+"---->hello,客户端，我收到了，你真烦人");

        }catch (Exception e){

        }finally {
            if (serverSocket!=null){
                serverSocket.close();
            }
        }
    }
}