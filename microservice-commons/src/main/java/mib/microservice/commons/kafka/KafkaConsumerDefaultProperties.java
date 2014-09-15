package mib.microservice.commons.kafka;

import java.util.Map;
import java.util.Properties;

/**
 * Default/fallback config properties for a kafka consumer
 * 
 * @author knittl
 *
 */
public class KafkaConsumerDefaultProperties extends Properties {
	private static final long serialVersionUID = 1L;

	public KafkaConsumerDefaultProperties() {
		this.put("zookeeper.session.timeout.ms", "400");
		this.put("zookeeper.sync.time.ms", "200");
		this.put("auto.commit.interval.ms", "1000");
		this.put("serializer.class", "kafka.serializer.StringDecoder");
	}
	
	public KafkaConsumerDefaultProperties(Map<String, String> props) {
		this();
		
		this.putAll(props);
	}
}
