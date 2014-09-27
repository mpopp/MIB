package mib.microservice.commons.events.meta;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public abstract class EventMetaBase implements IEventMeta {
	protected abstract String getEventName();
	protected abstract String getEventVersion();
	
	@Override
	public String getEventId() {
		return StringUtils.join(Arrays.asList(this.getEventName(), this.getEventVersion()), "_");
	}
}
