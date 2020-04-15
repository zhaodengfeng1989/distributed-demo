package com.zhaod;

/**
 * 类：UserServer
 *
 * @author zhaodf
 * @date 2019/7/17
 */
public class UserServer extends User {
    public static void main(String[] args) {
        UserServer userServer = new UserServer();
        userServer.setAge(18);

        User_Skeleton userSkeleton = new User_Skeleton(userServer);
        userSkeleton.start();
    }
}
