package mib.microservices.testservice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mib.microservices.util.TransformCommand;
import mib.microservices.util.Transformer;
import kafka.javaapi.producer.Producer;

public class ComplexTransformCommand extends TransformCommand<A, B> {
	private static final class Transform implements Transformer<A, B> {
		@Override
		public B apply(A a) {
			Map<String, A> map = new HashMap<>();
			map.put("a", a);
			map.put("b", null);
			map.put("a2", new A("f yeah"));
			
			return new B(a.s, Arrays.asList(1, 2, 3), map);
		}
	}

	public ComplexTransformCommand(String topic, Producer<String, B> producer) {
		super(topic, producer, new Transform());
	}
}
