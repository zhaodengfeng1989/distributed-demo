package com.zhaod.rmi.register;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 类：RegistryService
 *
 * @author zhaodf
 * @date 2019/7/18
 */
public class RegistryClient {
    public static void main(String[] args) {
        try {
            // 获取远程主机上的远程对象注册表Registry的实例,默认端口8888
            Registry registry = LocateRegistry.getRegistry(8888);
            //在注册表中查找对应的服务
            HelloRegistryFacade helloRegistryFacade = (HelloRegistryFacade) registry.lookup("helloRegistryFacade");
            //调用远程对象的方法，输出结果
            String response = helloRegistryFacade.sayHello("fengge");
            System.out.println("===调用服务成功====="+response);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
