package com.zhaodf.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 类：MasterSelector
 *
 * @author zhaodf
 * @date 2019/8/1
 */
public class MasterSelector {
    private ZkClient zkClient;
    private final static String MASTER_PATH="/master";
    //注册节点内容变化
    private IZkDataListener dataListener;
    //其他服务器
    private UserCenter server;
    //master服务器
    private UserCenter master;

    public MasterSelector(UserCenter server) {
        this.server = server;
        this.dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //节点如果被删除，发起一个选主操作

            }
        };
    }
}
