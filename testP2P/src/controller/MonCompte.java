package controller;

import model.User;
import view.Application;

/**
 * Acceder au compte de l'utilisateur connecte
 * @author Prudhomme Julien
 *
 */
public class MonCompte implements Validator{

	private String email;
	private String tel;
	private String adresse;
	private User user = Application.getInstance().getUsers().getConnectedUser();
	
	public boolean errorEmail, errorTel, errorAdresse;
	
	/**
	 * Permet d'editer son compte.
	 * @param email
	 * @param tel
	 * @param adresse
	 */
	public MonCompte(String email, String tel, String adresse) {
		this.email = email;
		this.tel = tel;
		this.adresse = adresse;
		
		errorEmail = errorTel = errorAdresse = false;
	}
	
	@Override
	public boolean validate() {
		Inscription insc = new Inscription("", "", "", adresse, email, tel, "", "");
		insc.validate();
		errorEmail = insc.errorEmail;
		errorTel = insc.errorTel;
		errorAdresse = insc.errorAdresse;
		return !(errorEmail || errorTel || errorAdresse);
	}

	@Override
	public boolean process() {
		user.setAdresse(adresse);
		user.setMail(email);
		user.setTel(tel);
		return true;
	}
}