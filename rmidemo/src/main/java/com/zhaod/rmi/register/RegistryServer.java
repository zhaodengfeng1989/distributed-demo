package com.zhaod.rmi.register;

import com.zhaod.rmi.register.impl.HelloRegistryFacadeImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 类：RegistryService
 *
 * @author zhaodf
 * @date 2019/7/18
 */
public class RegistryServer {
    public static void main(String[] args) {
        try {
            // 本地主机上的远程对象注册表Registry的实例,默认端口8888
            Registry registry = LocateRegistry.createRegistry(8888);
            //创建一个远程对象
            HelloRegistryFacade helloRegistryFacade = new HelloRegistryFacadeImpl();
            //把远程对象注册到RMI注册服务器上，并命名为helloRegistryFacade
            registry.rebind("helloRegistryFacade",helloRegistryFacade);
            System.out.println("==========启动RMI服务成功=========");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
