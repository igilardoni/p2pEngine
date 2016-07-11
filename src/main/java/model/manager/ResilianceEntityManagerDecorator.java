package model.manager;

import model.api.EntityManager;
import model.api.ManagerDecorator;
import network.api.Peer;

public class ResilianceEntityManagerDecorator<Entity> extends ManagerDecorator<Entity>{

	private Peer peer;
	
	public ResilianceEntityManagerDecorator(EntityManager<Entity> manager, Peer peer) {
		super(manager);
		this.peer = peer;
	}
	
	@Override
	public void persist(Entity entity) {
		super.persist(entity);
	}
	
	@Override
	public void begin() {
		super.begin();
	}
	
	@Override
	public void end() {
		super.end();
	}
	
}
