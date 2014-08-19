package mib.microservices.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MicroService can be configured via JSON
 *
 */
public class ServiceConfig {
	public ServiceConfig(String... subscriptions) {
		this.subscriptions = Arrays.asList(subscriptions);
		
		this.consumer = new HashMap<>();
		this.producer = new HashMap<>();
	}
	
	/**
	 * List of subscription topics
	 */
	public List<String> subscriptions;
	
	/**
	 * Publication topic
	 */
	public String publish;
	
	/**
	 * Properties for consumer 
	 */
	public Map<String, String> consumer;
	
	/**
	 * Properties for producer
	 */
	public Map<String, String> producer;
}
