/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package model;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.logging.Level;

import model.advertisement.AdvertisementInstaciator;
import model.data.RendezVousIp;
import model.data.contrat.Clause;
import model.data.contrat.Contrat;
import model.data.favorites.Favorites;
import model.data.item.Category;
import model.data.item.Item;
import model.data.item.Category.CATEGORY;
import model.data.item.Item.TYPE;
import model.data.manager.Manager;
import model.data.manager.resiliance.ContratsResiliance;
import model.data.manager.resiliance.FavoritesResiliance;
import model.data.manager.resiliance.ItemResiliance;
import model.data.manager.resiliance.MessageResiliance;
import model.data.manager.resiliance.SharingManager;
import model.data.manager.resiliance.UserResiliance;
import model.data.user.Conversations;
import model.data.user.User;
import model.data.user.UserMessage;
import model.network.Network;
import model.network.communication.Communication;
import model.network.communication.service.MessageService;
import model.network.communication.service.InstanceSender.ClassSenderService;
import model.network.communication.service.update.UpdateService;
import model.network.search.Search;
import util.Printer;
import util.VARIABLES;
import util.secure.AsymKeysImpl;

/**
 * The main class of the software. This class can be instancied only once. (singleton)
 * Instantiate this class will launch the P2P network.
 * @author Prudhomme Julien
 *
 */
public class Application {
	private static Application instance = null; //the current instance of this class.
	
	private EmbeddedRunner server = null;
	private Network network;
	private Manager manager;
	private Communication com;
	private SharingManager sharingManager;
	
	/**
	 * Launch the application.
	 */
	public Application(boolean startLocalServer) {
		if(instance != null) {
			throw new RuntimeException("this class can be instancied only once");
		}
		AdvertisementInstaciator.RegisterAllAdv();
		startNetwork();
		addAllGroups();
		startCommunication();
		manager = new Manager(network);
		manager.recovery(VARIABLES.ManagerFilePath);
		startSharingManager();
		
		if(startLocalServer)
			startLocalServer();	
		
		instance = this;
	}
	
	private void addAllGroups() {
		network.addGroup(Item.class.getSimpleName(), true);
		network.addGroup(User.class.getSimpleName(), true);
		network.addGroup(Contrat.class.getSimpleName(), true);
		network.addGroup(Favorites.class.getSimpleName(), true);
		network.addGroup(UserMessage.class.getSimpleName(), true);
	}
	
	private void startSharingManager() {
		sharingManager = new SharingManager(manager, network, com,  VARIABLES.ReplicationsAccount, VARIABLES.CheckTimeAccount);
		sharingManager.addResiliance(new ContratsResiliance(manager, com));
		sharingManager.addResiliance(new FavoritesResiliance(manager, com));
		sharingManager.addResiliance(new ItemResiliance(manager, com));
		sharingManager.addResiliance(new MessageResiliance(manager, com));
		sharingManager.addResiliance(new UserResiliance(manager, com));
		sharingManager.startSharing();
	}
	
