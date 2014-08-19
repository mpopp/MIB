package mib.microservices.util;

public interface Transformer<In, Out> {
	Out apply(In value);
}
