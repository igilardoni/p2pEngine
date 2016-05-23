package oldmodel;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.platform.Module;
import net.jxta.platform.ModuleClassID;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.ModuleClassAdvertisement;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.ModuleSpecAdvertisement;
import net.jxta.protocol.PipeAdvertisement;


/**
 * Cette classe est a instancier qu'une seul fois et représente le peer lancé par l'utilisateur
 * Elle gère la configuration du réseau.
 * @author Prudhomme Julien
 *
 */

public class Peer implements PipeMsgListener, DiscoveryListener {
	
	
	/*public static void main(String[] args) throws PeerGroupException, IOException {

        // JXTA logs beaucoup (trop)
        Logger.getLogger("net.jxta").setLevel(Level.OFF);
        
        //On utilise un port aléatoire (entre 9000 et 9100)
        int port = 9000 + new Random().nextInt(100);
        
        //On enregistre notre annonce personalisé à la factory
        ObjetAdvertisement.register();
        
        Utilisateur user = new Utilisateur("Prudhomme Julien", "06.50.50.71.21", "crashxxl@hotmail.fr");
        Offre offre = new Offre("patate", "des bonnes patates", "carotte", user);
        
        
        
        Peer peer = new Peer(port);
        peer.start(); 

        
        //offre.publish(peer.discovery);
        
        peer.fetch_advertisements();
    } */
	
	
	private String peer_name;
	private PeerID peer_id;
	private File conf;
	private NetworkManager manager;
	
	/**
	 * Creer le peer. Appeler la méthode start() pour démarer le réseau.
	 * @param port Port d'écoute, attention aux pare-feu
	 */
	public Peer(int port) {
		
		//Défini le nom du peer avec un nombre aléatoire pour générer plus tard un peer_id unique
		peer_name = "Peer " + new Random().nextInt(1000000); 
		
		//Génère un PeerID aléatoire avec une seed aléatoire pour minimiser les doublons
		peer_id = IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, peer_name.getBytes());
		
		//La ou est stoqué le fichier temporaire de configuration
		conf = new File("." + System.getProperty("file.separator") + peer_name);
		
