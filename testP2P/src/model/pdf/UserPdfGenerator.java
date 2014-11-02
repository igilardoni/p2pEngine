package model.pdf;



public class UserPdfGenerator extends AbstractPdfGenerator {
	
	public UserPdfGenerator(UserPdfModel model){

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
