package controller;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.tools.JsonTools;
import model.api.EntityManager;
import model.entity.Item;
import model.entity.User;
import model.persistance.ItemManager;
import model.persistance.UserManager;
import network.impl.advertisement.ItemAdvertisement;
import rest.api.Authentifier;
import rest.api.ServletPath;
import rest.util.JsonUtils;

@ServletPath("/api/items/*")
@Path("/")
public class Items {
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String add(Item item, @HeaderParam(Authentifier.PARAM_NAME) String token) {
		Authentifier auth = Application.getInstance().getAuth();
		EntityManager<User> users = new UserManager();
		User currentUser = users.findOneByAttribute("nick", auth.getLogin(token));
		EntityManager<Item> em = new ItemManager();
		em.begin();
		//TODO VALIDATION
			item.setCreatedAt(new Date());
			item.setUsername(currentUser.getNick());
			//item.setPbkey(currentUser.getKeys().getPublicKey());
			em.persist(item);
		em.end();
		ItemAdvertisement iadv = new ItemAdvertisement();
		iadv.setTitle(item.getTitle());
		
		iadv.publish(Application.getInstance().getPeer());
		
		JsonTools<Item> json = new JsonTools<>();
		json.initialize(Item.class);
		return json.toJson(item);
		//return JsonUtils.BeanStringify(item);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(
			@PathParam("id")int id) {
		EntityManager<Item> em = new ItemManager();
		JsonTools<Item> json = new JsonTools<>();
		json.initialize(Item.class);
		return json.toJson(em.findOneById(id));
		//return JsonUtils.BeanStringify(em.findOneById(id));
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@HeaderParam(Authentifier.PARAM_NAME) String token) {
		Authentifier auth = Application.getInstance().getAuth();
		EntityManager<User> users = new UserManager();
		User currentUser = users.findOneByAttribute("nick", auth.getLogin(token));
		EntityManager<Item> em = new ItemManager();
		JsonTools<Collection<Item>> json = new JsonTools<>();
		json.initialize(Collection.class);
		return json.toJson(em.findAllByAttribute("username", currentUser.getNick()));
		//return JsonUtils.collectionStringify(em.findAll());
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String edit(Item item) {
		EntityManager<Item> em = new ItemManager();
		em.begin();
		Item item2 = em.findOneById(item.getId());
		item2.setTitle(item.getTitle());
		item2.setDescription(item.getDescription());
		em.end();
		
		JsonTools<Item> json = new JsonTools<>();
		json.initialize(Item.class);
		return json.toJson(item2);
		
		//return JsonUtils.BeanStringify(item2);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(
			@PathParam("id")int id) {
			//TODO
		return null;
	}
	
}
