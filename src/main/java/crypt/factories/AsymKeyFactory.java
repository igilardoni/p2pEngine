package crypt.factories;

import org.bouncycastle.crypto.params.ElGamalParameters;

import crypt.api.key.AsymKey;
import crypt.impl.key.ElGamalAsymKey;

/**
 * {@link AsymKey} factory
 * @author Julien Prudhomme
 *
 */
public class AsymKeyFactory {

	/**
	 * Create the default implemented {@link AsymKey}
	 * @return an {@link AsymKey}
	 */
	public static AsymKey<?> createDefaultAsymKey() {
		return ElGamalAsymKeyFactory.create(false);
	}
	
	/**
	 * Create an {@link ElGamalAsymKey}
	 * @param generateParams true if the implementation should generate new parameters or false for default parameters
	 * @return an {@link ElGamalAsymKey}
	 */
	public static ElGamalAsymKey createElGamalAsymKey(boolean generateParams) {
		return ElGamalAsymKeyFactory.create(generateParams);
	}
	
	/**
	 * Create an ElGamalAsymKey from existing {@link ElGamalParameters}
	 * @param params The parameters used for creating the keys
	 * @return an {@link ElGamalAsymKey}
	 */
	public static ElGamalAsymKey createElGamalAsymKey(ElGamalParameters params) {
		return ElGamalAsymKeyFactory.createFromParameters(params);
	}
}
