package controller;

import model.Accord;
import model.Objet;
import model.User;

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
	
	public GeneratePdfContrat(Objet obj1, Objet obj2, User user1, User user2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public GeneratePdfContrat(Objet obj, User user1, User user2) {
		this.obj1 = obj;
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public boolean validate() {
		return obj1 != null && user1 != null && user2 != null;
	}

	public boolean process() {
		accord.createContrat(obj1, obj2, user1, user2);
		return true;
	}
}
