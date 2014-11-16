package model.search;

import model.Objet;
import model.ObjetsManagement;
import model.advertisements.Advertisable;

public class UserIntersection extends Intersection{

	private ObjetsManagement temp = new ObjetsManagement();
	
	/**
	 * Garde les objets de t1 uniquement si l'utilisateur est présent dans t2
	 * @param t1 Un filtre, une unions, etc
	 * @param t2 Un filtre, une unions, etc, dont on retient surtout l'utilisateur
	 */
	public UserIntersection(BaseListenerTalker t1, BaseListenerTalker t2) {
		super(t1, t2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void searchEvent(Advertisable adv) {
		Objet obj = (Objet) adv;
		
		if(t1.getObjets().contains(obj)) { //on reçoit un nouveau objet à accepter
			String user = obj.getUserName();
			if(t2.getObjets().containsUser(user)) {
				objets.add(obj);
				notifyListener(obj);
			}
			else {
				temp.add(obj);
			}
		}
		if(t2.getObjets().contains(obj)) { //on recoit un nouveau "utilisateur"
			String user = obj.getUserName();
			for(int i = 0; i < temp.size(); i++) {
				Objet o = temp.get(i);
				if(o.getUserName().equals(user)) {
					objets.add(o);
					temp.delete(i);
					i--;
					notifyListener(o);
				}
			}
		}
	}

}
