package controller;

import view.Application;
import model.Objet;
import model.communications.Accord;
import model.communications.AccordService;
import model.communications.Accords;

/**
 * Creer un nouvel accord.
 * @author Prudhomme Julien
 *
 */
public class NewAccord implements Validator{
	private String messageFrom;
	private Objet obj;
	
	public boolean errorMessageFrom;
	
	/**
	 * Créé un accord sur l'objet Obj. L'accord est envoyé au possesseur de l'annonce.
	 * @param obj
	 * @param messageFrom Le message à envoyer avec l'accord.
	 */
	public NewAccord(Objet obj, String messageFrom) {
		this.messageFrom = messageFrom;
		this.obj = obj;
		
		errorMessageFrom = false;
	}
	
	@Override
	public boolean validate() {
		checkMessageFrom();
		return !(errorMessageFrom);
	}
	
	/**
	 * Les message doivent faire au moins 10 caractère.
	 */
	private void checkMessageFrom() {
		if(messageFrom.length() < 10) {
			errorMessageFrom = true;
		}
	}

	@Override
	public boolean process() {
		
		Accords accords = Application.getInstance().getUsers().getConnectedUser().getAccords();
		accords.addAccord(new Accord(Application.getInstance().getUsers().getConnectedUser().getLogin(), 
				obj.getUser().getLogin(), messageFrom, obj.getTitre(), obj));
		
		return AccordService.sendAccord(Application.getInstance().getChatter(), Application.getInstance().getUsers().getConnectedUser().getLogin(),
				obj.getUser().getLogin(), obj.getTitre(), messageFrom);
	}

}
