package model.pdf;


/**
 * Permet de générer un PDF de type utilisateur à partir du modèle User et de son template
 * @author Ismael Cussac
 *
 */
public class UserPdfGenerator extends AbstractPdfGenerator {
	
	public UserPdfGenerator(UserPdfModel model){

		this.texte.putAll(model.getTexteMap());
		this.image.putAll(model.getImageMap());
		this.bool.putAll(model.getBoolMap());
		
		createPdf(model.getTexteMap().get("fileOut"), model.getTexteMap().get("modele"));
		
	}
	
	protected void addContent(){
		
		addTexte();
		addImage();
		addCheckBox();
		
	}	
}
