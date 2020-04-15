package com.zhaod.rmi.register;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author zhaodf
 * @date 2019/7/18
 */
public interface HelloRegistryFacade extends Remote {
    String sayHello(String name) throws RemoteException;
}
