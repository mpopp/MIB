package mib.microservice.main;

import mib.microservice.commons.cli.CLI;
import mib.microservice.commons.cli.CmdLineParser;
import mib.microservice.commons.jetty.JettyUtils;
import mib.microservice.commons.persistence.PersistenceUtils;
import org.eclipse.jetty.server.Server;
import mib.microservice.commons.persistence.PersistenceUtils;


/**
 * Main class for creating a microservice. Adds package scanning for the Genson
 * bodyWriter and our service package for detecting Jersey resources.
 * 
 * @author matthias.popp
 */
public class MicroserviceMain {

	public static void main(String[] args) throws Exception {
		final CLI options = new CLI();
		CmdLineParser.parseCmdLineArguments(options, args);

		// This jetty instance is configured to detect all webservices in the
		// package 'mib.microservice.rest'
		// Domain objects are (de)serialized in JSON format automatically. No
		// Annotations needed.
		Server server = JettyUtils.initializeJetty(options, "mib.microservice.rest");

        //Start hibernate entity manager to be ready to process events.
        PersistenceUtils.initialize("mib-test");

		// start the kafka consumer somewhere around here
		server.start();
		server.join();
	}
}
