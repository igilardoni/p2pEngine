package network.api;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Search interface for advertisement
 * @author Julien Prudhomme
 *
 * @param <T> Type of advertisement searched
 */
public interface SearchListener <T extends Advertisement<?>>{
	/**
	 * Call to notify one or more object are found
	 * @param result the advertisement found.
	 */
	public void notify(Collection<T> result);
}
