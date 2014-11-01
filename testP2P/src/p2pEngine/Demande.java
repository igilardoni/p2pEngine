package p2pEngine;

/**
 * Cette classe représente une demande (objet que l'on cherche à acquierir)
 * @author Prudhomme Julien
 *
 */

public class Demande extends Objet{

	public final static String serviceName = "Demande";
	
	/**
	 * 
	 * @param nom Nom de l'objet que l'on souhaite obtenir
	 * @param description Description de l'objet
	 * @param enEchange l'objet que l'on peut donner en échange 
	 * @param user L'utilisateur qui poste la demande
	 */
	public Demande(String nom, String description, String enEchange,
			Utilisateur user) {
		super(nom, description, enEchange, user);
		// TODO Auto-generated constructor stub
	}
	
	public Demande(ObjetAdvertisement obj) {
		super(obj);
	}

	@Override
	protected String getFullName() {
		return serviceName + ":" + getNom();
	}


}
