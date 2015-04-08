package model.search;

import java.util.ArrayList;

import model.Advertisable;
import model.ObjetsManagement;
import model.SearchListener;

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
	
	public ObjetsManagement getObjets() {
		return objets;
	}

}
