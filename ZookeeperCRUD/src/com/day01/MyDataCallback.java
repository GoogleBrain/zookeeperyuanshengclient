package com.day01;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.data.Stat;

public class MyDataCallback implements DataCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, byte[] data,
			Stat stat) {
		System.out.print(new String(data));
		System.out.println(stat);
	}
}
