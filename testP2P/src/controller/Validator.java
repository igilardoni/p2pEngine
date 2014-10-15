package controller;

/**
 * Tout formulaire doit être vérifier (validate() avant d'envoyer la requete (process())
 * @author Prudhomme Julien
 *
 */
public interface Validator {
	
	/**
	 * Valide une ou des entrées
	 * @return false une ou des entrée sont érronnée
	 */
	public boolean validate();
	
	
	/**
	 * Se sert des données validée pour faire la requête
	 * @return vrai si la requete à réussi ou faux (erreur réseau, etc)
	 */
	public boolean process();
}
