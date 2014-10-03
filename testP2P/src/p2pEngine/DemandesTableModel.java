package p2pEngine;

import gui.Application;

public class DemandesTableModel extends ObjetsTableModel {
	private static final long serialVersionUID = -574283428036595204L;

	private static final String[] COLUMNS_NAME = {"Objet voulu", "Objet à donner", "Auteur de la demande" };
	
	public DemandesTableModel(Objets objets, Application app) {
		super(objets, COLUMNS_NAME, app);
	}

	@Override
	public String getObjetColumnValue(int column, Object o) {
		Objet obj = (Objet) o;
		switch(column) {
		case 0: return  obj.getNom();
		case 1: return obj.getOtherName();
		case 2: return obj.getUser().getNom();
		}
		return null;
	}

}
