/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package model.data.item;

import java.util.ArrayList;

/**
 * This class contain a list of category permitted by the application.
 * This class can be instantiated for contain the category of an item.
 */
public class Category {
	
	/*
	 * This is just for translate text of categories.
	 */
	public static String linkText = "Category.CATEGORY.";
	
	/**
	 * CATEGORY is the list of Category permitted.
	 */
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
		NA
	}; 
	
	private CATEGORY choice;
	
	/**
	 * Constructor initialized by a CATEGORY chosen
	 * @param choice
	 */
	public Category(CATEGORY choice){
		setChoice(choice);
	}
	
	public Category(String choice){
		for(CATEGORY c: CATEGORY.values()) {
			String choiceLowCase = choice.toLowerCase();
			String catLowCase = c.toString().toLowerCase();
			if(catLowCase.equals(choiceLowCase)){
				this.choice = c;
				return;
			}
		}
		this.choice = CATEGORY.NA;
	}
	
	/**
	 * Return the choice
	 * @return
	 */
	public CATEGORY getChoice() {
		return choice;
	}
	
	/**
	 * Return the choice format String
	 * @return
	 */
	public String getStringChoice() {
		return choice.name();
	}
	
	/**
	 * Define the choice
	 * @param choice CATEGORY
	 */
	public void setChoice(CATEGORY choice) {
		this.choice = choice;
	}
	
	public static ArrayList<String> getAllCategorie(){
		ArrayList<String> all = new ArrayList<String>();
		for (CATEGORY c : CATEGORY.values()) {
			all.add(c.toString());
		}
		return all;
	}
}
