package mib.microservice.commons.events.base;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mib.microservice.commons.events.meta.IEventMeta;


/**
 * This is the abstract base class for all events in our microservices. Every
 * event must provide at least a name and a version. The name + version
 * combination can be used to listen to the event. If a concrete event
 * implementation is changed, the version should be increased, to signal the
 * change for all interested services.
 * 
 * All event implementations must provide an accessible parameterless constructor.
 *
 * Created by matthias.popp on 14.08.2014.
 */
public abstract class EventBase<TMeta extends IEventMeta> {
	private final Class<TMeta> metaType;
	private final static Map<Class<?>, IEventMeta> metadataCache = new HashMap<>();

	protected EventBase(Class<TMeta> metaClass) {
		this.metaType = metaClass;
	}
	
	@JsonIgnore
	public IEventMeta getMeta() {
		IEventMeta metaInstance = EventBase.metadataCache.get(this.metaType);
		
		if(metaInstance == null) {
			try {
				metaInstance = this.metaType.newInstance(); 
				EventBase.metadataCache.put(this.metaType, metaInstance);
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		
		return metaInstance;
	}
	
	@JsonIgnore
	public String getEventId() {
		IEventMeta meta = this.getMeta();
		return meta != null ? meta.getEventId() : null;
	}
	
	public static String getEventId(Class<? extends EventBase<?>> eventClass) {
		IEventMeta meta = getMeta(eventClass);
		
		return meta != null ? meta.getEventId() : null;
	}
	
	private static IEventMeta getMeta(Class<? extends EventBase<?>> eventClass) {
		EventBase<?> event = create(eventClass);
		
		return event != null ? event.getMeta() : null;
	}
	
	private static EventBase<?> create(Class<? extends EventBase<?>> eventClass) {
		EventBase<?> event = null;
		try {
			event = eventClass.newInstance();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		
		return event;
	}
}