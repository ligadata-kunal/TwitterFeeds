/**
 * Copyright 2013 Twitter, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.ligadata.twitterfeeds.kafkaproducer;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class FilterStreamExample {

	private static final String COMMA_DELIMITER = ",";

	public static void run(String consumerKey, String consumerSecret,
			String token, String secret, String hashtag)
			throws InterruptedException {
		final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(
				10000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH-mm-ss");
		Date date = new Date();

		// add some track terms
		endpoint.trackTerms(Lists.newArrayList("twitterapi", hashtag));

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token,
				secret);
		// Authentication auth = new BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		final Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();

		ExecutorService exec = Executors.newFixedThreadPool(1);
		final KafkaProducer producer = new KafkaProducer();

		exec.execute(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				// try {
				while (true) {
					// synchronized (msg) {
					JSONObject json;
					try {
						String msg = queue.take();

						StringBuffer str = new StringBuffer();
						json = new JSONObject(msg);
						str.append("System.PlusStringStringTestMsg");
						str.append(COMMA_DELIMITER);
						str.append(json.get("id"));
						str.append(COMMA_DELIMITER);
						str.append(json.get("text"));
						// str.append(COMMA_DELIMITER);
						// str.append(""+count(json.get("text").toString()));
						if (msg != null) {
							// producer.send(str.toString());
						}
						System.out.println(str.toString());
					} catch (Exception e) {
						continue;
						// e.printStackTrace();
						// client.stop();
					}

				}
				// }
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				//
			}
		});

		// client.stop();

	}

	public static int count(String text) {

		String[] wordArray = text.split("\\s+");
		int wordCount = wordArray.length;

		return wordCount;
	}
}
