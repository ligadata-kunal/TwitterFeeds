package com.ligadata.twitterfeeds.kafkaproducer;

import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
	private ProducerConfig config = null;
	private Producer<String, String> producer = null;
	private static final String ip = "127.0.0.1";
	private static final String topic = "testin_1";
	
	public KafkaProducer() {

		Properties props = new Properties();
		props.put("metadata.broker.list", "broker0:9092,localhost:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "com.ligadata.twitterfeeds.kafkaproducer.SimplePartitioner");
		props.put("request.required.acks", "1");

		config = new ProducerConfig(props);
		producer = new Producer<String, String>(config);
	}

	public void send(String msg) {

		KeyedMessage<String, String> data = new KeyedMessage<String, String>(
				topic, ip, msg);
		producer.send(data);
	}
	
	public void close() {
		
		producer.close();
	}

}
