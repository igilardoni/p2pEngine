package new_model;

public class new_categorie {

	public enum categorie {A, B, C} ;  
	
	private String choix;
	
	public new_categorie(new_categorie.categorie cat){
		this.choix = cat.toString() ;
	}
	
	public new_categorie(String choix){
		boolean temp = false;
		for (categorie type : categorie.values()){
			if(!type.equals(choix));
				// a voir
		}
		
	}


	public String getChoix() {
		return choix;
	}


	public void setChoix(String choix) {
		this.choix = choix;
	}
	
	
	
}
