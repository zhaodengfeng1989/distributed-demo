package com.zhaod.rmi.naming;

import com.zhaod.rmi.register.HelloRegistryFacade;

import java.net.MalformedURLException;
import java.rmi.Naming;
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
public class NamingClient {
    public static void main(String[] args) {
        try {
            //根据服务地址，在注册表中查找对应的服务
            HelloRegistryFacade helloRegistryFacade = (HelloRegistryFacade) Naming.lookup("rmi://host:8888/helloRegistryFacade");
            //调用远程对象的方法，输出结果
            String response = helloRegistryFacade.sayHello("fengge");
            System.out.println("===调用服务成功====="+response);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
