package controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import crypt.impl.signatures.ElGamalSignature;
import network.api.SearchListener;
import network.api.Service;
import network.impl.advertisement.ItemAdvertisement;
import network.impl.jxta.JxtaItemService;
import network.impl.jxta.JxtaItemsSenderService;
import network.impl.jxta.JxtaSyncSearch;
import rest.api.ServletPath;

@ServletPath("/api/search/*")
@Path("/")
public class Search{
	
	@GET
	@Path("/simple")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String searchByTitle(
			@QueryParam("title") String title) {
		JxtaItemsSenderService service = (JxtaItemsSenderService) Application.getInstance().getPeer().getService(JxtaItemsSenderService.NAME);
		Service items = Application.getInstance().getPeer().getService(JxtaItemService.NAME);
		network.api.Search<ElGamalSignature, ItemAdvertisement<ElGamalSignature>> s = new JxtaSyncSearch<>();
		s.initialize(items);
		Collection<ItemAdvertisement<ElGamalSignature>> advs = s.search("title", title);
		if(advs.isEmpty()) return "[]";
		ArrayList<String> peerIds = new ArrayList<>();
		for(ItemAdvertisement<ElGamalSignature> a: advs) {
			peerIds.add(a.getSourceURI());
		}
		
		service.sendRequest(title, peerIds.toArray(new String[1]));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return service.getResponse();
	}

}
