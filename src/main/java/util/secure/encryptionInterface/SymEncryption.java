package util.secure.encryptionInterface;

/**
 * Interface for encrypting and decrypting data with
 * a secret key.
 * @author Julien Prudhomme
 *
 * @param <D> The type of data encrypted / descripted 
 * @param <K> The type of the secret key
 */
public interface SymEncryption<D, K> {
	public void setSecretKey(K key);
	public D encrypt(D data);
	public D decrypt(D data);
}
