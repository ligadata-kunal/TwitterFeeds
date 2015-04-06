package com.ligadata.twitterfeeds.zookeeperclient;

import org.apache.curator.framework.CuratorFramework;

import com.ligadata.twitterfeeds.zookeeperclient.utl.ServicesFields;
import com.ligadata.twitterfeeds.zookeeperclient.utl.ServicesUtil;



public class Client {
	private static CuratorFramework client;

	public Client(String zkConnection) {
		client = ServicesUtil.createSimpleClient(zkConnection);

		client.start();
	}

	public void start() throws Exception {
		ServicesUtil.setValue(client, ServicesFields.ACTION, "1");
	}

	public void stop() throws Exception {
		ServicesUtil.setValue(client, ServicesFields.ACTION, "0");
	}

	public String getData() throws Exception {
		return ServicesUtil.getValue(client, ServicesFields.DATA);
	}

	public void setData(String value) throws Exception {
		ServicesUtil.setValue(client, ServicesFields.DATA, value);
	}

	public void close() throws Exception {
		client.close();
	}
}
