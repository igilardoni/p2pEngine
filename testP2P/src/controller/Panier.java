package controller;

import view.Application;
import model.Objet;
import model.ObjetsManagement;
import model.User;
import model.UsersManagement;

public class Panier implements Validator {
	
	private Objet obj = null;
	private User user;
	private String title;
	private String resume;
	private String img;
	private boolean troc;
	private boolean vente;
	
	private ObjetsManagement panier;
	
	public Panier(String title, String resume, String img, boolean troc, boolean vente) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.resume = resume;
		this.img = img;
		this.troc = troc;
		this.vente = vente;
		
		panier = Application.getInstance().getUsers().getConnectedUser().getPanier();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String res = "";
		res += "Troc: " + troc + " Argent: " + vente + "\n";
		res += "Titre: " + title + "\n";
		res += "R\u00E9sum\u00E9: " + resume + "\n\n";
		res += "Image: " + img;
		return res;
	}
	
	
	public String getTitle(){
		return title;
	}
	
		
	public String getResDescr(){
		return resume;
	}
	
	public String getTrocVente(){
		if(troc)
			return "Troc";
		return "Vente";
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		
		
		return false;
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		
		
		return false;
	}

	public void setEditObjet(Objet obj2) {
		// TODO Auto-generated method stub
		this.obj = obj;
	}

}
