package model.api;

import java.util.Collection;

/**
 * General interface for entity managers
 * @author Julien Prudhomme
 *
 * @param <Entity> class' entity
 */
public interface EntityManager<Entity> {
	/**
	 * Initialise the entity manager with the unit name
	 * @param unitName unit (entity) name for persistance. See persistance.xml in META-INF
	 */
	public void initialisation(String unitName, Class<?> c);
	
	/**
	 * Find one entity with its Id
	 * @param id entity id
	 * @return An instance of the entity or null.
	 */
	public Entity findOneById(long id);
	
	/**
	 * Return the whole collection of stored entities
	 * @return A collection of entities
	 */
	public Collection<Entity> findAll();
	
	/**
	 * Persist(insert) this instance to the database
	 * @param entity
	 */
	public void persist(Entity entity);
	
	/**
	 * Begin the transaction
	 */
	public void begin();
	
	/**
	 * end (commit) the transaction
	 */
	public void end();
}
