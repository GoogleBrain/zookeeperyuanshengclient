package com.day01;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class MyWatcher implements Watcher {
	private static boolean flag = false;

	@Override
	public void process(WatchedEvent event) {

		if (event.getState().equals(KeeperState.SyncConnected)) {
			if (!flag && event.getType() == EventType.None
					&& null == event.getPath()) {
				try {
					// synchCreate();
					// asynchCreate();
					// getChild2();
					// getChild3();
					// getValue();
					// getValue2();
					// delete();
					// delete2();
					// exist();
					// exist2();
					// synchUpdate();
					// synchCreate2();
					synchCreate3();
					// 防止掉线重连多次调用该方法；
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (event.getType().equals(EventType.NodeChildrenChanged)) {
					// 子节点发生变化
					try {
						List<String> lists = HelloWorld.zk.getChildren(
								event.getPath(), true);
						System.out.println("子节点发生变化>>>>>>" + lists);
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
				} else if (event.getType().equals(EventType.NodeDataChanged)) {
					// 节点值发生改变
					Stat stat = new Stat();
					try {
						System.out.print("节点值发生改变>>>>>"
								+ new String(HelloWorld.zk.getData(
										"/zookeeper/king", true, stat)));
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 权限问题
	public void synchCreate2() {

		String str;
		try {
			/**
			 * 如果是IP的话只允许127.0.0.1的机器访问这个节点或者通过用户名和密码远程访问；
			 */
			ACL acl = new ACL(Perms.READ, new Id("ip", "127.0.0.1"));
			ACL acl2 = new ACL(Perms.READ | Perms.WRITE, new Id("digest",
					DigestAuthenticationProvider.generateDigest("root:123456")));
			ArrayList<ACL> lists = new ArrayList<ACL>();
			lists.add(acl);
			lists.add(acl2);
			str = HelloWorld.zk.create("/zookeeper/a", "123".getBytes(), lists,
					CreateMode.PERSISTENT);
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 透过权限获取值
	public void synchCreate3() {
		try {
			Stat stat = new Stat();
			HelloWorld.zk.addAuthInfo("digest", "root:123456".getBytes());
			System.out.print(new String(HelloWorld.zk.getData("/zookeeper/a",
					false, stat)));
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 同步修改数据
	public void synchUpdate() throws KeeperException, InterruptedException {
		Stat str = HelloWorld.zk.setData("/zookeeper/king", "abc".getBytes(),
				-1);
		System.out.println(str);
	}

	// 异步修改数据
	public void synchUpdate2() throws KeeperException, InterruptedException {
		HelloWorld.zk.setData("/zookeeper/king", "def".getBytes(), -1,
				new MyStatCallBack(), null);
	}

	// 同步创建
	public void synchCreate() throws KeeperException, InterruptedException {
		String str = HelloWorld.zk.create("/zookeeper/a", "123".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(str);
	}

	// 异步创建
	public void asynchCreate() {
		HelloWorld.zk.create("/zookeeper/b", "123".getBytes(),
				Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
				new MyStringCallBack(), "asf");
	}

	// 同步获取，false表示不关心子节点的变化
	public void getChild() {
		try {
			List<String> lists = HelloWorld.zk.getChildren("/", false);
			System.out.println(lists);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 同步获取，true表示不关心子节点的变化
	public void getChild2() {
		try {
			List<String> lists = HelloWorld.zk.getChildren("/zookeeper", true);
			System.out.println("获取字节点>>>>>>" + lists);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 异步获取子节点
	public void getChild3() {
		HelloWorld.zk.getChildren("/", true, new MyChild2Callback(), null);
	}

	// 同步获取节点值。
	public void getValue() {
		Stat stat = new Stat();
		try {
			System.out.print(new String(HelloWorld.zk.getData(
					"/zookeeper/king", true, stat)));
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 异步获取节点值。
	public void getValue2() {
		HelloWorld.zk.getData("/zookeeper/king", true, new MyDataCallback(),
				null);
	}

	// 同步删除某个节点
	public void delete() {
		try {
			HelloWorld.zk.delete("/zookeeper/king", -1);
		} catch (InterruptedException | KeeperException e) {
			e.printStackTrace();
		}
	}

	// 异步删除某个节点
	public void delete2() {
		HelloWorld.zk.delete("/zookeeper/king", -1, new MyVoidCallBack(), null);
	}

	// 同步判断某个节点是否存在
	public void exist() {
		try {
			Stat stat = HelloWorld.zk.exists("/zookeeper", false);
			System.out.print(stat);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 异步判断某个节点是否存在
	public void exist2() {
		HelloWorld.zk.exists("/zookeeper", false, new MyStatCallBack(), null);
	}
}
