package model;



public class PDFGenerator extends AbstractPdfGenerator {

	
	public PDFGenerator(ObjetPdfModel model){

		this.model = model;
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
