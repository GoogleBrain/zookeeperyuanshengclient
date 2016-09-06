package com.day01;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.Stat;

public class MyStatCallBack implements StatCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		System.out.print("异步判断是否存在/修改是否成功>>>>>" + rc);
	}
}
