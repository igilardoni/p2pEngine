package controller;

import model.Objet;
import model.User;
import view.Application;

public class AnnonceEditor implements Validator{
	
	/**
	 * Valide le formulaire
	 * @param propChecked "proposition" selectionné
	 * @param souhaitChecked "souhait" selectionné
	 * @param trocChecked Case "trock" cochée
	 * @param venteChecked Case "vente" cochée
	 * @param title titre entré
	 * @param resDesc resumé de la description entrée
	 * @param desc description
	 * @param img image
	 * @return
	 */
	
	private boolean proposition, souhait;
	private boolean troc, vente;
	private String title;
	private String resume;
	private String desc;
	private String img;
	private User user;
	
	public boolean errorProposition, errorSouhait, errorTroc, errorVente, errorTitle, 
					errorResume, errorDesc, errorImg;
	
	public AnnonceEditor(boolean proposition, boolean souhait, 
			boolean troc, boolean vente, String title, 
			String resDesc, String desc, String img) {
		
		this.proposition = proposition;
		this.souhait = souhait;
		this.troc = troc;
		this.vente = vente;
		this.title = title;
		this.resume = resDesc;
		this.desc = desc;
		this.img = img;
		
		user = Application.getInstance().getUsers().getConnectedUser();
		
		errorProposition = errorSouhait = errorTroc = errorVente = errorTitle 
		= errorResume = errorDesc = errorImg = false;
	}
	
	@Override
	public boolean validate() {
		
		checkCheckBox();
		checkTitle();
		checkResume();
		checkDescription();
		checkImg();
		
		
		return !(errorProposition || errorSouhait || errorTroc || errorVente || errorTitle 
				|| errorResume || errorDesc || errorImg);
		
	}
	
	/**
	 * Au moins une case doit être cochée.
	 */
	private void checkCheckBox() {
		if(!(troc || vente)) errorTroc = errorVente = true;
	}
	
	/**
	 * Le titre doit être d'au moins 3 caractères.
	 */
	private void checkTitle() {
		if(!(title.length() >= 3)) errorTitle = true;
	}
	
	/**
	 * Le résumé doit faire au moins 10 caractères.
	 */
	private void checkResume() {
		if(!(resume.length() >= 10)) errorResume = true;
	}
	
	/**
	 * La description doit faire au moins 10 caractères.
	 */
	private void checkDescription() {
		if(!(desc.length() >= 10)) errorDesc = true;
	}
	
	/**
	 * Le champ image peut être vide, sinon l'image doit être valide
	 */
	private void checkImg() {
		
		if(img == null) return;
	}

	@Override
	public boolean process() {
		if(user == null) return false;
		
		Objet obj = new Objet(proposition, souhait, troc, vente, title, resume, desc, img, user);
		obj.setDate(System.currentTimeMillis());
		user.getObjets().add(obj);
		return true;
		
	}
	
	public String toString() {
		String res = "";
		res += "Proposition: " + proposition + " " + "Souhait: " + souhait + "\n";
		res += "Troc: " + troc + " Argent: " + vente + "\n";
		res += "Titre: " + title + "\n";
		res += "Résumé: " + resume + "\n\n";
		res += "Description: \n" + desc + "\n";
		res += "Image: " + img;
		
		return res;
		
	}
	
	
}
