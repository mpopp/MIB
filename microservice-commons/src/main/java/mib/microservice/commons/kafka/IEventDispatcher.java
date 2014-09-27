package mib.microservice.commons.kafka;

import mib.microservice.commons.events.base.EventBase;

public interface IEventDispatcher<TIn extends EventBase<?>>
extends Runnable {
}
