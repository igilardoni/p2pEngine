package controller;

import model.Advertisable;
import model.SearchListener;

public class SearchController implements Validator, SearchListener{

	// remoteSearch a trouvé un nouvelle objet, on peut le traiter, demander l'affichage ...
	@Override
	public void searchEvent(Advertisable adv) {
		// TODO Auto-generated method stub
		
	}

	//La on traite l'entree utilisateur, champs bien rempli etc ..
	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	// c'est ici qu'on lancera la recherche si validate a retourner true (voir les autres controller pour l'exemple)
	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		return false;
	}

}
