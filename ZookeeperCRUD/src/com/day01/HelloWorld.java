package com.day01;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class HelloWorld {

	public static ZooKeeper zk;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		zk = new ZooKeeper("127.0.0.1:2181", 5000, new MyWatcher());
		System.out.println(zk.getState());

		// 异步连接，如果不加sleep，等不到服务端返回的信息，main方法就结束了。
		Thread.sleep(40000);
	}
}
