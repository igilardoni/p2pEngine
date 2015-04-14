package model.pdf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet de g�n�rer un PDF de type contrat � partir des mod�les Objet et User et de leur template
 * @author Ismael Cussac
 *
 */
public class ContratPdfGenerator extends AbstractPdfGenerator{
	
	private List<String> modeles = new ArrayList<String>();
	private String fileOut = "";
	
	ObjetPdfModel obj1;
	ObjetPdfModel obj2 = null;
	UserPdfModel user1;
	UserPdfModel user2;
	
	
	public ContratPdfGenerator(ObjetPdfModel modelObjet1, ObjetPdfModel modelObjet2, UserPdfModel modelUser1, UserPdfModel modelUser2){

		this.obj1 = modelObjet1;
		this.obj2 = modelObjet2;
		this.user1 = modelUser1;
		this.user2 = modelUser2;
		
		genererContrat();
		fileOut += "Contrat "+ modelObjet1.getTexteMap().get("titreObjet")+"_"+modelObjet2.getTexteMap().get("titreObjet");
		mergePdf(modeles, fileOut);
	
		deleteFiles(modeles);
	}
	
	public ContratPdfGenerator(ObjetPdfModel modelObj1, UserPdfModel modelUser1, UserPdfModel modelUser2) {
		this.obj1 = modelObj1;
		this.user1 = modelUser1;
		this.user2 = modelUser2;
		
		genererContrat();
		fileOut += "Contrat "+ modelObj1.getTexteMap().get("titreObjet");
		mergePdf(modeles, fileOut);
	
		deleteFiles(modeles);
	}

	private void genererContrat() {
		
		creerFicheObjet(obj1);
		if(obj2 != null)
			creerFicheObjet(obj2);
		creerFicheUser(user1);
		creerFicheUser(user2);
		
	}

	private void creerFicheUser(UserPdfModel user) {
		this.texte.putAll(user.getTexteMap());
		this.image.putAll(user.getImageMap());
		this.bool.putAll(user.getBoolMap());
		createPdf(user.getTexteMap().get("nomUser"), user.getTexteMap().get("modele"));
		modeles.add(user.getTexteMap().get("nomUser"));
		viderMap();
	}

	private void creerFicheObjet(ObjetPdfModel obj){
		this.texte.putAll(obj.getTexteMap());
		this.image.putAll(obj.getImageMap());
		this.bool.putAll(obj.getBoolMap());
		createPdf(obj.getTexteMap().get("titreObjet"), obj.getTexteMap().get("modele"));
		modeles.add(obj.getTexteMap().get("titreObjet"));
		viderMap();
	}
	
	
	private void deleteFiles(List<String> liste){
		File myFile;
		
		for(String file : liste){
			myFile= new File("pdf/"+file+".pdf"); 
			myFile.setWritable(true);
			myFile.canWrite();
			myFile.canExecute();
			myFile.delete(); 
		}
	}
	
	private void viderMap(){
		this.texte.clear();
		this.image.clear();
		this.bool.clear();
	}
}
