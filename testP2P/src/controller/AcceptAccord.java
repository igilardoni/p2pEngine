package controller;

import view.Application;
import model.Accord;
import model.Accords;
import model.communications.AccordService;

/**
 * Controlleur pour accepter un accord
 * @author Prudhomme Julien
 *
 */
public class AcceptAccord implements Validator{

	private Accord a;
	private String messageTo;
	
	public boolean errorMessageTo; //erreur sur le message
	
	/**
	 * 
	 * @param a l'accord à accepter
	 * @param messageTo la réponse
	 */
	public AcceptAccord(Accord a, String messageTo) {
		this.a = a;
		this.messageTo = messageTo;
		errorMessageTo = false;
	}
	
	@Override
	public boolean validate() {
		checkMessageTo();
		return !(errorMessageTo);
	}
	
	/**
	 * Le message doit faire plus de 10 char
	 */
	private void checkMessageTo() {
		if(messageTo.length() < 10) errorMessageTo = true;
	}

	@Override
	public boolean process() {
		Accords accords = Application.getInstance().getUsers().getConnectedUser().getAccords();
		accords.acceptAccord(a);
		return AccordService.sendAccept(Application.getInstance().getChatter(), 
				Application.getInstance().getUsers().getConnectedUser().getLogin(), a.getFrom(), a.getAnnonce(), messageTo);
	}

}
