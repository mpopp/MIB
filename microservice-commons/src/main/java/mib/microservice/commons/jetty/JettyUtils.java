package mib.microservice.commons.jetty;

import java.util.Arrays;

import mib.microservice.commons.cli.CLI;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**s
 * Util class for configuring and initializing jetty.
 * 
 * @author matthias.popp
 */
public class JettyUtils {

	private JettyUtils() {
	}

	/**
	 * Initializes an embedded Jetty instance. The instance is runnable out of
	 * the box, but won't detect your jax-rs services unless you provide the
	 * packages to scan for additional resources.
	 * 
	 * @param options
	 *            The command line options from the caller.
	 * @param additionalPackagesToScan
	 *            The packages to scan for webservice implementations (jax-rs
	 *            classes).
	 * @return The configured Jetty instance.
	 */
	public static Server initializeJetty(CLI options, String... additionalPackagesToScan) {
		Server server = createServer(options);
		ServletContextHandler context = createServletContext(server);

		//length + 1 to make place for the genson package.
		String [] packagesToScan = Arrays.copyOf(additionalPackagesToScan, additionalPackagesToScan.length + 1);
		packagesToScan[packagesToScan.length - 1] = "com.owlike.genson.ext.jaxrs";
		ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
		// Package scan is used to detect resources. This implies that all
		// services we write are placed in the
		// "package mib.api.gateway.service".
		// "com.owlike.genson.ext.jaxrs" adds the genson library as provider for
		// MessageBodyWriter and MessageBodyReader.
		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", StringUtils.join(packagesToScan, ','));
		return server;
	}
	
	public static Server initializeJetty(CLI options, IJettyService ... serviceInstances) {
		Server server = createServer(options);
		ServletContextHandler context = createServletContext(server);

		ResourceConfig resourceConfig = new ResourceConfig();
		for(int i = 0; i < serviceInstances.length; i++) {
			resourceConfig.register(serviceInstances[i]);
		}
		resourceConfig.packages("com.owlike.genson" + ".ext.jaxrs");
		ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(resourceConfig));
		context.addServlet(jerseyServlet, "/*");
		
		return server;
	}

	private static Server createServer(CLI options) {
		// Create the embedded jetty server
		Server server = new Server(options.getJettyPort());

		// Set network interface on which jetty has to listen for calls. If that
		// block is not executed, jetty will listen on ALL network interfaces
		// (private and publicly available ips).
		if (!StringUtils.isBlank(options.getJettyHost())) {
			ServerConnector connector = new ServerConnector(server);
			connector.setHost(options.getJettyHost());
			connector.setPort(options.getJettyPort());
			server.setConnectors(new ServerConnector[] { connector });
		}
		return server;
	}

	private static ServletContextHandler createServletContext(Server server) {
		// Create the context handler
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		// with contextPath set to "/" we are able to access our service with
		// following pattern http://<ip>/<paths into theservice>
		context.setContextPath("/");
		server.setHandler(context);
		return context;
	}

}
