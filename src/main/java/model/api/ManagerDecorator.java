package model.api;

import java.util.Collection;

/**
 * Entities manager. Handle local and distant storage, search.
 * @author Julien Prudhomme
 *
 * @param <T>
 */
public abstract class ManagerDecorator<Entity> implements EntityManager<Entity>{

	private EntityManager<Entity> manager;
	
	public ManagerDecorator(EntityManager<Entity> manager) {
		this.manager = manager;
	}
	
	@Override
	public void initialisation(String unitName, Class<?> c) {
		manager.initialisation(unitName, c);
	}

	@Override
	public Entity findOneById(long id) {
		return manager.findOneById(id);
	}

	@Override
	public Collection<Entity> findAll() {
		return manager.findAll();
	}

	@Override
	public Entity findOneByAttribute(String attribute, String value) {
		return manager.findOneByAttribute(attribute, value);
	}

	@Override
	public void persist(Entity entity) {
		manager.persist(entity);
	}

	@Override
	public void begin() {
		manager.begin();
	}

	@Override
	public void end() {
		manager.end();
	}
	
	@Override
	public Collection<Entity> findAllByAttribute(String attribute, String value) {
		return manager.findAllByAttribute(attribute, value);
	}
}
