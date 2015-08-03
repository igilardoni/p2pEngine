package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Application;
import model.data.user.User;
import model.network.search.Search;
import model.network.search.SearchListener;

public class SearchUserController implements SearchListener<User>{
	
	private ArrayList<SearchListener<User>> listeners = new ArrayList<SearchListener<User>>();
	private HashMap<String, User> users = new HashMap<>();
	
	public void startSearch(String nick) {
		Search<User> s = new Search<User>(Application.getInstance().getNetwork(), User.class.getSimpleName(), "nick", false);
		s.addListener(this);
		s.search(nick, 0, 0);
	}
	
	public void addListener(SearchListener<User> l) {
		listeners.add(l);
	}
	
	private void notifyListeners(User event) {
		for(SearchListener<User> l: listeners) {
			l.searchEvent(event);
		}
	}
	
	@Override
	public void searchEvent(User event) {
		if(!event.checkSignature(event.getKeys())) return;
		if(users.containsKey(event.getKeys().getPublicKey().toString(16))) {
			User u = users.get(event.getKeys().getPublicKey().toString(16));
			if(event.getLastUpdated() > u.getLastUpdated()) {
				users.put(event.getKeys().getPublicKey().toString(16), event);
				notifyListeners(event);
			}
			else {
				users.put(event.getKeys().getPublicKey().toString(16), event);
				notifyListeners(event);
			}
		}
	}

}
