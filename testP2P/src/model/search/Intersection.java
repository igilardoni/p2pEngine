package model.search;

import model.Objet;
import model.advertisements.Advertisable;

/**
 * Fait l'intersection entre les résultats de 2 requetes.
 * @author Prudhomme Julien
 *
 */
public class Intersection extends BaseListenerTalker{

	protected BaseListenerTalker t1, t2;
	
	/**
	 * Fait l'intersection entre t1 et t2
	 * Pour cela, cet instance sera un listener de t1 et t2, 
	 * a chaque fois que t1 et t2 acceptent un objet, il est transmis ici, 
	 * et on vérifie que l'objet est dans les deux liste t1 et t2.
	 * @param t1
	 * @param t2
	 */
	public Intersection(BaseListenerTalker t1, BaseListenerTalker t2) {
		this.t1 = t1;
		this.t2 = t2;
		t1.addListener(this);
		t2.addListener(this);
	}
	
	
	public void searchEvent(Advertisable adv) {
		Objet obj = (Objet) adv;
		if(t1.getObjets().contains(obj) && t2.getObjets().contains(obj) && !objets.contains(obj)) {
			objets.add(obj);
			notifyListener(obj);
		}
	}

}
