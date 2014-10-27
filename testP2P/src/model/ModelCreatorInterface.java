package model;

public interface ModelCreatorInterface {
	
	
	/**
	 * Génère un modèle
	 */
	public ObjetPDFModel getHTML();
	
	
	/**
	 * Crée le fichier PDF
	 */
	public void createPDF();

}
