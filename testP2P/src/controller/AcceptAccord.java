package controller;

import model.Accord;

public class AcceptAccord implements Validator{

	private Accord a;
	private String messageTo;
	
	public boolean errorMessageTo;
	
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
	
	private void checkMessageTo() {
		if(messageTo.length() < 10) errorMessageTo = true;
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		return false;
	}

}
