package mib.microservices.util;

import kafka.javaapi.producer.Producer;

public class FilterCommand<V> extends CommandBase<V> implements Command<String, V> {
	private Transformer<V, Boolean> predicate;

	public FilterCommand(String topic, Producer<String, V> producer, Transformer<V, Boolean> predicate) {
		super(topic, producer);
		this.predicate = predicate;
	}
	
	@Override
	public void execute(String key, V value) {
		if(this.predicate.apply(value)) {
			super.send(key, value);
		}
	}
}
