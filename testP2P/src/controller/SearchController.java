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
import model.RemoteSearch;
import model.SearchListener;

public class SearchController implements Validator, SearchListener{

	
	private HashMap <String,String> rechercheFiltre = new HashMap <String,String>();
	private String recherche ; 
	private boolean troc;
	private boolean vente;
	
	public boolean errorRecherche;
	public boolean errorTroc;
	public boolean errorVente;
	
	
	public SearchController(String recherche, boolean troc, boolean vente)
	{
			this.recherche = recherche;
			this.troc = troc;
			this.vente = vente;
			
			troc = vente = errorRecherche = errorTroc = errorVente = false;
	}
	
	// remoteSearch a trouvé un nouvelle objet, on peut le traiter, demander l'affichage ...
	@Override
	public void searchEvent(Advertisable adv) {
		Advertisement ad = adv.getAdvertisement();
		
	}

	//La on traite l'entree utilisateur, champs bien rempli etc ..
	@Override

	public boolean validate() {
		checkRecherche();
		checkEchange();		
		
		return errorRecherche || errorTroc || errorVente;
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
	
		if(recherche.contains(";"))
			Filtage();
		else 
			rechercheFiltre.put("login", recherche);
		
		DiscoveryService ds = Application.getInstance().getPeer().getDiscovery();
		RemoteSearch<Objet> remoteSearch = new RemoteSearch(ds,"login");
		remoteSearch.search(recherche);
		
		if (remoteSearch.getResults() == null)
			return false;
		
		for (Objet obj : remoteSearch.getResults())
			searchEvent(obj);
			
		return true ;
	}
	
	public void Filtage()
	{
			String [] liste = recherche.split(";");
			ArrayList <String[]> p = new ArrayList<String[]>();
		
			for (String l : liste)
				p.add(l.split("?"));
		
			for (String[] l : p )
				this.rechercheFiltre.put(l[0], l[1]);
	}

}
