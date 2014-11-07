package model.pdf;


/**
 * Permet de générer un PDF de type objet à partir du modèle Objet et de son template
 * @author Ismael Cussac
 *
 */
public class ObjetPdfGenerator extends AbstractPdfGenerator {

	
	public ObjetPdfGenerator(ObjetPdfModel model){

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
