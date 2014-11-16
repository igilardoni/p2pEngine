package model.search;

import model.advertisements.Advertisable;

/**
 * Les recherches étant asymétrique, on a besoin de recevoir et d'envoyer des notifications au fur et à mesure
 * @author Prudhomme Julien
 *
 */
public interface ListenerTalker extends SearchListener{
	public void addListener(SearchListener l);
	public void notifyListener(Advertisable adv);
}
