package mib.microservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mib.microservice.dto.SampleDomainObject;

@Path(Routes.EXAMPLE)
public class ExampleService {
	
	@GET
	@Path(Routes.EXAMPLE_GET)
	@Produces(MediaType.APPLICATION_JSON)
	public SampleDomainObject getFulfillment(@PathParam("contractid") String contractId) {
		// Here is the right place to call actions that trigger events after
		// execution which are published in the message queue.
		return new SampleDomainObject();
	}
}
