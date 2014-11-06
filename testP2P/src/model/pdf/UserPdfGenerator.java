package model.pdf;



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
