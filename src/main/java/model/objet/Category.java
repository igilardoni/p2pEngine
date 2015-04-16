package model.objet;

import util.*;

public class Category {
	
	private static String linkText = "Category.CATEGORY.";
	
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
	
	private CATEGORY choice;
	
	public Category(CATEGORY choice){
		setChoice(choice);
	}
	
	public String getChoice() {
		return choice.toString();
	}
	
	public void setChoice(CATEGORY choice) {
		this.choice = choice;
	}
	
	/*public void setChoice(String choice){
		for(CATEGORY c: CATEGORY.values()) {
			String choiceLowCase = choice.toLowerCase();
			String catLowCase = c.toString().toLowerCase();
			if(catLowCase.equals(choiceLowCase) || 
			Langues.getString(linkText+c.toString()).toLowerCase().equals(choiceLowCase)){
				this.choise = c;
				return;
			}
		}
		this.choise = CATEGORY.NC;
	}*/
	
	public static void main(String[] args){
		Category test = new Category(Category.CATEGORY.Baby);
		System.out.println("test 1 : "+test.getChoice());
		
		test = new Category(Category.CATEGORY.Books);
		System.out.println("test 2 : "+test.getChoice());

		test = new Category(Category.CATEGORY.NC);
		System.out.println("test 3 : "+test.getChoice());
	}
	
}
