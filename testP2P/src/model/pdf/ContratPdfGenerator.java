package model.pdf;

public class ContratPdfGenerator extends AbstractPdfGenerator{
	
	public ContratPdfGenerator(ObjetPdfModel modelObjet, UserPdfModel modelUser ){

		this.texte.putAll(modelObjet.getTexteMap());
		this.image.putAll(modelObjet.getImageMap());
		this.bool.putAll(modelObjet.getBoolMap());
		this.texte.putAll(modelUser.getTexteMap());
		this.image.putAll(modelUser.getImageMap());
		this.bool.putAll(modelUser.getBoolMap());
		
		mergePdf();
		
	}
	
	protected void addContent() {
		
		addTexte();
		addImage();
		addCheckBox();
	}

}
