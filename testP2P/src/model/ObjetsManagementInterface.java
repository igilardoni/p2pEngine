package model;

public interface ObjetsManagementInterface {
	
	/**
	 * Ajoute un objet
	 * @param objet
	 * @return false si l'ajout à échoué (objet qui existe déjà..)
	 */
	public boolean add(Objet objet);
	
	
	/**
	 * Supprime l'objet
	 * @param objet
	 * @return false si l'objet n'existe pas
	 */
	public boolean delete(Objet objet);
	
	
	/**
	 * Supprime l'objet à la position pos
	 * @param pos
	 * @return faux si pos négatif ou supérieur au nombre d'objet
	 */
	public boolean delete(int pos);
	
	
	/**
	 * Positionne l'objet à l'endroit voulu dans la liste
	 * @param objet
	 * @param pos
	 * @return faux si pos négatif ou supérieur au nombre d'objet
	 */
	public boolean setPos(Objet objet, int pos);
	
	
	/**
	 * Positionne l'objet à la position objetPos à la nouvelle position newPos
	 * @param objetPos
	 * @param newPos
	 * @return faux si pos négatif ou supérieur au nombre d'objet, ou si objetPos = newPos
	 */
	public boolean setPos(int objetPos, int newPos);
	
	
	/**
	 * Fait monter un objet dans la liste (diminue sa position de 1)
	 * @param objet
	 * @return faux si l'objet est déjà en première position
	 */
	public boolean upPos(Objet objet);
	
	
	/**
	 * Fait descendre un objet dans la liste(augmente sa position de 1)
	 * @param objet
	 * @return faux si l'objet est déjà en dernière position
	 */
	public boolean downPos(Objet objet);
	
	
	/**
	 * Retourne l'objet à la position pos
	 * @param pos
	 * @return null si pos < 0 ou pos > nombre d'objets
	 */
	public Objet get(int pos);
	
	/**
	 * Trie les objet par ordre alphabetique
	 * vous êtes pas obligée de la faire celle là si c'est trop dur, 
	 * conseil : les objets doivent implémenter l'interface Comparable
	 */
	public void sortByName();
	
	/**
	 * Retourne le nombre d'objets
	 * @return int le nombre d'objet
	 */
	public int size();
	
}
