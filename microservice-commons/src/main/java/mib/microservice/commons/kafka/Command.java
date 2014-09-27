package mib.microservice.commons.kafka;

public interface Command<K, V> {
	public void execute(K key, V value);
}
