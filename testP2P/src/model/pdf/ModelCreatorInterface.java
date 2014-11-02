package model.pdf;

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