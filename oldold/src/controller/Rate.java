package controller;

import view.Application;
import model.Accord;
import model.communications.AccordService;

public class Rate implements Validator{

	private Accord a;
	private int rating;
	
	public Rate(Accord a, int rating) {
		this.a = a;
		this.rating = rating;
	}
	
	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public boolean process() {
		String user = Application.getInstance().getUsers().getConnectedUser().getLogin();
		Application.getInstance().getUsers().getConnectedUser().getAccords().rateAccord(a);
		return AccordService.sendRate(Application.getInstance().getChatter(), user,
				a.getFrom() == user ? a.getTo():a.getFrom(), a.getAnnonce(), rating);
	}

}
