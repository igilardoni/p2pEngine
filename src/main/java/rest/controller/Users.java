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

import model.entity.User;

@Path("/api")
public class Users {

	@POST
	@Path("users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String add(User user) {
		
		return null;
	}
	
	@GET
	@Path("users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(
			@PathParam("id") long id) {
		
		return null;
	}
	
	@GET
	@Path("items")
	@Produces(MediaType.APPLICATION_JSON)
	public String get() {
		return null;
	}
	
	@PUT
	@Path("users/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String edit(User user) {
		
		return null;
	}
	
	@DELETE
	@Path("items/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(
			@PathParam("id") long id) {
		return null;
	}
	
}
