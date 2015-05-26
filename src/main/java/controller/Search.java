package controller;

import java.util.ArrayList;

import model.item.Item;
import model.network.search.SearchListener;

public class Search implements SearchListener<Item>{
ArrayList<String> liste_tem =  new ArrayList<>();
	@Override
	public void searchEvent(Item event) {
		
		
	
		liste_tem.add(event.getTitle()+":"+event.getCountry()+":"+event.getDescription());
		
		
	}

}
