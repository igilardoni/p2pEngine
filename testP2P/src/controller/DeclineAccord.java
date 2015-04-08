package controller;

import view.Application;
import model.Accord;
import model.communications.AccordService;

public class DeclineAccord implements Validator{

	private Accord accord;
	
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
