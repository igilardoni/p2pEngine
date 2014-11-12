package controller;

import view.Application;
import model.Accord;
import model.Accords;
import model.communications.AccordService;

public class AcceptAccord implements Validator{

	private Accord a;
	private String messageTo;
	
	public boolean errorMessageTo;
	
	public AcceptAccord(Accord a, String messageTo) {
		this.a = a;
		this.messageTo = messageTo;
		System.out.println("messageTo = " + messageTo);
		System.out.println("messageTo = " + messageTo);
		System.out.println("messageTo = " + messageTo);
		System.out.println("messageTo = " + messageTo);
		System.out.println("messageTo = " + messageTo);
		errorMessageTo = false;
	}
	
	@Override
	public boolean validate() {
		checkMessageTo();
		return !(errorMessageTo);
	}
	
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
