package mib.microservice.commons.events;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import mib.microservice.commons.events.base.EventBase;
import mib.microservice.commons.events.meta.EventMetaBase;

import org.junit.Test;

public class EventMetadataTest {
	public static final class TestEvent extends EventBase<TestEvent.Meta> {
		public static class Meta extends EventMetaBase {
			@Override
			protected String getEventName() {
				return "testname";
			}

			@Override
			protected String getEventVersion() {
				return "testversion";
			}
		}
		
		public TestEvent() {
			super(Meta.class);
		}
	}
	
	public static final class PrivateTestEvent extends EventBase<PrivateTestEvent.Meta> {
		public static class Meta extends EventMetaBase {
			private Meta() {
				// private constructor to make meta creation fail
			}
			
			@Override
			protected String getEventName() {
				return "private";
			}

			@Override
			protected String getEventVersion() {
				return "0";
			}
		}
		
		private PrivateTestEvent() {
			super(Meta.class);
		}
	}
	
	/**
	 * EventId must be retrievable of subclasses of EventBase
	 */
	@Test
	public void getEventIdTest() {
		String eventId = new TestEvent().getEventId();
		
		assertThat(eventId, is(equalTo("testname_testversion")));
	}
	
	/**
	 * Events which cannot be instantiated return null as eventId
	 */
	@Test
	public void getEventIdNullTest() {
		String eventId = new PrivateTestEvent().getEventId();
		
		assertThat(eventId, is(nullValue()));
	}
	
	/**
	 * Get event id when only type info is available (no instance)
	 */
	@Test
	public void getEventIdStatic() {
		String eventId = EventBase.getEventId(TestEvent.class);
		
		assertThat(eventId, is(equalTo("testname_testversion")));
	}
}