	/**
	 * Start the communication package and adds the services
	 */
	private void startCommunication() {
		try {
			this.com = new Communication(network);
			com.addService(new MessageService());
			com.addService(new UpdateService());
			ClassSenderService.addSenderServices(com);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the network and keep instance reference.
	 * TODO keep reference ?
	 */
	private void startNetwork() {
		network = new Network(9800, VARIABLES.NetworkFolderName, VARIABLES.NetworkPeerName);
		network.setLogger(Level.SEVERE);
		//network.boot("tcp://85.171.121.182:9800"); TODO Check !
		try {
			if(new File(VARIABLES.BootstrapFilePath).exists()){
				FileReader xmlFile = new FileReader(VARIABLES.BootstrapFilePath);
				BufferedReader br = new BufferedReader(xmlFile);
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
	
			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    String xml = sb.toString();
			    br.close();
				RendezVousIp rdv = new RendezVousIp(xml);
				for(String ip : rdv.getIps()){
					network.boot(ip);
				}
			} else {
				Printer.printInfo(this, "startNetwork", VARIABLES.BootstrapFileName + " doesn't exist.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		network.start();
	}
	
	/**
	 * Launch the localServer for websocket communication between HTML5 UI and the P2P network.
	 */
	private void startLocalServer() {
		try {
			server = new EmbeddedRunner(8080).init().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the unique Application instance
	 * @return the Application instance.
	 */
	public static Application getInstance() {
		if(instance == null) {
			try {
				throw new Exception("there is no instance of this class");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Get the current Network.
	 * @return An instanced and started Network.
	 */
	public Network getNetwork() {
		return network;
	}
	
	/**
	 * Get the current Manager.
	 * @return a manager.
	 */
	public Manager getManager() {
		return manager;
	}
	
	/**
	 * Get the current Communication
	 * @return
	 */
	public Communication getCommunication(){
		return com;
	}
	
	public void stopServer() {
		if(server == null) return;
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Properly close the app : closing network & server, and saving datas.
	 */
	public void close() {
		System.out.println("closing ...");
		stopServer();
		network.stop();
		sharingManager.stopSharing();
		
		File f = new File(".data");
		FileWriter fw = null;
		try {
			fw = new FileWriter(f);
			fw.write(manager.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("bye !");	
	}
	
	private static void createAccountTest(boolean create) {
		if(create) {
			User user = new User("test", " ", "Doe", "John", "john.doe@sxp.com", "+33442044204");
			User sender = new User("test2", " ", "name", "firstName", "email@email.email", "phone"); 
			AsymKeysImpl keySender = sender.getKeys().copy();
			keySender.decryptPrivateKey(" ");
			
			Application.getInstance().getManager().getUserManager().registration(user);
			Application.getInstance().getManager().getUserManager().registration(sender);
			Application.getInstance().getManager().getUserManager().login("test", " ");
			
			/** Messages **/
			UserMessage m1 = new UserMessage(Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys(), sender.getKeys(), "question", "Bonjour ? Je peux etre votre ami ?");
			AsymKeysImpl key = sender.getKeys().copy();
			key.decryptPrivateKey(" ");
			m1.sign(key);
			UserMessage m2 = new UserMessage(sender.getKeys(), Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys(), "reponse", "non !!!");
			m2.sign(Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
			/** Conversation **/
			Conversations c = new Conversations(Application.getInstance().getManager().getUserManager().getCurrentUser());
			c.addMessage(m1);
			c.addMessage(m2);
			c.sign(Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
			Application.getInstance().getManager().getMessageManager().addConversations(c);
			/** Items **/
			Item item1 = new Item(user, "Potatoes", new Category(CATEGORY.FoodAndBeverages), "Great potatoes", "", "FRANCE", "Call me", 0L, 0L, TYPE.OFFER);
			item1.sign(Application.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
			Application.getInstance().getManager().getItemManager().addItem(item1);
			Item item2 = new Item(sender, "Carott", new Category(CATEGORY.FoodAndBeverages), "Great food for rabbits", "", "FRANCE", "Call me", 0L, 0L, TYPE.OFFER);
			item2.sign(keySender);
			Application.getInstance().getManager().getItemManager().addItem(item2);
			Application.getInstance().getManager().getFavoriteManager().addFavoritesItem(item2);
			/** Contracts **/
			Contrat contract = new Contrat("Food deal", Application.getInstance().getManager().getUserManager().getCurrentUser());
			contract.addItem(item1);
			contract.addItem(item2);
			contract.addClaus(new Clause("Art. 1", "Can't abort !"));
			Application.getInstance().getManager().getContratManager().addContrat(contract);
			
			Application.getInstance().getManager().getUserManager().logout();
			Application.getInstance().getManager().getUserManager().login("test2", " ");
			Application.getInstance().getManager().getUserManager().logout();
		}
	}
	
	/**
	 * We start the app here !
	 * @param args
	 */
	public static void main(String[] args) {
		new Application(true);
		Network n = Application.getInstance().getNetwork();
		createAccountTest(false);
		if(Desktop.isDesktopSupported()) {
		  try {
			Desktop.getDesktop().browse(new URI("http://localhost:8080/SXPManager/index.html"));
		  } catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		  }
		}
		
	}
}
