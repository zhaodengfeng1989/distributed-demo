package com.zhaodf.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * 类：ZkClientDemo
 *
 * @author zhaodf
 * @date 2019/8/6
 */
public class ZkClientDemo {
    private static final String CONNECTSTRING = "192.168.202.128:2181,192.168.202.130:2181,192.168.202.133:2181";
    private ZkClient zkClient;

    public ZkClientDemo() {
        this.zkClient = getInstance();
    }

    private ZkClient getInstance(){
        //使用默认的序列化器
        return new ZkClient(CONNECTSTRING, 5000);
    }

    //创建节点（增加节点事件的监听--事件订阅）
    public String createNode(String path, final String data) {
        //创建有两种方式：①直接调用create，指定创建的模式 ②直接调用对应的createxxx(递归创建父节点的功能)
        String node = zkClient.create(path,data,CreateMode.PERSISTENT);
        //事件订阅
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath+"节点数据发生变化了："+data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath+"节点被删除了！");
            }
        });
        //递归创建节点
        //zkClient.createPersistent(path,true);
        return node;
    }

    //节点数据更新
    public void updateNode(String path, String data){
        zkClient.writeData(path,data,-1);
    }

    //获取节点数据
    public String getData(String path){
        String data = zkClient.readData(path);
        return data;
    }

    //获取子节点
    public List<String> getChildrenNode(String path){
        List<String> children = zkClient.getChildren(path);
        return children;
    }

    //删除节点
    public void deleteNode(String path){
        zkClient.delete(path,-1);
        //级联(递归)删除
        //zkClient.deleteRecursive(path);
    }

    //判断节点是否存在
    public boolean exist(String path) throws KeeperException, InterruptedException {
        boolean isExist = zkClient.exists(path);
        return isExist;
    }

    public static void main(String[] args) throws InterruptedException, KeeperException {
        ZkClientDemo demo = new ZkClientDemo();
        //1、创建节点
        //String node = demo.createNode("/zhaodf3","123");
        //System.out.println(node);

        //2、更新节点数据，并且订阅事件
        //demo.updateNode("/zhaodf3","7890");
        //TimeUnit.SECONDS.sleep(2);

        //3、获取子节点列表
        //List<String> nodeChildren = demo.getChildrenNode("/zhaodf");
        //System.out.println(nodeChildren);

        //4、获取节点数据
        //String data = demo.getData("/zhaodf1");
        //System.out.println(data);

        //5、判断节点是否存在
        //boolean isExist = demo.exist("/zhaodf");
        //System.out.println(isExist);

        //6、删除节点
        demo.deleteNode("/zhaodf1");
    }
}
