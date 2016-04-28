package model.persistance;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class AbstractEntityManager<Entity> implements model.api.EntityManager<Entity>{
	private EntityManagerFactory factory;
	private EntityManager em;
	private Class<?> theClass;
	@Override
	public void initialisation(String unitName, Class<?> c) {
		factory = Persistence.createEntityManagerFactory(unitName);
		this.theClass = c;
		em = factory.createEntityManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity findOneById(long id) {
		return (Entity) em.find(theClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Entity> findAll() {
		Query q = em.createQuery("select t from Item t");
		return q.getResultList();
	}

	@Override
	public void begin() {
		em.getTransaction().begin();
	}

	@Override
	public void end() {
		em.getTransaction().commit();
	}

	@Override
	public void persist(Entity entity) {
		em.persist(entity);
	}
	
}
