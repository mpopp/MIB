package mib.microservices.testservice;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class FixedPartitioner implements Partitioner {
	public FixedPartitioner(VerifiableProperties props) {
	}

	@Override
	public int partition(Object object, int numPartitions) {
		return 0;
	}
}