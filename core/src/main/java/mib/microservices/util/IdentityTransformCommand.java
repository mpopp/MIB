package mib.microservices.util;

import kafka.javaapi.producer.Producer;

public class IdentityTransformCommand<T> extends TransformCommand<T, T> {
	public IdentityTransformCommand(String topic, Producer<String, T> producer,
			Transformer<T, T> transformer) {
		super(topic, producer, new Transformer<T, T>() {
			@Override
			public T apply(T value) {
				return value;
			}
		});
	}
}