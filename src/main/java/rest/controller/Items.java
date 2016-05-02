package rest.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.api.EntityManager;
import model.entity.Item;
import model.persistance.ItemManager;
import rest.util.JsonUtils;

@Path("/api")
public class Items {
	
	@POST
	@Path("items")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String add(Item item) {
		EntityManager<Item> em = new ItemManager();
		em.begin();
		//TODO VALIDATION
			em.persist(item);
		em.end();
		
		return JsonUtils.BeanStringify(item);
	}
	
	@GET
	@Path("items/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(
			@PathParam("id")int id) {
		EntityManager<Item> em = new ItemManager();
		return JsonUtils.BeanStringify(em.findOneById(id));
	}
	
	@GET
	@Path("items")
	@Produces(MediaType.APPLICATION_JSON)
	public String get() {
		EntityManager<Item> em = new ItemManager();
		return JsonUtils.collectionStringify(em.findAll());
	}
	
	@PUT
	@Path("items/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String edit(Item item) {
		EntityManager<Item> em = new ItemManager();
		em.begin();
		Item item2 = em.findOneById(item.getId());
		item2.setTitle(item.getTitle());
		item2.setDescription(item.getDescription());
		em.end();
		
		return JsonUtils.BeanStringify(item2);
	}
	
	@DELETE
	@Path("items/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(
			@PathParam("id")int id) {
			
		return null;
	}
	
}
