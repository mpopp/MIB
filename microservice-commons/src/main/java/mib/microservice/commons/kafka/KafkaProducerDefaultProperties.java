package mib.microservice.commons.kafka;

import java.util.Map;
import java.util.Properties;

/**
 * Default/fallback config properties for a kafka producer
 * 
 * @author knittl
 *
 */
public class KafkaProducerDefaultProperties extends Properties {
	private static final long serialVersionUID = 1L;

	public KafkaProducerDefaultProperties() {
		this.put("serializer.class", JsonEncoder.class.getName());
		this.put("key.serializer.class", kafka.serializer.StringEncoder.class.getName());
		this.put("partitioner.class", kafka.producer.DefaultPartitioner.class.getName());
		this.put("request.required.acks", "1");
	}
	
	public KafkaProducerDefaultProperties(Map<String, String> props) {
		this();
		
		this.putAll(props);
	}
}
