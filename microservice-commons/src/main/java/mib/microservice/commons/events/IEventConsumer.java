package mib.microservice.commons.events;

import mib.microservice.commons.events.base.EventBase;

public interface IEventConsumer<K, V extends EventBase<?>> {
	public void execute(K key, V value);
}
