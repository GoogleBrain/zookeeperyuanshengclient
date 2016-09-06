package com.day01;

import org.apache.zookeeper.AsyncCallback.StringCallback;

public class MyStringCallBack implements StringCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.print(rc + "," + path + "," + ctx + "," + name);
	}

}
