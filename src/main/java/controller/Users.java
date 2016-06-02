package controller;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import crypt.api.hashs.Hasher;
import crypt.factories.HasherFactory;
import model.api.EntityManager;
import model.entity.User;
import model.persistance.UserManager;
import rest.api.Authentifier;
import rest.api.ServletPath;
import rest.util.JsonUtils;

@ServletPath("/api/users/*")
@Path("/")
public class Users {

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(
			@QueryParam("login") String login,
			@QueryParam("password") String password) {
		
		Authentifier auth = Application.getInstance().getAuth();
		return "{\"token\": \"" + auth.getToken(login, password) + "\"}";
	}
	
	@GET
	@Path("/logout")
	public String logout(@HeaderParam(Authentifier.PARAM_NAME) String token) {
		Authentifier auth = Application.getInstance().getAuth();
		auth.deleteToken(token);
		return null;
	}
	
	@GET
	@Path("/subscribe")
	@Produces(MediaType.APPLICATION_JSON)
	public String subscribe(
			@QueryParam("login") String login,
			@QueryParam("password") String password) {
		
		User u = new User();
		u.setNick(login);
		Hasher hasher = HasherFactory.createDefaultHasher();
		u.setSalt(HasherFactory.generateSalt());
		hasher.setSalt(u.getSalt());
		u.setPasswordHash(hasher.getHash(password.getBytes()));
		u.setCreatedAt(new Date());
		EntityManager<User> em = new UserManager();
		em.begin();
		em.persist(u);
		em.end();
		
		Authentifier auth = Application.getInstance().getAuth();
		return "{\"token\": \"" + auth.getToken(login, password) + "\"}";
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String add(User user) {
		
		return null;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(
			@PathParam("id") long id) {
		
		return null;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String get() {
		EntityManager<User> em = new UserManager();
		return JsonUtils.collectionStringify(em.findAll());
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String edit(User user) {
		
		return null;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(
			@PathParam("id") long id) {
		return null;
	}
	
}
