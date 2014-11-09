package model.search;

import model.Advertisable;
import model.Objet;

public class Intersection extends BaseListenerTalker{

	protected BaseListenerTalker t1, t2;
	
	public Intersection(BaseListenerTalker t1, BaseListenerTalker t2) {
		this.t1 = t1;
		this.t2 = t2;
		t1.addListener(this);
		t2.addListener(this);
	}
	
	
	@Override
	public void searchEvent(Advertisable adv) {
		Objet obj = (Objet) adv;
		System.out.println("Objet trouvé " + obj.getTitre());
		if(t1.getObjets().contains(obj) && t2.getObjets().contains(obj)) {
			objets.add(obj);
			notifyListener(obj);
		}
	}

}
