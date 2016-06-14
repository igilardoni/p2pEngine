package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.glassfish.jersey.server.ChunkedOutput;

import network.api.ItemRequestService;
import network.api.Messages;
import network.api.SearchListener;
import network.api.Service;
import network.api.ServiceListener;
import network.impl.advertisement.ItemAdvertisement;
import network.impl.jxta.JxtaItemService;
import network.impl.jxta.JxtaItemsSenderService;
import rest.api.Authentifier;
import rest.api.ServletPath;

@ServletPath("/api/search/*")
@Path("/")
public class Search{
	
	/*@GET
	@Path("/simple")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String searchByTitle(
			@QueryParam("title") String title) {
		JxtaItemsSenderService service = (JxtaItemsSenderService) Application.getInstance().getPeer().getService(JxtaItemsSenderService.NAME);
		Service items = Application.getInstance().getPeer().getService(JxtaItemService.NAME);
		network.api.Search<ItemAdvertisement> s = new JxtaSyncSearch<>();
		s.initialize(items);
		Collection<ItemAdvertisement> advs = s.search("title", title);
		if(advs.isEmpty()) return "[]";
		ArrayList<String> peerIds = new ArrayList<>();
		for(ItemAdvertisement a: advs) {
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
	}*/
	
	
	@GET
	@Path("/simple")
	public ChunkedOutput<String> chunckedSearchByTitle(
			@QueryParam("title") final String title,
			@HeaderParam(Authentifier.PARAM_NAME) final String token) {
		final ChunkedOutput<String> output = new ChunkedOutput<String>(String.class);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				final ItemRequestService itemSender = (ItemRequestService) Application.getInstance().getPeer().getService(JxtaItemsSenderService.NAME);
				Service items = Application.getInstance().getPeer().getService(JxtaItemService.NAME);
				
				itemSender.addListener(new ServiceListener() {
					
					@Override
					public void notify(Messages messages) {
						try {
							output.write(messages.getMessage("items"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}, token == null ? "test":token);
				
				items.search("title", title, new SearchListener<ItemAdvertisement>() {
					@Override
					public void notify(Collection<ItemAdvertisement> result) {
						ArrayList<String> uids = new ArrayList<>();
						for(ItemAdvertisement i: result) {
							uids.add(i.getSourceURI());
						}
						itemSender.sendRequest(title, token == null ? "test":token, uids.toArray(new String[1]));
					}
					
				});
				try {
					Thread.sleep(3000);
					itemSender.removeListener(token == null ? "test":token);
					try {
						output.write("[]");
						output.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (InterruptedException e) {
					
				}
			}
			
		}).start();
		
		return output;
	}

}
