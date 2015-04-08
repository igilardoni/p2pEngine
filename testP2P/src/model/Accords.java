package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Accords implements Serializable{

	private static final long serialVersionUID = 3517141237494256533L;
	private ArrayList<Accord> accords =  new ArrayList<Accord>();
	
	public void addAccord(Accord a) {
		accords.add(a);
	}
	
	public void acceptAccord(Accord a) {
		a.setAccepted();
	}
	
	public void rateAccord(Accord a) {
		a.setRated();
	}
	
	public Accord getAccord(String from, String to, String annonce) {
		for(Accord a: accords) {
			if(a.getFrom().equals(from) && a.getTo().equals(to) && a.getAnnonce().equals(annonce)) {
				return a;
			}
		}
		return null;
	}
	
	public void declineAccord(String from, String to, String annonce) {
		Accord a = getAccord(from, to, annonce);
		accords.remove(a);
	}
	
	public ArrayList<Accord> getAccords() {
		return accords;
	}
	
	
	
	
}
