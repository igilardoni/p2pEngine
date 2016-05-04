package controller;

import rest.factories.RestServerFactory;

/**
 * Main class
 * {@link Application} is a singleton
 * @author Julien Prudhomme
 *
 */
public class Application {
	private static Application instance = null;
	
	public Application() {
		if(instance != null) {
			throw new RuntimeException("Application can be instanciate only once !");
		}
		instance = this;
		run();
	}
	
	public static Application getInstance()	{
		return instance;
	}
	
	private void run() {
		RestServerFactory.createAndStartDefaultRestServer();
	}
	
	public static void main(String[] args) {
		new Application();
	}
}
