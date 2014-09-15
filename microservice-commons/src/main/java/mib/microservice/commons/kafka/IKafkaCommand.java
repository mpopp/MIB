package mib.microservice.commons.kafka;


public interface IKafkaCommand<TIn> {
	void execute(TIn event);
}
