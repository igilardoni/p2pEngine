package model.pdf;



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
