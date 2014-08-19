package mib.microservice.rest;

/**
 * This file contains all routes for that microservice. No route to a rest call
 * should be hardcoded within the rest class itself.
 * 
 * @author matthias.popp
 * 
 */
public class Routes {

	// No instances needed for that class.
	private Routes() {
	}

	public static final String EXAMPLE = "/exampleroute";
	public static final String EXAMPLE_GET = "/example/{exampleId}";
}
