package model.search;

import java.util.ArrayList;

import model.ObjetsManagement;
import model.advertisements.Advertisable;

/**
 * Classe abstraite.
 * Permet de définir le model suivant : le BaseListenerTalker reçoit des résultats
 * Il les analyses (cas d'un filtre : accepte ou non un résultat)
 * Puis envoit les résultats aux listeners (au fur et a mesure, un par un, recu -> analyse -> envoi)
 * Cela permet de brancher plusieurs filtre et de garder une bonne conconcordance malgré le faite
 * que sous jxta les résultat arrivent dans le désordre et dans un temps indéfini.
 * @author Prudhomme Julien
 *
 */
public abstract class BaseListenerTalker implements ListenerTalker{

	protected ArrayList<SearchListener> listeners = new ArrayList<SearchListener>();
	protected ObjetsManagement objets = new ObjetsManagement();
	
	@Override
	public abstract void searchEvent(Advertisable adv);

	@Override
	public void addListener(SearchListener l) {
		listeners.add(l);
	}

	@Override
	public void notifyListener(Advertisable adv) {
		for(SearchListener l: listeners) {
			l.searchEvent(adv);
		}
	}
	
	/**
	 * La liste des objets acceptés
	 * @return
	 */
	public ObjetsManagement getObjets() {
		return objets;
	}

}
