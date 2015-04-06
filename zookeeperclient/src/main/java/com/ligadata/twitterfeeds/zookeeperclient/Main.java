package com.ligadata.twitterfeeds.zookeeperclient;

public class Main {

	public static void main(String[] args) throws Exception {
		Client c = new Client("localhost:2181");
		System.out.print(c.getData());
		c.close();

	}

}
