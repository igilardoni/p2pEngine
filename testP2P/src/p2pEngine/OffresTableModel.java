package p2pEngine;

import gui.Application;

public class OffresTableModel extends  ObjetsTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2701419468362814410L;
	private static final String[] COLUMNS_NAME = { "Objet à offrir", "Objet en échange", "Auteur de l'offre" };


	public OffresTableModel(Objets objets, Application app) {
		super(objets, COLUMNS_NAME, app);
	}



	@Override
	public String getObjetColumnValue(int column, Object o) {
		Objet obj = (Objet) o;
		switch(column) {
		case 0: return obj.getNom(); 
		case 1: return obj.getOtherName(); 
		case 2: return obj.getUser().getNom();
		}
		return null;
	}

}
