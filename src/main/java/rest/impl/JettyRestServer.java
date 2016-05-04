package rest.impl;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import rest.api.RestServer;
import rest.api.ServletPath;

public class JettyRestServer implements RestServer{
	
	private ServletContextHandler context;
	private Server server;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Class<?> ...entryPoints) {
		context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        
        for(Class<?> c : entryPoints) {
        	
        	ServletPath path = c.getAnnotation(ServletPath.class);
        	if(path == null) {
        		throw new RuntimeException("No servlet path annotation on class " + c.getCanonicalName());
        	}
        	ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, path.value());
        	jerseyServlet.setInitOrder(0);
        	jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", c.getCanonicalName());
        }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(int port) throws Exception {
		server = new Server(8080);
        server.setHandler(context);
		server.start();
        server.join();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		server.destroy();
	}

}
