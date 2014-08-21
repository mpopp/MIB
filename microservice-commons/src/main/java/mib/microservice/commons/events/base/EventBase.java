package mib.microservice.commons.events.base;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

/**
 * This is the abstract base class for all events in our microservices. Every
 * event must provide at least a name and a version. The name + version
 * combination can be used to listen to the event. If a concrete event
 * implementation is changed, the version should be increased, to signal the
 * change for all interested services.
 *
 * Created by matthias.popp on 14.08.2014.
 */
public abstract class EventBase {
    public String getEventId(){
        return StringUtils.join(Lists.newArrayList(getEventName(), getEventVersion()), "_");
    }

    protected abstract String getEventName();
    protected abstract String getEventVersion();
}
