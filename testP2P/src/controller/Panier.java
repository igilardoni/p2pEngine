package controller;

import view.Application;
import model.ObjetsManagement;

/**
 * Controller pour le panier (ajout /suppression / gestion)
 * @author Prudhomme Julien
 *
 */
public class Panier implements Validator {
	
	public static enum Action {
		DELETE,
		UP,
		DOWN
	}
	
	private int i;
	private ObjetsManagement panier;
	private Action action;
	
	/**
	 * Pour effectuer une action sur le panier
	 * @param i L'objet visé (indice dans le panier)
	 * @param action L'action
	 */
	public Panier(int i, Action action) {
		panier = Application.getInstance().getUsers().getConnectedUser().getPanier();
		this.i = i;
		this.action = action;
	}

	private boolean validateDelete() {
		return panier.get(i) != null;
	}
	
	private boolean validateUp() {
		return panier.get(i) != null;
	}
	
	private boolean validateDown() {
		return panier.get(i) != null;
	}

	@Override
	public boolean validate() {
		switch(action) {
		case DELETE: return validateDelete(); //on souhaite supprimer l'objet du panier
		case UP: return validateUp(); //on souhaite faire monter l'objet dans la liste
		case DOWN: return validateDown(); //on souhaite le faire descendre
		default: return false;
		}
	}

	@Override
	public boolean process() {
		switch(action) {
		case DELETE: panier.delete(i); break;
		case UP: panier.upPos(panier.get(i)); break;
		case DOWN: panier.downPos(panier.get(i)); break;
		default: return false;
		}
		
		return true;
	}
}