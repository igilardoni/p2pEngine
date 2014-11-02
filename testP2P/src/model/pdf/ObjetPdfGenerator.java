package model.pdf;



public class ObjetPdfGenerator extends AbstractPdfGenerator {

	
	public ObjetPdfGenerator(ObjetPdfModel model){

		this.texte = model.getTexteMap();
		this.image = model.getImageMap();
		this.bool = model.getBoolMap();
		
		createPDF();
		
	}
	
	protected void addContent(){
		
		addTexte();
		addImage();
		addCheckBox();
		
	}	
}
