package mib.microservices.testservice;

import mib.microservices.util.Command;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

public class PingCommandJson implements Command<String, A> {
	private Producer<String, A> producer;

	public PingCommandJson(Producer<String, A> producer) {
		this.producer = producer;
	}
	
	@Override
	public void execute(String key, A value) {
		System.out.println("Got json: " + value.s);
		
		this.producer.send(new KeyedMessage<String, A>("ping", value));
	}
}
