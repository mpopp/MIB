package mib.microservice.commons.kafka;

import java.util.HashMap;
import java.util.Map;

/**
 * MicroService can be configured via JSON
 *
 */
public class ServiceConfig {
	public ServiceConfig() {
		this.consumer = new HashMap<>();
		this.producer = new HashMap<>();
	}
	
	/**
	 * Properties for consumer 
	 */
	public Map<String, String> consumer;
	
	/**
	 * Properties for producer
	 */
	public Map<String, String> producer;
}
