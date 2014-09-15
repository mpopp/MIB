package mib.microservice.commons.kafka;

import mib.microservice.commons.events.base.EventBase;

public interface IKafkaDispatcher<TIn extends EventBase>
extends Runnable {
}
