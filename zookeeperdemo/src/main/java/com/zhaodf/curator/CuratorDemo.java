package com.zhaodf.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 类：CuratorDemo
 *
 * @author zhaodf
 * @date 2019/8/6
 */
public class CuratorDemo {
    private static final String CONNECTSTRING = "192.168.202.128:2181,192.168.202.130:2181,192.168.202.133:2181";

    private CuratorFramework client;

    public CuratorDemo() {
        this.client = getInstance();
        //当创建会话成功，得到client的实例然后可以直接调用其start( )方法：
        this.client.start();
    }

    private static CuratorFramework getInstance(){
        //创建客户端api连接有两种方式，普通的和fluent风格的
        //1、普通风格的
        //client = CuratorFrameworkFactory.newClient(CONNECTSTRING,5000,5000,new ExponentialBackoffRetry(10000,3));
        //2、Fluent风格的,命名空间指定根节点
        return CuratorFrameworkFactory.builder().
                namespace("zhaodf").
                connectString(CONNECTSTRING).
                sessionTimeoutMs(5000).
                connectionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(60000,3)).build();
    }

    //创建节点（增加节点事件的监听--事件订阅）
    public String createNode(String path, final String data) throws Exception {
        return client.create().withMode(CreateMode.PERSISTENT).forPath(path,data.getBytes());
    }

    //节点数据更新
    public void updateNode(String path, String data) throws Exception {
        client.setData().forPath(path,data.getBytes());
    }

    //获取节点数据
    public String getData(String path) throws Exception {
        //1、读取一个节点的数据内容，同时获取到该节点的stat:client.getData().storingStatIn(stat).forPath("path");
        //2、普通读取：byte[] bytes = client.getData().forPath("path");
        byte[] bytes = client.getData().forPath(path);
        return new String(bytes);
    }

    //获取子节点
    public List<String> getChildrenNode(String path) throws Exception {
        List<String> children = client.getChildren().forPath(path);
        return children;
    }

    //删除节点
    public void deleteNode(String path) throws Exception {
        //1、递归删除：client.delete().deletingChildrenIfNeeded().forPath("path");
        //2、指定版本：client.delete().withVersion(10086).forPath("path");
        //3、强制保证删除:guaranteed()接口是一个保障措施，只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到删除节点成功。
        // client.delete().guaranteed().forPath("path");
        //4、自由组合：client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(10086).forPath("path");
        client.delete().forPath(path);
    }

    //判断节点是否存在
    public Stat exist(String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return stat;
    }

    //事务操作
    //CuratorFramework的实例包含inTransaction( )接口方法，调用此方法开启一个ZooKeeper事务.
    // 可以复合create, setData, check, and/or delete 等操作然后调用commit()作为一个原子操作提交。
    public void inTransaction(String path1,String path2,String data) throws Exception {
        client.inTransaction().create().withMode(CreateMode.PERSISTENT).forPath(path1, data.getBytes())
                .and().setData().forPath(path2,data.getBytes())
                .and().commit();
    }

    public CuratorFramework getClient() {
        return client;
    }

    //事件监听

    public static void main(String[] args) throws Exception {
        CuratorDemo demo = new CuratorDemo();
        //1、创建节点
        //String node = demo.createNode("/zhaodf4","1234");
        //System.out.println(node);

        //2、修改数据
        //demo.updateNode("/zhaodf4","5678");

        //3、获取节点数据
        //String data = demo.getData("/zhaodf4");
        //System.out.println(data);

        //4、获取子节点列表
        //List<String> childrenNode = demo.getChildrenNode("/");
        //System.out.println(childrenNode.toString());

        //5、判断节点是否存在
        //Stat stat = demo.exist("/zhaodf4");
        //System.out.println(stat.toString());

        //6、删除节点
        //demo.deleteNode("/zhaodf4");

        //7、事务操作
        //demo.inTransaction("/zhaodf5","/zhaodf-2","456");

        //8、事件监听(使用Watcher，一次性监听)
//        CuratorFramework client = demo.getClient();
//        Watcher watcher = new Watcher() {
//            @Override
//            public void process(WatchedEvent watchedEvent) {
//                System.out.println("监听到的变化："+watchedEvent);
//            }
//        };
//        byte[] bytes = client.getData().usingWatcher(watcher).forPath("/zhaodf5");
//        System.out.println("监听节点内容："+new String(bytes));
//
//        client.setData().forPath("/zhaodf5","9087".getBytes());
//
//        client.setData().forPath("/zhaodf5","8778".getBytes());
//
//        TimeUnit.SECONDS.sleep(2);

        //9、使用NodeCache
//        //①构造NodeCache
//        final NodeCache nodeCache = new NodeCache(demo.getClient(),"/zhaodf6",false);
//        //②构造
//        NodeCacheListener listener = new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                ChildData childData = nodeCache.getCurrentData();
//                System.out.println("节点数据发生变化："+childData.getPath()+"--->"+new String(childData.getData()));
//            }
//        };
//        //③注册监听，并启动nodeCache
//        nodeCache.getListenable().addListener(listener);
//        nodeCache.start();
//        demo.createNode("/zhaodf6","1234");
//        //④节点数据变化多次
//        demo.updateNode("/zhaodf6","78976");
//        TimeUnit.SECONDS.sleep(2);
//        demo.updateNode("/zhaodf6","93648");
//        TimeUnit.SECONDS.sleep(2);
        //10、使用PathChildrenCache
//        //①构造PathChildrenCache
//        PathChildrenCache pathChildrenCache = new PathChildrenCache(demo.getClient(),"/zhaodf5",true);
//        //②构造监听
//        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//                ChildData childData = pathChildrenCacheEvent.getData();
//                switch (pathChildrenCacheEvent.getType()){
//                    case CHILD_ADDED:
//                        System.out.println("子节点增加："+childData.getPath()+"--->"+new String(childData.getData()));
//                        break;
//                    case CHILD_UPDATED:
//                        System.out.println("子节点更新："+childData.getPath()+"--->"+new String(childData.getData()));
//                        break;
//                    case CHILD_REMOVED:
//                        System.out.println("子节点移除："+childData.getPath()+"--->"+new String(childData.getData()));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//        pathChildrenCache.getListenable().addListener(listener);
//        pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
//        //③新增子节点、更新子节点内容、移除子节点
//        demo.createNode("/zhaodf5/zhaodf5-1","1234");
//        TimeUnit.SECONDS.sleep(1);
//        demo.updateNode("/zhaodf5/zhaodf5-1","4567");
//        TimeUnit.SECONDS.sleep(20);
//        demo.deleteNode("/zhaodf5/zhaodf5-1");
        //11、使用TreeCache
        //1、构造TreeCache
        TreeCache treeCache = new TreeCache(demo.getClient(),"/zhaodf7");
        //2、构造监听
        TreeCacheListener listener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                ChildData childData = treeCacheEvent.getData();
                switch (treeCacheEvent.getType()){
                    case NODE_ADDED:
                        System.out.println("节点增加(包含子节点)："+childData.getPath()+"--->"+new String(childData.getData()));
                        break;
                    case NODE_UPDATED:
                        System.out.println("节点更新(包含子节点)："+childData.getPath()+"--->"+new String(childData.getData()));
                        break;
                    case NODE_REMOVED:
                        System.out.println("节点移除(包含子节点)："+childData.getPath()+"--->"+new String(childData.getData()));
                        break;
                    default:
                        break;
                }
            }
        };
        treeCache.getListenable().addListener(listener);
        treeCache.start();
        //3、创建节点（子节点）、更新、删除节点或子节点
        demo.createNode("/zhaodf7","12345");
        TimeUnit.SECONDS.sleep(1);

        demo.createNode("/zhaodf7/zhaodf7-1","12345");
        TimeUnit.SECONDS.sleep(10);

        demo.updateNode("/zhaodf7","8776674");
        TimeUnit.SECONDS.sleep(1);

        demo.updateNode("/zhaodf7/zhaodf7-1","98494");
        TimeUnit.SECONDS.sleep(20);

        demo.deleteNode("/zhaodf7/zhaodf7-1");
        TimeUnit.SECONDS.sleep(10);

        demo.deleteNode("/zhaodf7");
    }
}
