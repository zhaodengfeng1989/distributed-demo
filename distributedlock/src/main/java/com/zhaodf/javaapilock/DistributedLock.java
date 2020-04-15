package com.zhaodf.javaapilock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 类：DistributedLock
 *
 * @author zhaodf
 * @date 2019/7/31
 */
public class DistributedLock {
    private static final String ROOT_LOCKS="/LOCKS";
    private ZooKeeper zooKeeper;
    private int sessionTimeOut;
    private String lockId;
    private final static byte[] data = {1,2};
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistributedLock() throws IOException, InterruptedException {
        this.zooKeeper = ZookeeperClient.getInstance();
        this.sessionTimeOut = ZookeeperClient.getSessionTimeOut();
    }

    public boolean lock(){
        try {
            lockId = zooKeeper.create(ROOT_LOCKS+"/",data,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+"成功创建了lock节点【"+lockId+"】,开始去竞争");
            //获取根节点下的所有子节点，每个客户端都会在根节点下创建一个临时有序的子节点
            List<String> childrenNodes = zooKeeper.getChildren(ROOT_LOCKS,true);
            //排序，从小到大，只有最小id的节点可以访问
            SortedSet<String> sortedSet = new TreeSet<String>();
            for (String children:childrenNodes
                 ) {
                sortedSet.add(children);
            }
            //拿到最小的节点id
            String first = sortedSet.first();
            if(lockId.equals(first)){
                System.out.println(Thread.currentThread().getName()+"成功获得锁，lock节点为："+lockId);
                return true;
            }
            SortedSet<String> lessThanLockId =  sortedSet.headSet(lockId);
            if (!lessThanLockId.isEmpty()) {
                String prevLockId = lessThanLockId.last();
                zooKeeper.exists(prevLockId,new LockWatcher(countDownLatch));
                countDownLatch.await(sessionTimeOut,TimeUnit.MILLISECONDS);
                //上面这段代码意味着超时或者节点被删除释放
                System.out.println(Thread.currentThread().getName()+"成功获得锁："+lockId);
                return true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlock(){
        System.out.println(Thread.currentThread().getName()+"开始释放锁："+lockId);
        try {
            zooKeeper.delete(lockId,-1);
            System.out.println("节点"+lockId+"成功删除");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i <10 ; i++) {

        }
    }
}
