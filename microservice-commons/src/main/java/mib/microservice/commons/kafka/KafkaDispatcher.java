package mib.microservice.commons.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;
import mib.microservice.commons.cli.CLI;
import mib.microservice.commons.events.base.EventBase;
import mib.microservices.util.Command;
import mib.microservices.util.Consumer;
import mib.microservices.util.JsonDecoder;

public class KafkaDispatcher<TIn extends EventBase> implements IKafkaDispatcher<TIn> {
	private static final int threadsPerTopic = 1;
	
	private JsonDecoder<TIn> valueDecoder;
	private Decoder<String> keyDecoder;
	
	private ConsumerConfig consumerConfig;

	private IKafkaCommand<TIn> dispatcherCommand;

	private String topic;
	
	public KafkaDispatcher(
			final Class<TIn> eventClass,
			final CLI options,
			final IKafkaCommand<TIn> dispatcherCommand) {
		KafkaConfigParser configParser = new KafkaConfigParser();
		configParser.parseConfig(options);
		
		this.consumerConfig = configParser.getConsumerConfig();
		
		this.valueDecoder = new JsonDecoder<>(eventClass);
		this.keyDecoder = new StringDecoder(null);
		
		this.dispatcherCommand = dispatcherCommand;
		
		this.topic = EventBase.getEventId(eventClass);
	}

	@Override
	public void run() {
		int cpus = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cpus);
		
		ConsumerConnector consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(this.consumerConfig);

		// map topics to thread count
		Map<String, Integer> topicCountMap = new HashMap<>();
		topicCountMap.put(this.topic, threadsPerTopic);
		
		// map topics to list of streams (1 stream per thread per topic)
		Map<String, List<KafkaStream<String, TIn>>> consumerMap = consumer
				.createMessageStreams(topicCountMap, this.keyDecoder, this.valueDecoder);
		
		// actually create/submit threads
		for (final KafkaStream<String, TIn> stream : consumerMap.get(this.topic)) {
			executor.submit(new Consumer<String, TIn>(
					stream,
					new Command<String, TIn>() {
						@Override
						public void execute(String key, TIn value) {
							dispatcherCommand.execute(value);
						};
					}));
		}

		// do not close producer while threads are still running
		// this.producer.close();
	}
}
