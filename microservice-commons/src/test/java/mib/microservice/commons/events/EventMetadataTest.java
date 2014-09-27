package mib.microservice.commons.events;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import mib.microservice.commons.events.base.EventBase;
import mib.microservice.commons.events.base.EventMetadata;

import org.junit.Test;

public class EventMetadataTest {
	public static final class TestEvent extends EventBase {
		public TestEvent() {
			super(new EventMetadata("testname", "testversion"));
		}
	}
	
	public static final class PrivateTestEvent extends EventBase {
		private PrivateTestEvent() {
			super(new EventMetadata("private", "0"));
		}
	}
	
	/**
	 * EventId must be retrievable of subclasses of EventBase
	 */
	@Test
	public void getEventIdTest() {
		String eventId = EventBase.getEventId(TestEvent.class);
		
		assertThat(eventId, is(equalTo("testname_testversion")));
	}
	
	/**
	 * Events which cannot be instantiated return null as eventId
	 */
	@Test
	public void getEventIdNullTest() {
		String eventId = EventBase.getEventId(PrivateTestEvent.class);
		
		assertThat(eventId, is(nullValue()));
	}
}
