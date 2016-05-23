package model.pdf;


/**
 * Permet de g�n�rer un PDF de type objet � partir du mod�le Objet et de son template
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
}
