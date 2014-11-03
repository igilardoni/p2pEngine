package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ObjetsManagement implements ObjetsManagementInterface, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8340926028918727791L;
	ArrayList <Objet> objets = new ArrayList<Objet>();
	
	@Override
	public boolean add(Objet objet) {
		// TODO Auto-generated method stub
		if (objets.contains(objet))
			return false;
		objets.add(objet);
		return true;
	}

	@Override
	public boolean delete(Objet objet) {
		// TODO Auto-generated method stub
		if (!objets.contains(objet))
			return false;
		objets.remove(objet);
		return true;
		
	}

	@Override
	public boolean delete(int pos) {
		// TODO Auto-generated method stub
		if (pos < 0 || pos > objets.size())
			return false;
		objets.remove(pos);
		return true ;
	}

	@Override
	public boolean setPos(Objet objet, int pos) {
		// TODO Auto-generated method stub
		if (pos < 0 || pos > objets.size())
			return false;
		objets.add(pos, objet);
		return true ;
	}

	@Override
	public boolean setPos(int objetPos, int newPos) {
		// TODO Auto-generated method stub
		if (objetPos < 0 || objetPos > objets.size() || newPos < 0 || newPos > objets.size())
			return false;
		
		Objet objet = objets.get(objetPos);
		objets.remove(objetPos);
		objets.add(newPos, objet);
		return true ;
	}

	@Override
	public boolean upPos(Objet objet) {
		// TODO Auto-generated method stub
		if (!objets.contains(objet))
			return false;
		
		int pos = objets.indexOf(objet);
		
		if (pos == 0)
			return false;
		
		return setPos(pos, pos-1);
			
	}

	@Override
	public boolean downPos(Objet objet) {
		// TODO Auto-generated method stub
		if (!objets.contains(objet))
			return false;
		int pos = objets.indexOf(objet);
		return setPos(pos, pos+1);
	}

	@Override
	public Objet get(int pos) {
		// TODO Auto-generated method stub
		if (pos <0 || pos > objets.size())
			return null;
		return objets.get(pos);
	}

	@Override
	public void sortByName() {
		// TODO Auto-generated method stub
		Collections.sort(objets);
	}

	@Override
	public int size() {
		return this.objets.size();
	}

	@Override
	public void removeAll() {
		this.objets.removeAll(objets);
		
	}

}
