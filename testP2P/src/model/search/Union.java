package model.search;

import model.Objet;
import model.ObjetsManagement;
import model.advertisements.Advertisable;

public class Union extends BaseListenerTalker{
	
	/**
	 * Fais l'union de f1 et f2
	 * @param f1
	 * @param f2
	 */
	public Union(BaseListenerTalker f1, BaseListenerTalker f2) {
		f1.addListener(this);
		f2.addListener(this);
	}

	@Override
	public void searchEvent(Advertisable adv) {
		Objet obj = (Objet) adv;
		if(!objets.contains(obj)) { //on évite les doublons (annonce qui répondent à 2 requetes différentes)
			objets.add(obj);
			notifyListener(obj);
		}
		
	}
}
