package controller;

import model.Advertisable;
import model.Objet;
import model.ObjetsManagement;
import model.SearchListener;
import model.search.GrammarSearch;
import net.jxta.discovery.DiscoveryService;
import view.Application;

/**
 * Recherche un ou plusieurs mots dans les donnees des utilisateurs
 * @author 
 *
 */

public class SearchController implements Validator, SearchListener{

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

	public boolean process() {
		DiscoveryService ds = Application.getInstance().getPeer().getDiscovery();
		GrammarSearch gs = new GrammarSearch(ds);
		gs.addListener(this);
		gs.search(recherche);
		return true;
	}
	
	public boolean filtrage(Advertisable adv) {
		return true;
	}

	public void searchEvent(Advertisable adv) {
		if(filtrage(adv)) {
			results.add((Objet) adv);
			container.searchEvent(adv);
		}
		Application.getInstance().updateUI();
	}
}
