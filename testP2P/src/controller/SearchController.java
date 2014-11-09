package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import view.Application;
import model.AbstractAdvertisement;
import model.Advertisable;
import model.Objet;
import model.ObjetsManagement;
import model.RemoteSearch;
import model.SearchListener;
import model.search.GrammarSearch;

public class SearchController implements Validator, SearchListener{

	
	private HashMap <String,String> rechercheFiltre = new HashMap <String,String>();
	private String recherche ; 
	private boolean troc;
	private boolean vente;
	
	public boolean errorRecherche;
	public boolean errorTroc;
	public boolean errorVente;
	
	public ObjetsManagement results = new ObjetsManagement();
	private SearchListener container;
	
	public SearchController(String recherche, boolean troc, boolean vente, SearchListener container)
	{
			this.recherche = recherche;
			this.troc = troc;
			this.vente = vente;
			this.container = container;
			
			errorRecherche = errorTroc = errorVente = false;
	}
	
	

	//La on traite l'entree utilisateur, champs bien rempli etc ..
	@Override

	public boolean validate() {
		checkRecherche();
		checkEchange();		
		
		return !(errorRecherche || errorTroc || errorVente);
	}
	
	private void checkRecherche(){
		if(recherche.length() < 3) errorRecherche = true;	
	}
	
	private void checkEchange(){
		if(!(troc || vente)) errorTroc = errorVente = true;
	}

	// c'est ici qu'on lancera la recherche si validate a retourner true (voir les autres controller pour l'exemple)
	@Override
	public boolean process() {
		DiscoveryService ds = Application.getInstance().getPeer().getDiscovery();
		//RemoteSearch<Objet> remoteSearch = new RemoteSearch(ds,"titre");
		//remoteSearch.addListener(this);
		GrammarSearch gs = new GrammarSearch(ds);
		gs.addListener(this);
		gs.search(recherche);
		//remoteSearch.search(recherche);
		return true;
	}
	
	public boolean filtrage(Advertisable adv) {
			
		
		return true;
	}



	@Override
	public void searchEvent(Advertisable adv) {
		if(filtrage(adv)) {
			results.add((Objet) adv);
			container.searchEvent(adv);
		}
		
		Application.getInstance().updateUI();
		
	}

}
