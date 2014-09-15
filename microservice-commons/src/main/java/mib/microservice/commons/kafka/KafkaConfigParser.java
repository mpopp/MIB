package mib.microservice.commons.kafka;

import java.io.IOException;

import kafka.consumer.ConsumerConfig;
import kafka.producer.ProducerConfig;
import mib.microservice.commons.cli.CLI;
import mib.microservices.util.ServiceConfig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaConfigParser {
	private ProducerConfig producerConfig;
	private ConsumerConfig consumerConfig;
	
	private ServiceConfig serviceConfig;
	
	public final ProducerConfig getProducerConfig() {
		return this.producerConfig;
	}

	public final ConsumerConfig getConsumerConfig() {
		return this.consumerConfig;
	}
	
	public final ServiceConfig getServiceConfig() {
		return this.serviceConfig;
	}
	
	public KafkaConfigParser() {
		this.serviceConfig = null;
		this.producerConfig = null;
		this.consumerConfig = null;
	}

	public void parseConfig(CLI args) {
		this.serviceConfig = this.parseConfig(args.getKafkaConfigJson());
		
		if(this.serviceConfig == null) {
			this.serviceConfig = new ServiceConfig("partitions");
			this.serviceConfig.publish = "ping";
		}
		
		// TODO remove, only for testing
		this.serviceConfig.producer.put("metadata.broker.list", "127.0.0.1:9091,127.0.0.2:9092,127.0.0.3:9093");
		this.serviceConfig.consumer.put("zookeeper.connect", "127.0.0.1:2181");
		this.serviceConfig.consumer.put("group.id", "consumerGroup");
		
		this.producerConfig = new ProducerConfig(new KafkaProducerDefaultProperties(this.serviceConfig.producer));
		this.consumerConfig = new ConsumerConfig(new KafkaConsumerDefaultProperties(this.serviceConfig.consumer));
	}
	
	private ServiceConfig parseConfig(String jsonConfig) {
		if(jsonConfig == null || jsonConfig.length() == 0)
			return null;
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			return mapper.readValue(jsonConfig, ServiceConfig.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
