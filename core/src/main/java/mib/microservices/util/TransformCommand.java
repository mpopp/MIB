package mib.microservices.util;

import kafka.javaapi.producer.Producer;

public class TransformCommand<TIn, TOut> extends CommandBase<TOut> implements Command<String, TIn> {
	private Transformer<TIn, TOut> transformer;
	public TransformCommand(
			String topic,
			Producer<String, TOut> producer,
			Transformer<TIn, TOut> transformer) {
		super(topic, producer);
		this.transformer = transformer;
	}
	
	@Override
	public void execute(String key, TIn value) {
		super.send(key, this.transformer.apply(value));
	}
}
