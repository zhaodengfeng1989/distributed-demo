package com.zhaod.rmi.register.impl;

import com.zhaod.rmi.register.HelloRegistryFacade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 类：HelloRegistryFacadeImpl
 *
 * @author zhaodf
 * @date 2019/7/18
 */
public class HelloRegistryFacadeImpl extends UnicastRemoteObject implements HelloRegistryFacade {

    public HelloRegistryFacadeImpl() throws RemoteException{
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "你好," + name;
    }
}
