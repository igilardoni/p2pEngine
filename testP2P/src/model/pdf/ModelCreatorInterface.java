package model.pdf;

/**
 * 
 * @author Ismael Cussac
 *
 */
public interface ModelCreatorInterface {
	
	
	/**
	 * Génère un modèle
	 */
	public ObjetPdfModel createModel();
	
	
	/**
	 * Crée le fichier PDF
	 */
	public void createPDF();

}