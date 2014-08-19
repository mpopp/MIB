package mib.microservices.util;

public interface Command<K, V> {
	public void execute(K key, V value);
}
