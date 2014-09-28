package mib.microservice.main;

import mib.microservice.commons.events.base.EventBase;
import mib.microservice.commons.events.meta.EventMetaBase;

public class Event extends EventBase<Event.Meta> {
	protected static class Meta extends EventMetaBase {
		@Override
		protected String getEventName() { return "event_name"; }

		@Override
		protected String getEventVersion() { return "0.0"; }
	}
	
	protected Event() {
		super(Event.Meta.class);
	}
}
