package mib.microservice.commons.events;

import mib.microservice.commons.events.base.EventBase;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Created by matthias.popp on 14.08.2014.
 */
public class EventUtils {

    static public <EVENTTYPE extends EventBase> void raiseEvent(final EVENTTYPE event){
        ProducerConfig params = null; //TODO this one should definitly be accessable from a common place.
        final Producer<String, EVENTTYPE> producer = new Producer<>(params);
        producer.send(new KeyedMessage<String, EVENTTYPE>(event.getEventId(),event));
    }
}
