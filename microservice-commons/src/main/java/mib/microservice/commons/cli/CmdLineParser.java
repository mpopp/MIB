package mib.microservice.commons.cli;

import org.kohsuke.args4j.CmdLineException;

/**
 * Util class for parsing cmd line arguments.
 * 
 * @author matthias.popp
 * 
 */
public class CmdLineParser {

	public static void parseCmdLineArguments(Object options, String[] args) {
		final org.kohsuke.args4j.CmdLineParser parser = new org.kohsuke.args4j.CmdLineParser(options);
		try {
			parser.parseArgument(args);
		} catch (final CmdLineException e) {
			System.err.println(e.getMessage());
			String correctUsage = "java -jar myprogram.jar [options...] arguments...";
			System.err.println(correctUsage);
			parser.printUsage(System.err);
			throw new IllegalArgumentException("Invalid parameters upon startup. Correct usage: " + correctUsage);
		}
	}
}
