package model;

import org.glassfish.embeddable.*;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Embedded Glassfish server 
 * @author Prudhomme Julien
 *
 */
public class EmbeddedRunner {
	private int port;
	private AtomicBoolean initialized = new AtomicBoolean();
	private GlassFish glassfish;

	public EmbeddedRunner(int port) {
		this.port = port;
	}

	public EmbeddedRunner init() throws Exception{
		if ( initialized.get() ) {
			throw new RuntimeException("runner was already initialized");
		}
		BootstrapProperties bootstrapProperties = new BootstrapProperties();
		GlassFishRuntime glassfishRuntime = GlassFishRuntime.bootstrap(bootstrapProperties);

		GlassFishProperties glassfishProperties = new GlassFishProperties();
		glassfishProperties.setPort("http-listener", port);
		glassfish = glassfishRuntime.newGlassFish(glassfishProperties);
		initialized.set(true);
		return this;
	}

	private void check() {
		if ( !initialized.get() ) {
			throw new RuntimeException("runner was not initialised");
		}
	}

	/**
	 * Run the server and deploy the application
	 * @return this
	 * @throws Exception
	 */
	public EmbeddedRunner start() throws Exception{
		check();
		glassfish.start();
		glassfish.getDeployer().deploy(new File("build"));
		return this;
	}

	public EmbeddedRunner stop() throws Exception{
		check();
		glassfish.stop();
		return this;
	}

	public static void main(String args[]) throws Exception {
		
		EmbeddedRunner runner = new EmbeddedRunner(8080).init().start();
		// kill the task to stop the server :p
	}
}
