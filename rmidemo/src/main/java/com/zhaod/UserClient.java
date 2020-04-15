package com.zhaod;

import java.io.IOException;

/**
 * 类：UserServer
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class UserClient extends User {
    public static void main(String[] args) throws IOException {
        User user = new User_Stub();
        int age = user.getAge();
        System.out.println(age);
    }
}
