package controller;

import model.ImageBase64;
import model.Objet;
import model.User;
import view.Application;

/**
 * Fenetre de creation/edition d'une annonce
 * @author Prudhomme Julien
 * 
 */

public class AnnonceEditor implements Validator{
	
	private boolean proposition, souhait;
	private boolean troc, vente;
	private String title;
	private String resume;
	private String desc;
	private String img;
	private User user;
	private Objet obj = null; /* si l'on souhaite modifier un objet */
	
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
	 * Au moins une case doit etre cochee
	 */
	private void checkCheckBox() {
		if(troc == false && vente == false) errorTroc = errorVente = true;
	}
	
	/**
	 * Le titre doit etre d'au moins 3 caracteres.
	 */
	private void checkTitle() {
		if(title.length() < 3) errorTitle = true;
	}
	
	/**
	 * Le resume doit faire au moins 10 caracteres.
	 */
	private void checkResume() {
		if(resume.length() < 10) errorResume = true;
	}
	
	/**
	 * La description doit faire au moins 10 caracteres.
	 */
	private void checkDescription() {

		String plaintText = desc.replaceAll("<[^>]*>", "").replaceAll("\n", "").replaceAll(" ", "");
		if(plaintText.length() < 10) errorDesc = true;
	}
	
	/**
	 * Le champ image peut etre vide, sinon l'image doit etre valide
	 */
	private void checkImg() {
		
		if(img == null) return;
	}

	public void setEditObjet(Objet obj) {
		this.obj = obj;
	}
	
	public boolean process() {
		if(user == null) return false;
		
		if(obj == null) {
			Objet obj = new Objet(proposition, souhait, troc, vente, title, resume, desc, img == null ? null:ImageBase64.encode(img), user);
			obj.setUserName(user.getLogin());
			obj.setDate(System.currentTimeMillis());
			user.getObjets().add(obj);
			obj.publish(Application.getInstance().getPeer().getDiscovery());
		}
		else {
			obj.setProposition(proposition);
			obj.setSouhait(souhait);
			obj.setTroc(troc);
			obj.setVente(vente);
			obj.setTitre(title);
			obj.setResume(resume);
			obj.setDesc(desc);
			if(obj.getImg() != null && !obj.getImg().equals(img) && img != null) {
				obj.setImg(ImageBase64.encode(img));
			}
			
			obj.update(Application.getInstance().getPeer().getDiscovery());
			
		}	
		return true;	
	}
	
	public String toString() {
		String res = "";
		res += "Proposition: " + proposition + " " + "Souhait: " + souhait + "\n";
		res += "Troc: " + troc + " Argent: " + vente + "\n";
		res += "Titre: " + title + "\n";
		res += "R\u00E9sum\u00E9: " + resume + "\n\n";
		res += "Description: \n" + desc + "\n";
		res += "Image: " + img;
		
		return res;	
	}
}
