package com.zhaodf.javaapilock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * 类：LockWatcher
 *
 * @author zhaodf
 * @date 2019/7/31
 */
public class LockWatcher implements Watcher {
    private CountDownLatch latch;

    public LockWatcher(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType()==Event.EventType.NodeDeleted){

        }
    }
}
