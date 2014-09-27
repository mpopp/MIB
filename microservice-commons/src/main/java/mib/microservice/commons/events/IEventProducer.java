package mib.microservice.commons.events;

import mib.microservice.commons.events.base.EventBase;

public interface IEventProducer<TOut extends EventBase> {
	public abstract void raise(TOut event);
}