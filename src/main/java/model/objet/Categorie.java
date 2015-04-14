package model.objet;

import util.*;

/*
 * TODO
 * Salut, je vois l'idée de ta classe
 * Mais c'est pas la bonne façon de faire (ne le prends pas mal)
 * Si j'ai bien compris, la classe sert :
 *   - a choisir une categorie (enfin sauvegarder la variable private choix)
 *   - a obtenir la string (dans la bonne langue) qui correspond à la category
 *   - a mettre la category "NC" si la string choisie n'existe pas.
 *   
 *   Donc quelques remarque :
 *    -on est dans le model, on propose juste des structures de données ainsi que le traitement de celle-ci
 *    -C'est à dire qu'a ce niveau là, on ne gère aucune interraction ui > controller > model
 *    -Donc par exemple, verifier qu'une categorie existe quand un utilisateur en propose une, 
 *     ca se ferai dans le controller (du coup un controller du type CategoryController, a voir pour la suite)
 *     - pour moi, a ce niveau là, on ne gère pas la représentation "textuelle" de la category, on reste avec l'enum
 *     getChoix renverrai donc juste l'enum, au pire tu peux rajouter surcharger la methode toString pour renvoyer
 *     ton "linkText" du genre Category.CATEGORY.Applicances, et la "traduction" se fait dans la vue en faite. C'est
 *     juste l'utilisateur qui a besoin de la traduc.
 *     Y'a peu etre des erreur de ce type dans le "old", mais du coup la ou va rien laisser passer !
 *     On en parlera. je t'ai mis des exemples de code aussi tu t'es compliqué pour rien
 *     
 *     Derniere chose, evite de mixer anglais / français (l'un ou l'autre, mais de pref anglais), pense a commenter 
 *     /documenter ton code, et met les tests dans src/test/java/model/objet/CategorieTest.java
 */

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
		/*boolean temp = false;
		
		for (int i = 0; i < CATEGORY.values().length; i++) {
	
			// tu fais un .toString sur un String ? :D
			String s = choix.toString().toLowerCase();
			if(CATEGORY.values()[i].toString().toLowerCase().equals(s) ||
					Langues.getString(linkText+CATEGORY.values()[i]).toLowerCase().equals(s)){
				temp = true;
				this.choix = CATEGORY.values()[i];
				break;
			}
		}
		if(!temp)
			this.choix = CATEGORY.NC;*/
		
		//plus jolie IMHO mais de toute je pense que c'est pas ici qu'il faut le faire !
		for(CATEGORY c: CATEGORY.values()) {
			String choiceLowCase = choix.toLowerCase();
			String catLowCase = c.toString().toLowerCase();
			if(catLowCase.equals(choiceLowCase) || 
			Langues.getString(linkText+c.toString()).toLowerCase().equals(choiceLowCase)){
				this.choix = c;
				return;
			}
		}
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
