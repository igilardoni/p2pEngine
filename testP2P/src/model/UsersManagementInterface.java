package model;

/**
 * Interface pour la gestion des objets
 * @author Prudhomme Julien
 *
 */
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
	public boolean addUser(User user);
	
	
	/**
	 * Supprime un utilisateur
	 * @param login
	 * @param password
	 * @return True si la suppression à réussi, false si l'utilisateur n'existe pas ou si le mdp est incorrect
	 */
	public boolean deleteUser(String login, String password);
	
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @return vrai si le combo nom/pass est correct
	 */
	public boolean checkLogin(String login, String password);
	
	
	/**
	 * Renvoit une instance d'un utilisateur
	 * Cette fonction devrait utiliser checkLogin()
	 * @param login
	 * @param password
	 * @return Un utilisateur ou null si nom/mdp incorrect
	 */
	public User getUser(String login, String password);
	
	/**
	 * Connecte l'utilisateur correspondant
	 * @param login
	 * @param password
	 * @return false si la connexion à échouée
	 */
	public boolean connectUser(String login, String password);
	
	/**
	 * Déconnecte l'utilisateur
	 * @return false si aucun utilisateur était connecté.
	 */
	public boolean disconnectUser();
	
	public boolean userExists(String login);
	
	/**
	 * Renvoit l'utilisteur connecté
	 * @return null si personne n'est connecté.
	 */
	public User getConnectedUser();
	
	
}
