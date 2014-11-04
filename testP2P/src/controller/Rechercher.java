package controller;

import java.util.ArrayList;
import java.util.Random;

import view.Application;

import net.jxta.discovery.DiscoveryService;
import model.Objet;
import model.RemoteRessource;
import model.RemoteSearch;
import model.User;

public class Rechercher implements Validator{

		private String recherche;
		private boolean troc;
		private boolean vente;
		
		public boolean errorRecherche;
		public boolean errorTroc;
		public boolean errorVente;

		public Rechercher(String recherche, boolean troc, boolean vente){
			this.recherche = recherche;
			this.troc = troc;
			this.vente = vente;
			
			troc = vente = errorRecherche = errorTroc = errorVente = false;
		}

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

		@Override
		public boolean process() {
			// TODO Auto-generated method stub
			//System.out.println(recherche);
			
			
			DiscoveryService ds = Application.getInstance().getPeer().getDiscovery(); 
			RemoteSearch<Objet> rs = new RemoteSearch<Objet>(ds, "login");
			rs.search("recherche");
			ArrayList<Objet> obj = rs.getResults();
			if (obj!=null)
			{
				//System.out.println(obj.size());
				for(Objet u : obj)
					System.out.println( u.getDesc());
			}
			else
				System.out.println("ca marche pas ");
			return true;
		}
}
