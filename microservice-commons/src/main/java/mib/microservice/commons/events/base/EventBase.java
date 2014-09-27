package mib.microservice.commons.events.base;


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
	private EventMetadata metadata;
	
	protected EventBase(EventMetadata metadata) {
		this.metadata = metadata;
	}
	
	public String getEventId() {
		return this.metadata.getEventId();
	}
	
	public static String getEventId(Class<? extends EventBase> eventClass) {
		try {
			return eventClass.newInstance().getEventId();
		} catch (Exception ex) {
			return null;
		}
	}
}