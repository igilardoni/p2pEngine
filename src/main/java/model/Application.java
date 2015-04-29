package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.advertisement.AdvertisementInstaciator;
import model.manager.Manager;
import model.network.Network;
import model.network.communication.Communication;
import model.network.communication.service.ChatService;
import model.network.communication.service.TransmitAccountService;
import model.user.User;

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
	private Thread process = null; //Thread that do updates and saves 
	private boolean processRunning = false; //true if process main loop can be executed.
	
	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unchecked")
	public Application(boolean startLocalServer) {
		if(instance != null) {
			try {
				throw new Exception("this class can be instancied only once");
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		startNetwork();
		AdvertisementInstaciator.RegisterAllAdv();
		startCommunication();
		manager = new Manager(network);
		com.getService(TransmitAccountService.class.getName()).addListener(manager);
		
		network.addGroup("items");
		network.addGroup("users");
		
		startProcess();
		if(startLocalServer)
			startLocalServer();	
		
		instance = this;
	}
	
	/**
	 * Start the communication package.
	 */
	private void startCommunication() {
		try {
			this.com = new Communication(network);
		} catch (Exception e) {
			e.printStackTrace();
		}
		com.addService(new ChatService());
		com.addService(new TransmitAccountService());
	}
	
	/**
	 * Start the network and keep instance reference.
	 */
	private void startNetwork() {
		network = new Network(9706, ".peerFolder", "peer name");
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
	
	public void stopServer() {
		if(server == null) return;
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Own the thread that handle recurrent tasks.
	 */
	private void startProcess() {
		this.processRunning = true;
		process = new Thread(new Runnable() {

			@Override
			public void run() {
				while(processRunning) {
					
					
					//do all the things.
					
					
					try {
						Thread.sleep(120000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
		
	}
	
	private void stopProcess() {
		processRunning = false;
		if(process == null) return;
		while(process.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		process = null;
	}
	
	/**
	 * Properly close the app : closing network & server, and saving datas.
	 */
	public void close() {
		System.out.println("closing ...");
		stopServer();
		network.stop();
		stopProcess();
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
	
	/**
	 * We start the app here !
	 * @param args
	 */
	public static void main(String[] args) {
		new Application(true);
		User u = new User("nick", "pwd", "name", "firstname", "email", "phone");
		u.sign(u.getKeys());
		Application.getInstance().getManager().addUser(u);
		/*try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Application.getInstance().close(); */
	}
}
