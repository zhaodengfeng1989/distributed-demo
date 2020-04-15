package com.zhaodf.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 类：ZookeeperApiDemo
 *
 * @author zhaodf
 * @date 2019/8/2
 */
public class ZookeeperApiDemo {
    private static final String CONNECTSTRING = "192.168.202.128:2181,192.168.202.130:2181,192.168.202.131:2181";
    private static final int sessionTimeOut = 5000;
    private CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zooKeeper;

    public ZookeeperApiDemo() throws IOException {
        zooKeeper = new ZooKeeper(CONNECTSTRING, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                String path = watchedEvent.getPath();
                Event.EventType eventType = watchedEvent.getType();
                Event.KeeperState keeperState = watchedEvent.getState();
                if (Event.KeeperState.SyncConnected.equals(keeperState)) {
                    latch.countDown();
                    System.out.println("=======zk连接成功");
                }
            }
        });
    }

    public ZooKeeper getZooKeeper() throws InterruptedException {
        latch.await();
        return zooKeeper;
    }

    public void closeZookeeper() throws InterruptedException {
        if(zooKeeper!=null){
            zooKeeper.close();
            System.out.println("========zk连接关闭");
        }
    }

    //创建节点
    public String createNode(String path,String data) throws KeeperException, InterruptedException {
        String node = zooKeeper.create(path,data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        return node;
    }

    //节点数据更新
    public Stat updateNode(String path,String data) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.setData(path,data.getBytes(),-1);
        return stat;
    }

    //获取节点数据
    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] bytes = zooKeeper.getData(path,false,null);
        return new String(bytes);
    }

    //获取子节点
    public List<String> getChildrenNode(String path) throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(path,false);
        return children;
    }

    //删除节点
    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path,-1);
    }

    //判断节点是否存在
    public Stat exist(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path,false);
        return stat;
    }

    public static void main(String[] args) {
        try {
            ZookeeperApiDemo zookeeperApiDemo = new ZookeeperApiDemo();
            //1、创建节点
            //String node = zookeeperApiDemo.createNode("/zhaodf","123");
            //System.out.println(node);

            //2、查询节点的状态信息
            //Stat stat = zookeeperApiDemo.exist("/zhaodf");
            //System.out.println(stat.toString());

            //3、获取节点数据
            //String data = zookeeperApiDemo.getData("/zhaodf");
            //System.out.println(data);

            //4、创建子节点
            //String childNode1 = zookeeperApiDemo.createNode("/zhaodf/zhaodf-1","123");
            //System.out.println(childNode1);
            //String childNode2 = zookeeperApiDemo.createNode("/zhaodf/zhaodf-2","456");
            //System.out.println(childNode2);

            //5、获取子节点列表
            List<String> childrenNodes = zookeeperApiDemo.getChildrenNode("/zhaodf");
            System.out.println(childrenNodes.toString());

            //6、节点数据更新
            Stat stat = zookeeperApiDemo.updateNode("/zhaodf/zhaodf-1","789");
            System.out.println(stat);

            //7、删除节点
            zookeeperApiDemo.deleteNode("/zhaodf/zhaodf-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
