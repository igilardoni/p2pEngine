package controller;

import view.Application;
import model.Accord;
import model.communications.AccordService;

/**
 * Controller pour refuser un accord
 * @author Prudhomme Julien
 *
 */
public class DeclineAccord implements Validator{

	private Accord accord;
	
	/**
	 * 
	 * @param accord L'accord à décliner
	 */
	public DeclineAccord(Accord accord) {
		this.accord = accord;
	}
	
	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public boolean process() {
		Application.getInstance().getUsers().getConnectedUser().getAccords().declineAccord(accord.getFrom(), accord.getTo(), accord.getAnnonce());
		return  AccordService.sendDecline(Application.getInstance().getChatter(), accord.getTo(), accord.getFrom(), accord.getAnnonce());
	}

}
