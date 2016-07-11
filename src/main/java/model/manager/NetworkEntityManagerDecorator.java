package model.manager;

import java.util.Collection;

import model.api.EntityManager;
import model.api.ManagerDecorator;
import network.api.Peer;

public class NetworkEntityManagerDecorator<Entity> extends ManagerDecorator<Entity>{

	private Peer peer;
	
	public NetworkEntityManagerDecorator(EntityManager<Entity> manager, Peer peer) {
		super(manager);
		this.peer = peer;
	}

	@Override
	public Entity findOneById(long id) {
		Entity t = super.findOneById(id);
		if(t != null) return t;
		
		
		
		return super.findOneById(id);
	}
	
	@Override
	public Collection<Entity> findAll() {
		return super.findAll();
	}

	@Override
	public Entity findOneByAttribute(String attribute, String value) {
		return super.findOneByAttribute(attribute, value);
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

	@Override
	public Collection<Entity> findAllByAttribute(String attribute, String value) {
		return super.findAllByAttribute(attribute, value);
	}
	
}
