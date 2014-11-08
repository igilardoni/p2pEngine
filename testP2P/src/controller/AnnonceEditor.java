package controller;

import model.Objet;
import model.User;
import view.Application;

/**
 * Fenêtre de création/édition d'une annonce
 * @author 
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
	private String contre;
	private long argent;
	private Objet obj = null; /* si l'on souhaite modifier un objet */
	
	public boolean errorProposition, errorSouhait, errorTroc, errorVente, errorTitle, 
					errorResume, errorDesc, errorImg;
	
	
	public AnnonceEditor(boolean proposition, boolean souhait, 
			boolean troc, boolean vente, String title, 
			String resDesc, String desc, String img, String contre, long argent) {
		
		this.proposition = proposition;
		this.souhait = souhait;
		this.troc = troc;
		this.vente = vente;
		this.title = title;
		this.resume = resDesc;
		this.desc = desc;
		this.img = img;
		this.contre = contre;
		this.argent = argent;
		
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
	 * Au moins une case doit être cochée
	 */
	private void checkCheckBox() {
		if(!(troc || vente) || (troc && vente)) errorTroc = errorVente = true;
	}
	
	/**
	 * Le titre doit Ãªtre d'au moins 3 caractÃ¨res.
	 */
	private void checkTitle() {
		if(title.length() < 3) errorTitle = true;
	}
	
	/**
	 * Le rÃ©sumÃ© doit faire au moins 10 caractÃ¨res.
	 */
	private void checkResume() {
		if(resume.length() < 10) errorResume = true;
	}
	
	/**
	 * La description doit faire au moins 10 caractÃ¨res.
	 */
	private void checkDescription() {

		String plaintText = desc.replaceAll("<[^>]*>", "").replaceAll("\n", "").replaceAll(" ", "");
		if(plaintText.length() < 10) errorDesc = true;
	}
	
	/**
	 * Le champ image peut Ãªtre vide, sinon l'image doit Ãªtre valide
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
			Objet obj = new Objet(proposition, souhait, troc, vente, title, resume, desc, img, user, contre, argent);
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
			obj.setImg(img);
			obj.setContre(contre);
			obj.setArgent(argent);
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
		res += "Contre: " + contre;
		res += "Prix: " + argent;
		
		return res;	
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getTrocVente(){
		if(troc)
			return "Troc";
		return "Vente";
	}
	
	public String getPropSouhait(){
		if(proposition)
			return "Proposition";
		return "Souhait";
	}
	
	public String getResDescr(){
		return resume;
	}
	
	public String getDescrComp(){
		return desc;
	}
}