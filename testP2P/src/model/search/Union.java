package model.search;

import model.Advertisable;
import model.Objet;
import model.ObjetsManagement;
import model.SearchListener;

public class Union extends BaseListenerTalker{
	
	public Union(BaseListenerTalker f1, BaseListenerTalker f2) {
		f1.addListener(this);
		f2.addListener(this);
	}

	@Override
	public void searchEvent(Advertisable adv) {
		Objet obj = (Objet) adv;
		if(!objets.contains(obj)) {
			objets.add(obj);
			notifyListener(obj);
		}
		
	}
}
