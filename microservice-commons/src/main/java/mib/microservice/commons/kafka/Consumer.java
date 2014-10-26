package mib.microservice.commons.kafka;

import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import mib.microservice.commons.events.IEventConsumer;
import mib.microservice.commons.events.base.EventBase;


public class Consumer<K, V extends EventBase<?>> implements Runnable {
	private KafkaStream<K, V> stream;
	private IEventConsumer<K, V> command;
	
	public Consumer(KafkaStream<K, V> stream, IEventConsumer<K, V> command) {
		this.stream = stream;
		this.command = command;
	}
	
	@Override
	public void run() {
		for (MessageAndMetadata<K, V> msg : this.stream) {
			System.out.println("p" + msg.partition() + " @" + msg.offset());
			
			this.command.execute(msg.key(), msg.message());
		}
	}
}