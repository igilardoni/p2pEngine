package network.api;

/**
 * Search interface for advertisement
 * @author Julien Prudhomme
 *
 * @param <T> Type of advertisement searched
 */
public interface SearchListener<T extends Advertisement<?>> {
	/**
	 * Call to notify an object was found.
	 * @param result the advertisement found.
	 */
	public void notify(T result);
}
