package controller;

import view.Application;
import model.communications.Accord;
import model.communications.AccordService;

/**
 * Controller pour noter une transatction
 * @author Prudhomme Julien
 *
 */
public class Rate implements Validator{

	private Accord a;
	private int rating;
	
	/**
	 * Note un accord, s'il n'as pas deja été noté
	 * @param a
	 * @param rating
	 */
	public Rate(Accord a, int rating) {
		this.a = a;
		this.rating = rating;
	}
	
	@Override
	public boolean validate() {
		return a.isRated();
	}

	@Override
	public boolean process() {
		String user = Application.getInstance().getUsers().getConnectedUser().getLogin();
		Application.getInstance().getUsers().getConnectedUser().getAccords().rateAccord(a);
		return AccordService.sendRate(Application.getInstance().getChatter(), user,
				a.getFrom() == user ? a.getTo():a.getFrom(), a.getAnnonce(), rating);
	}

}
