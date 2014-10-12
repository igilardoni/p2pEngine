package p2pEngine;

public interface UsersManagementInterface {

	/**
	 * Retourne un tableau contenant le nom des utilisateurs enregistrés
	 * @return String[] contenant les noms d'utilisateurs
	 */
	public String[] getUsersNames();
	
	
	
	/**
	 * Ajoute un utilisateur
	 * @param user
	 * @return True si l'ajout à réussi, False si un utilisateur à deja ce nom
	 */
	public boolean addUser(Utilisateur user);
	
	
	/**
	 * Supprime un utilisateur
	 * @param nom
	 * @param password
	 * @return True si la suppression à réussi, false si l'utilisateur n'existe pas ou si le mdp est incorrect
	 */
	public boolean deleteUser(String nom, String password);
	
	
	/**
	 * 
	 * @param nom
	 * @param password
	 * @return vrai si le combo nom/pass est correct
	 */
	public boolean checkLogin(String nom, String password);
	
	
	/**
	 * Renvoit une instance d'un utilisateur
	 * Cette fonction devrait utiliser checkLogin()
	 * @param nom
	 * @param password
	 * @return Un utilisateur ou null si nom/mdp incorrect
	 */
	public Utilisateur getUser(String nom, String password);
	
}
