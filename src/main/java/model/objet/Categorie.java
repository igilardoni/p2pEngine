package model.objet;

import util.*;

public class Categorie {
	
	private static String linkText = "Categorie.CATEGORY.";
	
	public static enum CATEGORY {
		Appliances,
		Baby,
		Beauty,
		Books,
		ClothingAccessories,
		Clothing,
		Computers,
		FoodAndBeverages,
		Health,
		Home,
		Industrial,
		Jewelry,
		Music,
		MusicalInstruments,
		Office,
		OtherProducts,
		Photography,
		Shoes,
		Sports,
		Tools,
		Toys,
		Vehicles,
		Video,
		VideoGames,
		OtherServices,
		PersonalCaring,
		Teaching,
		Transport,
		NC
	}; 
	
	private CATEGORY choix;
	
	public Categorie(CATEGORY choix){
		setChoix(choix);
	}
	
	public Categorie(String choix){
		setChoix(choix);		
	}
	
	public String getChoix() {
		return Langues.getString(linkText+choix.toString());
	}
	
	public void setChoix(CATEGORY choix) {
		this.choix = choix;
	}
	
	public void setChoix(String choix){
		boolean temp = false;
		
		for (int i = 0; i < CATEGORY.values().length; i++) {
			String s = choix.toString().toLowerCase();
			if(CATEGORY.values()[i].toString().toLowerCase().equals(s) ||
					Langues.getString(linkText+CATEGORY.values()[i]).toLowerCase().equals(s)){
				temp = true;
				this.choix = CATEGORY.values()[i];
				break;
			}
		}
		if(!temp)
			this.choix = CATEGORY.NC;
	}
	
	public static void main(String[] args){
		Categorie test = new Categorie(Categorie.CATEGORY.Baby);
		System.out.println("test 1 : "+test.getChoix());
		
		test = new Categorie("Baby");
		System.out.println("test 2 : "+test.getChoix());

		test = new Categorie("???");
		System.out.println("test 3 : "+test.getChoix());
	}
	
}
