package com.demo.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by huangjinlong7 on 2017/9/1.
 */
public class RmiDemoImpl extends UnicastRemoteObject implements RmiDemoInterface {


    protected RmiDemoImpl() throws RemoteException {
    }

    @Override
    public void test() throws RemoteException {

        System.out.println("Hello RMI");

    }
}
