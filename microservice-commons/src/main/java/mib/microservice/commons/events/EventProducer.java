package mib.microservice.commons.events;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import mib.microservice.commons.events.base.EventBase;

public class EventProducer<TOut extends EventBase<?>> implements IEventProducer<TOut> {
	private final Producer<String, TOut> producer;
	
	public EventProducer(final ProducerConfig producerConfig) {
		this.producer = new Producer<>(producerConfig);
	}
	
	@Override
	public void raise(final TOut event) {
		this.producer.send(new KeyedMessage<String, TOut>(event.getEventId(), event));
	}
}
