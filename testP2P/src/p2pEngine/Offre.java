package p2pEngine;

/**
 * Cette classe représente une offre (objet que l'on veut échanger)
 * @author Prudhomme Julien
 *
 */
public class Offre extends Objet{

	public final static String serviceName = "Offre";
	
	/**
	 * 
	 * @param nom Nom de l'objet à échanger
	 * @param description Description de l'objet
	 * @param enEchange l'objet que l'on voudrait en échange
	 * @param user l'utilisateur qui poste l'offre
	 */
	public Offre(String nom, String description, String enEchange, Utilisateur user) {
		super(nom, description, enEchange, user);
		// TODO Auto-generated constructor stub
	}

	public Offre(ObjetAdvertisement adv) {
		super(adv);
	}

	@Override
	protected String getFullName() {
		return serviceName + ":" + getNom();
	}

}
