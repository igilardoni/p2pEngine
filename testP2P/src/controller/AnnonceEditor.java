package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.itextpdf.text.DocumentException;

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
	private Objet obj = null; /* si l'on souhaite modifier un objet */
	private HTMLDocument doc = new HTMLDocument();
	private HTMLEditorKit kit = new HTMLEditorKit();
	
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
		if(!(troc || vente) || (troc && vente)) errorTroc = errorVente = true;
	}
	
	/**
	 * Le titre doit être d'au moins 3 caractères.
	 */
	private void checkTitle() {
		if(title.length() < 3) errorTitle = true;
	}
	
	/**
	 * Tris le document en remplaçant les balises <br> par des balises <p> puis utilise l'EditorKit pour séparer
	 * les attributs des contenus textuels et les renvoies
	 * @param html
	 * @return doc
	 */
	public synchronized String getAsText(String html)
    {
        try
        {
            // clear our document's contents
            doc.remove(0, doc.getLength());
            if(html == null || html.equals("")) return html;
 
            // change <br> tags to <p> since the kit doesn't convert by a new line
            html = html.replaceAll("<[bB][rR][\\s]*[/]?>","<p>");
 
            // use the editorKit for separate "attributes set" to "text-contents" by managing the document
            Reader r = new StringReader(html);
            kit.read(r, doc, 0);
 
            // return only "text-contents" part from the document ignoring this way all "attributes set"
            return doc.getText(0, doc.getLength()).trim();
        }
        
        catch(Exception e) { 
        	e.printStackTrace();
        	return null;
        }
    }
	
	/**
	 * Le résumé doit faire au moins 10 caractères.
	 */
	
	private void checkResume() {
		if(resume.length() < 10) errorResume = true;
	}
	
	/**
	 * La description doit faire au moins 10 caractères.
	 */
	private void checkDescription() {

		String plaintText = desc.replaceAll("<[^>]*>", "").replaceAll("\n", "").replaceAll(" ", "");
		System.out.println(plaintText);
		if(plaintText.length() < 10) errorDesc = true;
	}
	
	/**
	 * Le champ image peut être vide, sinon l'image doit être valide
	 */
	private void checkImg() {
		
		if(img == null) return;
	}

	
	public void setEditObjet(Objet obj) {
		this.obj = obj;
	}
	
	@Override
	public boolean process() {
		if(user == null) return false;
		
		if(obj == null) {
			Objet obj = new Objet(proposition, souhait, troc, vente, title, resume, desc, img, user);
			obj.setDate(System.currentTimeMillis());
			user.getObjets().add(obj);
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
		}
		
		/*	C'est ici pour d�activer le PDF */
		try { new AfficherPdf(this);} 
		catch (IOException e) { e.printStackTrace();}	
		
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
