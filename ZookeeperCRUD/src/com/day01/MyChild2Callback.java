package com.day01;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.Children2Callback;
import org.apache.zookeeper.data.Stat;

public class MyChild2Callback implements Children2Callback {

	@Override
	public void processResult(int rc, String path, Object ctx,
			List<String> children, Stat stat) {
		System.out.print(rc + " " + path + " " + children);
	}
}
