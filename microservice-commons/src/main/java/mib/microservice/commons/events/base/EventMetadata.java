package mib.microservice.commons.events.base;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class EventMetadata {
	private final String eventId;
	
	public EventMetadata(String eventName, String eventVersion) {
		this.eventId = StringUtils.join(
				Arrays.asList(eventName, eventVersion), "_"); 
	}
	
	public String getEventId() {
		return this.eventId;
	}
}