		//Network setup
		try {
			manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, peer_name, conf.toURI());
		} catch (IOException e) {
			//chemin incorrect ? 
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			NetworkConfigurator configurator = manager.getConfigurator();
            configurator.setTcpPort(port);
            configurator.setTcpEnabled(true);
            configurator.setTcpIncoming(true);
            configurator.setTcpOutgoing(true);
            configurator.setUseMulticast(true);
            configurator.setPeerID(peer_id);
		} catch (IOException e) {
			// ? Si le dossier a bien été crée pas trop de raison d'avoir cette exception 
			e.printStackTrace();
		}
		
	}
	
	//Note pour les groupes qui veulent reprendre mon code :
	//il faut changer la valeur des noms de groupe,  unicast , mutlicast et service
	//sinon on va être en conflit ! merci
	private static final String subgroup_name = "Reseau de troc";
    private static final String subgroup_desc = "...";
    private static final PeerGroupID subgroup_id = IDFactory.newPeerGroupID(PeerGroupID.defaultNetPeerGroupID, subgroup_name.getBytes());

    private static final String unicast_name = "P2P troc unicast";
    private static final String multicast_name = "P2P troc multicast";

    private static final String service_name = "P2P troc service";
	
    private PeerGroup subgroup;
    private PipeService pipe_service;
    private PipeID unicast_id;
    private PipeID multicast_id;
    private PipeID service_id;
    private DiscoveryService discovery;
    private ModuleSpecAdvertisement mdadv;
    
    /**
     * Démarre le service P2P
     * @throws IOException 
     * @throws PeerGroupException 
     */
	public void start() throws PeerGroupException, IOException {
		
		//on lance tout ! Si aucune exception de lancé, le réseau est en place
		PeerGroup net_group = manager.startNetwork();
		
		//passe en mode rendez vous (sorte de peer plus important que les autre) si besoin
		net_group.getRendezVousService().setAutoStart(true);
		
		
		//on se connecte au sous groupe, on le crée s'il n'existe pas
		//tout les sous groupes appartiennent au netGroup
		ModuleImplAdvertisement mAdv = null;
        try {
            mAdv = net_group.getAllPurposePeerGroupImplAdvertisement();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
		subgroup = net_group.newGroup(subgroup_id, mAdv, subgroup_name, subgroup_desc);
		
		 // On verifie qu'on est bien connecté au sous groupe !
        if (Module.START_OK != subgroup.startApp(new String[0]))
            System.err.println("Impossible de d�marer le sous-groupe");
        
        // on cree les pipes
        unicast_id = IDFactory.newPipeID(subgroup.getPeerGroupID(), unicast_name.getBytes());
        multicast_id = IDFactory.newPipeID(subgroup.getPeerGroupID(), multicast_name.getBytes());
        pipe_service = subgroup.getPipeService();
        pipe_service.createInputPipe(get_PipeAdvertisement(unicast_id, false), this);
        pipe_service.createInputPipe(get_PipeAdvertisement(multicast_id, true), this);
        
        
        // Pour que les autre peer nous découvre, on annonce un service "hello" pour se
        // présenter
        discovery = subgroup.getDiscoveryService();
        discovery.addDiscoveryListener(this);
        
        ModuleClassAdvertisement mcadv = (ModuleClassAdvertisement)
        AdvertisementFactory.newAdvertisement(ModuleClassAdvertisement.getAdvertisementType());
        
        mcadv.setName("P2PEngine:HELLO");
        mcadv.setDescription("Troc avec moi");
        
        ModuleClassID mcID = IDFactory.newModuleClassID();
        mcadv.setModuleClassID(mcID);
        
        // On dit au group qu'on existe !
        discovery.publish(mcadv);
        discovery.remotePublish(mcadv);
        
        
        //Apparament ca d�fini la manière dont on discute entre peers
        //vive la doc obsolète / inexistante !
        mdadv = (ModuleSpecAdvertisement)
                AdvertisementFactory.newAdvertisement(ModuleSpecAdvertisement.getAdvertisementType());
        mdadv.setName("P2PEngine:HELLO");
        mdadv.setVersion("Version 1.0");
        mdadv.setCreator("sun.com");
        mdadv.setModuleSpecID(IDFactory.newModuleSpecID(mcID));
        mdadv.setSpecURI("http://www.jxta.org/Ex1");

        service_id = IDFactory.newPipeID(subgroup.getPeerGroupID(), service_name.getBytes());
        PipeAdvertisement pipeadv = get_PipeAdvertisement(service_id, false);
        mdadv.setPipeAdvertisement(pipeadv);

        // On laisse le groupe connaitre le service
        discovery.publish(mdadv);
        discovery.remotePublish(mdadv);

        // on capte les msg ici
        pipe_service.createInputPipe(pipeadv, this);
        
	}
	
	
	/**
	 * Renvoi un adv pour faire reconnaitre un service de Pipe
	 * @param id l'id du pipe
	 * @param is_multicast multicast ou non
	 * @return un PipeAdvertisement
	 */
	private static PipeAdvertisement get_PipeAdvertisement(PipeID id, boolean is_multicast) {
        PipeAdvertisement adv = (PipeAdvertisement )AdvertisementFactory.
            newAdvertisement(PipeAdvertisement.getAdvertisementType());
        adv.setPipeID(id);
        if (is_multicast)
            adv.setType(PipeService.PropagateType); 
        else 
            adv.setType(PipeService.UnicastType); 
        adv.setName("Pipe");
        adv.setDescription("...");
        return adv;
    }


	@Override
	public void pipeMsgEvent(PipeMsgEvent event) {
		
	}
	
	public DiscoveryService getDiscovery() {
		return discovery;
	}

	public static String foundPeerIDtoPeerID(String founded) {
		return "urn:jxta:" + founded.substring(7);
	}
	
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		// On a trouvé un peer ! ouaaiii !
        //String found_peer_id = "urn:jxta:" + event.getSource().toString().substring(7);
       // System.out.println(this.peer_id + " a trouvé " + found_peer_id);
       // send_to_peer("Hello",null);
		
	}
	
	/**
	 * Lance un thread qui lance une recherche de nouveaux peer toute les 10 secondes
	 */
    public void fetch_advertisements() {
        new Thread("fetch advertisements thread") {
           public void run() {
        	   //SearchOffre search = new SearchOffre(null);
              while(true) {
                  discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, "Name", "P2PEngine:HELLO", 1, null);
                  //discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, "fullName", "Offre:patate", 1, search);
                  try {
                      sleep(10000);

                  }
                  catch(InterruptedException e) {} 
              }
           }
        }.start();
     }
	
}
