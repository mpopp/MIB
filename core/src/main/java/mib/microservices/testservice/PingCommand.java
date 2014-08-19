package mib.microservices.testservice;

import mib.microservices.util.Command;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

public class PingCommand implements Command<String, A> {
	private Producer<String, String> producer;

	public PingCommand(Producer<String, String> producer) {
		this.producer = producer;
	}
	
	@Override
	public void execute(String key, A value) {
		System.out.println("Got message: " + value.s);
		
		this.producer.send(new KeyedMessage<String, String>("ping", value.s));
	}
};