package com.day01;

import org.apache.zookeeper.AsyncCallback.VoidCallback;

public class MyVoidCallBack implements VoidCallback {

	@Override
	public void processResult(int rc, String path, Object ctx) {
		System.out.println("异步删除>>>>>>>" + path);
	}

}
