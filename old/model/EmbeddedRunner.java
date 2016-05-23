/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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

	
	/**
	 * Create the glassfish embeddedRunner
	 * Call init and start to run the server.
	 * @param port the server port. For exemple, the server will be accessible at localhost:port
	 */
	public EmbeddedRunner(int port) {
		this.port = port;
	}

	/**
	 * Initialize the serveur properties.
	 * @return
	 * @throws Exception
	 */
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
	 * @return EmbeddedRunner
	 * @throws Exception
	 */
	public EmbeddedRunner start() throws Exception{
		check();
		glassfish.start();
		glassfish.getDeployer().deploy(new File("build"));
		return this;
	}

	/**
	 * Stop the server.
	 * @return
	 * @throws Exception
	 */
	public EmbeddedRunner stop() throws Exception{
		check();
		glassfish.stop();
		return this;
	}
}
