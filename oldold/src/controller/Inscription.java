package controller;

import view.Application;
import model.User;
import model.UsersManagement;

/**
 * Creer un nouveau compte utilisateur
 * @author 
 *
 */

public class Inscription implements Validator{

	private String login;
	private String password, password2;
	private String adresse;
	private String email, tel;
	private String nom, prenom;
	private UsersManagement users = Application.getInstance().getUsers();
	
	public boolean errorLogin, errorDuplicateLogin, errorPassword, errorPassword2, errorEmail, errorTel,
					errorAdresse, errorNom, errorPrenom;
	
	public Inscription(String login, String password, String password2, String adresse, 
			String email, String tel, String nom, String prenom) {
		
		this.login = login;
		this.password = password;
		this.password2 = password2;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
		this.prenom = prenom;
		this.nom = nom;
		
		errorLogin = errorPassword = errorPassword2 = errorEmail = errorTel = errorDuplicateLogin = false;
	}
	
	public boolean validate() {
		
		checkLogin();
		checkPassword();
		checkPassword2();
		checkAdresse();
		checkEmail();
		checkTel();
		checkNom();
		checkPrenom();
		
		return !(errorLogin || errorDuplicateLogin || errorPassword || errorPassword2 || errorEmail || errorTel || errorAdresse || errorNom || errorPrenom);
	}

	
	/**
	 * Le login doit faire plus de 3 caract�res et ne doit pas d�ja exister
	 */
	private void checkLogin() {
		if(login.length() < 3) errorLogin = true;
		if(users.userExists(login)) errorDuplicateLogin = true;
	}
	
	/**
	 * Le mot de passe doit faire plus de 6 caract�res
	 */
	private void checkPassword() {
		if(password.length() < 6) errorPassword = true;
	}
	
	/**
	 * Les deux mots de passe doivent �tre identiques
	 */
	private void checkPassword2() {
		if(!password.equals(password2)) errorPassword2 = true;
	}
	
	/**
	 * L'adresse ne doit pas �tre vide
	 */
	private void checkAdresse() {
		if(adresse.length() < 1) errorAdresse = true;
	}
	
	/**
	 * L'e-mail doit respecter le format
	 */
	private void checkEmail() {
		if(!email.matches("^[a-zA-Z_0-9.]*@[a-zA-Z_0-9]*\\.[a-z]*$")) errorEmail = true;
	}
	
	/**
	 * Le num�ro de t�l�phone ne doit comporter que 10 chiffres
	 */
	private void checkTel() {
		tel = tel.replaceAll("[^0-9]*", "");
		if(tel.length() != 10) errorTel = true;
		String normalizedTel = "";
		int i = 0;
		for(char c: tel.toCharArray()){
			normalizedTel += c;
			i++;
			if(i%2 == 0 && i < tel.length() -1) normalizedTel += ".";
		}
		tel = normalizedTel;
	}
	
	/**
	 * Le nom ne doit pas �tre vide
	 */
	private void checkNom() {
		if(nom.length() < 1) errorNom = true;
	}
	
	/**
	 * Le pr�nom de doit pas �tre vide
	 */
	private void checkPrenom() {
		if(prenom.length() < 1) errorPrenom = true;
	}
	
	public boolean process() {

		User user = new User(nom, prenom, adresse, tel, email, login, password);
		users.addUser(user);
		user.publish(Application.getInstance().getPeer().getDiscovery());
		
		return true;
	}
	
	public String toString() {
		String res = "Login: " + login + "\n";
		res += "Pass: " + password + "\n";
		res += "Pass2: " + password2 + "\n";
		res += "Adresse: " + adresse + "\n";
		res += "Email: " + email + "\n";
		res += "Tel: " + tel + "\n";
		return res;
	}
}