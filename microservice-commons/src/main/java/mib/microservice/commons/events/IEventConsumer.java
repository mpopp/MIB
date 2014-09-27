package mib.microservice.commons.events;

public interface IEventConsumer<K, V> {
	public void execute(K key, V value);
}
