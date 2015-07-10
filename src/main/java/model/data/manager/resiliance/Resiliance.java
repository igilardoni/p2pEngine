package model.data.manager.resiliance;

public interface Resiliance {
	
	/**
	 * Each time the sharing manager calls this function, it will search for 
	 * (potentials) missing updates, and it will verify that's our objects are enough
	 * replicated on the network.
	 */
	public void step();
}
