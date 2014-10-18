package controller;

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

		@Override
		public boolean validate() {
			// TODO Auto-generated method stub
			return false;
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
			return false;
		}
}
