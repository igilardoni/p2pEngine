package model.search;

import model.Advertisable;
import model.SearchListener;

public interface ListenerTalker extends SearchListener{
	public void addListener(SearchListener l);
	public void notifyListener(Advertisable adv);
}
