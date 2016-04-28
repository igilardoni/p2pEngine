package rest.impl;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import rest.api.RestServer;

public class JettyRestServer implements RestServer{
	
	private ServletContextHandler context;
	private Server server;
	
	@Override
	public void initialize(Class<?> entryPoint) {
		//exec context
		context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        
        //servlet container
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
    
        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", entryPoint.getCanonicalName());
	}

	@Override
	public void start(int port) throws Exception {
		server = new Server(8080);
        server.setHandler(context);
		server.start();
        server.join();
	}

	@Override
	public void stop() {
		server.destroy();
	}
	
	public static void main(String[] args) {
		JettyRestServer server = new JettyRestServer();
		server.initialize(RestApi.class);
		try {
			server.start(8080);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.stop();
		}
	}

}
