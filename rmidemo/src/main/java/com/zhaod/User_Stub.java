package com.zhaod;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 类：User_Stub
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class User_Stub extends User {
    private Socket socket;

    public User_Stub() throws IOException {
        socket = new Socket("localhost",8888);
    }

    @Override
    public int getAge() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject("age");
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return objectInputStream.readInt();
    }
}
