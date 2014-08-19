package mib.microservices.util;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

public abstract class CommandBase<V> {
	private Producer<String, V> producer;
	private String topic;
	
	public CommandBase(String topic, Producer<String, V> producer) {
		this.topic = topic;
		this.producer = producer;
	}
	
	protected void send(String key, V value) {
		this.producer.send(new KeyedMessage<String, V>(this.topic, key, value));
	}
}
