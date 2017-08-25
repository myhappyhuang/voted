package com.demoredis.voted;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huangjinlong7 on 2017/8/24.
 */
public class ZookeeperDemon {
    static final String CONNECT_ADDR = "10.33.242.51:2182,10.33.242.52:2182,10.33.242.57:2182";
    static final int SESSION_OUTTIME = 2000;//ms
    /** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher(){
            @Override
            public void process(WatchedEvent event) {
                //获取事件的状态
                KeeperState keeperState = event.getState();
                EventType eventType = event.getType();
                //如果是建立连接
                if(KeeperState.SyncConnected == keeperState){
                    if(EventType.None == eventType){
                        //如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                        connectedSemaphore.countDown();
                        System.out.println("zk 建立连接");
                    }
                }
            }
        });

        connectedSemaphore.await();

        System.out.println("..");
        zk.create("/testDemo", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/testDemo/testSon", "testSon".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(zk.getData("/testDemo", false, null));
        System.out.println(zk.getChildren("/testDemo", false));
        //创建父节点
//      zk.create("/testRoot", "testRoot".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //创建子节点，使用EPHEMERAL，主程序执行完成后该节点被删除，只在本次会话内有效，可以用作分布式锁。
//      zk.create("/testRoot/children", "children data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        //获取节点信息
//      byte[] data = zk.getData("/testRoot", false, null);
//      System.out.println(new String(data));
//      System.out.println(zk.getChildren("/testRoot", false));

        //修改节点的值，-1表示跳过版本检查，其他正数表示如果传入的版本号与当前版本号不一致，则修改不成功，删除是同样的道理。
//      zk.setData("/testRoot", "modify data root".getBytes(), -1);
//      byte[] data = zk.getData("/testRoot", false, null);
//      System.out.println(new String(data));

        //判断节点是否存在
//      System.out.println(zk.exists("/testRoot/children", false));
        //删除节点
//      zk.delete("/testRoot/children", -1);
//      System.out.println(zk.exists("/testRoot/children", false));

        zk.close();
    }
}
