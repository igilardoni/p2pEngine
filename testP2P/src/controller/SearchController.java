package controller;

import model.Objet;
import model.ObjetsManagement;
import model.advertisements.Advertisable;
import model.search.GrammarSearch;
import model.search.SearchListener;
import net.jxta.discovery.DiscoveryService;
import view.Application;

/**
 * Controller pour la recherche.
 * @author Prudhomme Julien
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
	
	/**
	 * Lance une recherche. Les résultats sont notifiés à container
	 * @param recherche
	 * @param troc
	 * @param vente
	 * @param container Les résultats arriverons au fur et a mesure, et seront notifié à cet objet
	 */
	public SearchController(String recherche, boolean troc, boolean vente, SearchListener container)
	{
			this.recherche = recherche;
			this.troc = troc;
			this.vente = vente;
			this.container = container;
			
			errorRecherche = errorTroc = errorVente = false;
	}

	@Override
	public boolean validate() {
		checkRecherche();
		checkEchange();		
		
		return !(errorRecherche || errorTroc || errorVente);
	}
	
	/**
	 * La recherche doit faire u moins 3 caractères.
	 */
	private void checkRecherche(){
		if(recherche.length() < 3) errorRecherche = true;	
	}
	
	/**
	 * Au moin une des cases doit être cochée.
	 */
	private void checkEchange(){
		if(!(troc || vente)) errorTroc = errorVente = true;
	}

	public boolean process() {
		DiscoveryService ds = Application.getInstance().getPeer().getDiscovery();
		GrammarSearch gs = new GrammarSearch(ds);
		gs.addListener(this); //on recoit les résultat dans cet objet en premier
		changeMinuscule(); //on passe la recherche en miniscule 
		gs.search(recherche); //on lance la recherche
		return true;
	}
	
	/**
	 * Passe la recherche en minuscule sauf AND, OR
	 */
	private void changeMinuscule() {
		String[] result = recherche.split("\\s");
		for (int x=0; x<result.length; x++){
		     if(!result[x].equals("AND") && !result[x].equals("OR"))
		    	 result[x] = result[x].toLowerCase();
		}
		 
		StringBuilder builder = new StringBuilder();
		for(String s : result) {
		     builder.append(s+" ");
		}
		
		recherche = builder.toString();

	}

	/**
	 * On applique un dernier filtre (troc/vente)
	 * @param adv 
	 * @return Vrai si l'objet est accepté
	 */
	public boolean filtrage(Advertisable adv) {
		Objet obj = (Objet) adv;
		if(!this.troc) {
			if(obj.isTroc()) return false;
		}
		if(!this.vente) {
			if(obj.isVente()) return false;
		}
		return true;
	}

	/**
	 * On a recu un résultat
	 */
	public void searchEvent(Advertisable adv) {
		if(filtrage(adv)) { //si l'objet est accepté par le filtre
			results.add((Objet) adv); //on le garde
			container.searchEvent(adv); //on notifie le listenaire l'interface graphique
		}
		Application.getInstance().updateUI(); //on met a jour l'affichage
	}
}
