package mib.microservice.commons.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;


public class Consumer<K, V> implements Runnable {
	private KafkaStream<K, V> stream;
	private Command<K, V> command;
	
	public Consumer(KafkaStream<K, V> stream, Command<K, V> command) {
		this.stream = stream;
		this.command = command;
	}
	
	@Override
	public void run() {
		ConsumerIterator<K, V> it = this.stream.iterator();
		while (it.hasNext()) {
			MessageAndMetadata<K, V> msg = it.next();
			
			System.out.println("p" + msg.partition() + " @" + msg.offset());
			
			this.command.execute(msg.key(), msg.message());
		}
	}
}