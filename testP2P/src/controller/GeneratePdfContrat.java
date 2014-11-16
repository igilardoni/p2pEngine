package controller;

import model.Objet;
import model.User;
import model.communications.Accord;

/**
 * Cree un PDF de type contrat de vente/echange
 * @author Ismael Cussac
 *
 */
public class GeneratePdfContrat implements Validator {

	private Accord accord;
	private User user1;
	private User user2;
	private Objet obj1;
	private Objet obj2 = null;
	
	/**
	 * Génère un contrat avec 2 objets à échanger
	 * @param accord
	 * @param obj1
	 * @param obj2
	 * @param user1
	 * @param user2
	 */
	public GeneratePdfContrat(Accord accord, Objet obj1, Objet obj2, User user1, User user2) {
		this.accord = accord;
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.user1 = user1;
		this.user2 = user2;
		this.obj1.setUser(user1);
		this.obj2.setUser(user2);
	}
	
	/**
	 * Génère un contrat sur un seul objet
	 * @param accord
	 * @param obj
	 * @param user1
	 * @param user2
	 */
	public GeneratePdfContrat(Accord accord, Objet obj, User user1, User user2) {
		this.accord = accord;
		this.obj1 = obj;
		this.user1 = user1;
		this.user2 = user2;
		this.obj1.setUser(user1);
	}
	
	@Override
	public boolean validate() {
		return obj1 != null && user1 != null && user2 != null;
	}

	@Override
	public boolean process() {
		accord.createContrat(obj1, obj2, user1, user2);
		return true;
	}
}
