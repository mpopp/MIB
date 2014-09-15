package mib.microservice.commons.cli;

import org.kohsuke.args4j.Option;

/**
 * This class contains all command line arguments that our standard microservice
 * can handle. Every argument that is not given on startup will cause the
 * corresponding field in this class to be left uninitialized. For primitive
 * types this means it contains the default value, for complex types (i.e.
 * Object subclasses) this means the field is null.
 * 
 * For customization in certain microservices, create a subclass of this CLI
 * class.
 * 
 * @author matthias.popp
 * 
 */
public class CLI {

	@Option(name="-jetty-port", usage="Sets the port on which jetty has to run. "
			+ "Jetty chooses a random port if that option is not set.")
	protected int jettyPort;

	@Option(name = "-jetty-host", usage = "Sets the host on which jetty is listening for calls. Can either be an ip "
			+ "address or a host name. If left blank, jetty listenes to ALL network interfaces.")
	protected String jettyHost;
	
	@Option(name = "--kafka-config", usage = "Sets additional config options for the Kafka consumer and producer")
	protected String kafkaConfigJson;

	public int getJettyPort() {
		return jettyPort;
	}

	public String getJettyHost() {
		return jettyHost;
	}
	
	public String getKafkaConfigJson()
	{
		return this.kafkaConfigJson;
	}
}
