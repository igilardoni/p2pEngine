package model.pdf;

import java.util.ArrayList;
import java.util.List;

public class ContratPdfGenerator extends AbstractPdfGenerator{
	
	private List<String> modeles = new ArrayList<String>();
	private String fileOut = "";
	
	public ContratPdfGenerator(ObjetPdfModel modelObjet, UserPdfModel modelUser ){

		this.texte.putAll(modelObjet.getTexteMap());
		this.image.putAll(modelObjet.getImageMap());
		this.bool.putAll(modelObjet.getBoolMap());
		this.texte.putAll(modelUser.getTexteMap());
		this.image.putAll(modelUser.getImageMap());
		this.bool.putAll(modelUser.getBoolMap());
		
		modeles.add(modelObjet.getTexteMap().get("modele"));
		modeles.add(modelUser.getTexteMap().get("modele"));
		
		fileOut += "Contrat "+ modelObjet.getTexteMap().get("titreObjet");
		mergePdf(modeles, fileOut);
		
		
	}
	
	protected void addContent() {
		
		addTexte();
		addImage();
		addCheckBox();
	}

}
