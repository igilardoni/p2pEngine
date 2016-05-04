package crypt.factories;

import crypt.api.hashs.Hasher;
import crypt.impl.hashs.SHA256Hasher;

/**
 * {@link Hasher} factory
 * @author Julien Prudhomme
 *
 */
public class HasherFactory {
	
	/**
	 * Create the default implementation of {@link Hasher}
	 * @return a {@link Hasher}
	 */
	public static Hasher createDefaultHasher() {
		return createSHA256Hasher();
	}
	
	/**
	 * Create a SHA256 implementation of {@link Hasher}
	 * @return a {@link SHA256Hasher}
	 */
	public static SHA256Hasher createSHA256Hasher() {
		return new SHA256Hasher();
	}
}
