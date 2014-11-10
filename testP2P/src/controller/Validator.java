package controller;

/**
 * Tout formulaire doit etre verifie (validate() avant d'envoyer la requete (process())
 * @author Prudhomme Julien
 *
 */
public interface Validator {
	
	/**
	 * Valide une ou des entrees
	 * @return false une ou des entrees sont erronnee
	 */
	public boolean validate();
	
	
	/**
	 * Se sert des donnees valides pour faire la requete
	 * @return vrai si la requete a reussi ou faux (erreur reseau, etc)
	 */
	public boolean process();
}
