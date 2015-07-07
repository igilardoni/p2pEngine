package model.data.manager.resiliance;

import model.data.manager.Manager;
import model.network.communication.Communication;

public abstract class AbstractResiliance implements Resiliance{

	protected Manager manager;
	protected Communication com;
	
	public AbstractResiliance(Manager m, Communication c) {
		manager = m;
		com = c;
	}
	
	
	
	@Override
	public abstract void step();

}
