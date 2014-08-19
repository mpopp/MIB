package mib.microservices.testservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mib.microservices.util.Command;
import mib.microservices.util.Consumer;
import mib.microservices.util.JsonDecoder;
import mib.microservices.util.ServiceConfig;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestService {
	private static ProducerConfig createProducerConfig(
			Map<String, String> producerConfig) {
		Properties props = new Properties();
		
		// default/fallback properties
		props.put("serializer.class", "mib.microservices.util.JsonEncoder");
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "kafka.producer.DefaultPartitioner");
		props.put("request.required.acks", "1");
		
		props.putAll(producerConfig);
		
		return new ProducerConfig(props);
	}

	private static ConsumerConfig createConsumerConfig(
			Map<String, String> consumerConfig) {
		Properties props = new Properties();

		// default/fallback properties
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		props.put("serializer.class", "kafka.serializer.StringDecoder");
		
		props.putAll(consumerConfig);
		
		return new ConsumerConfig(props);
	}
	
	private static ServiceConfig parseConfig(String jsonConfig) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if(jsonConfig == null || jsonConfig.length() == 0)
			return null;
		
		try {
			return mapper.readValue(jsonConfig, ServiceConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// configured via json, like this:
	// {
	//   "subscriptions":["a","b","c"],
	//   "publish":"x"
	//   "consumer":{"group.id":"group"},
	//   "producer":{"metadata.broker.list":"127.0.0.1:9091,127.0.0.1:9092"}
	// }
	public static void main(String[] args) {
		int threadsPerTopic = 1;

		ServiceConfig serviceConfig = null;
		ProducerConfig producerConfig = null;
		ConsumerConfig consumerConfig = null;
		if (args.length > 0) {
			serviceConfig = parseConfig(args[0]);
		}
		
		if(serviceConfig == null) {
			serviceConfig = new ServiceConfig("partitions");
			serviceConfig.publish = "ping";
		}
		
		// TODO remove, only for testing
		serviceConfig.producer.put("metadata.broker.list", "127.0.0.1:9091,127.0.0.2:9092,127.0.0.3:9093");
		serviceConfig.consumer.put("zookeeper.connect", "127.0.0.1:2181");
		serviceConfig.consumer.put("group.id", "consumerGroup");
		
		producerConfig = createProducerConfig(serviceConfig.producer);
		consumerConfig = createConsumerConfig(serviceConfig.consumer);

		Decoder<String> keyDecoder = new StringDecoder(null);
		Decoder<A> valueDecoder = new JsonDecoder<>(A.class);

		ConsumerConnector consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(consumerConfig);

		// map topics to thread count
		Map<String, Integer> topicCountMap = new HashMap<>();
		for (String topic : serviceConfig.subscriptions) {
			topicCountMap.put(topic, threadsPerTopic);
		}

		// map topics to list of streams (1 stream per thread per topic)
		Map<String, List<KafkaStream<String, A>>> consumerMap = consumer
				.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);

		ExecutorService executor = Executors.newFixedThreadPool(threadsPerTopic
				* serviceConfig.subscriptions.size());

		//PingCommand pingCommand = new PingCommand(new Producer<String, String>(createProducerConfig()));
		//PingCommandJson pingCommand = new PingCommandJson(new Producer<String, A>(createProducerConfig()));
		Command<String, A> catCommand = new ComplexTransformCommand(serviceConfig.publish, new Producer<String, B>(producerConfig));

		// actually create/submit threads
		for (String topic : serviceConfig.subscriptions) {
			for (final KafkaStream<String, A> stream : consumerMap.get(topic)) {
				executor.submit(new Consumer<String, A>(stream, catCommand));
			}
		}

		// do not close producer while threads are still running
		// this.producer.close();
	}
}
