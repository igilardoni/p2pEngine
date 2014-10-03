package p2pEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Représente une liste d'objet
 * @author Prudhomme Julien
 *
 */

public class Objets {
	private HashMap<String, Objet> map = new HashMap<String, Objet>();
	
	public Objets() {
		
	}
	
	/**
	 * Ajoute un objet à la liste
	 * @param o Un object
	 * @return true si l'objet a bien été ajouté (false si il été deja présent)
	 */
	public boolean add(Objet o) {
		
		return map.put(o.getNom() + o.getUser().toString(), o) == null;
	}
	
	/**
	 * Supprime un objet de la liste
	 * @param o Un object
	 * @return true si l'objet était présent et a été supprimé
	 */
	public boolean delete(Objet o) {
		return map.remove(o) != null;
	}
	
	/**
	 * Retourne le nombre d'élément
	 * Utile pour connaitre le nombre de ligne a afficher
	 * @return int le nombre d'élément de la liste
	 */
	public int size() {
		return map.size();
	}
	
	/**
	 * Retourne le nième élément de la liste
	 * @param n l'index de l'objet
	 * @return L'Objet à l'index n
	 */
	public Objet get(int n) {
		
		if(n < 0 || n >= size()) throw new IllegalArgumentException("n doit être inférieur à size()");
		
		return (Objet) map.values().toArray()[n];
	}
	
	
	
	
	
	
}
