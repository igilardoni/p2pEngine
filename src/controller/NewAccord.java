package controller;

import view.Application;
import model.Accord;
import model.Accords;
import model.Objet;
import model.communications.AccordService;

public class NewAccord implements Validator{
	private String messageFrom;
	private Objet obj;
	
	public boolean errorMessageFrom;
	
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
