package util.secure.encryptionInterface;

/**
 * public interface for encrypt/ decrypt operation with an asymetric algorithm.
 * The interface isn't dependant on the algorithm.
 * @author Julien Prudhomme
 *
 * @param <K> The type of private and public keys (Typically a BigInteger)
 * @param <D> The type of crypted or decrypted data (Typically byte[])
 */
public interface AsymEncryption<K, D> {
	
	
	/**
	 * Set the public/private key if known, and the right P & G.
	 * @param keys
	 */
	public void setAsymsKeys(AsymKeys<K> keys);
	
	/**
	 * Encrypt the data with a public key.
	 * @param data the data to encrypt
	 * @param publicKey the public key
	 * @return the data encrypted
	 */
	public D encryptWithPublicKey(D data);
	
	/**
	 * Encrypt with the data with a private key (typically for emit a signature)
	 * @param data the data to encrypt
	 * @param privateKey the private key
	 * @return the data encrypted
	 */
	public D encryptWithPrivateKey(D data);
	
	
	/**
	 * Decrypt the data with the public key (typically for decrypt a signature)
	 * @param data the data to decrypt
	 * @param publicKey the public key
	 * @return the data decrypted.
	 */
	public D decryptWithPublicKey(D data);
	
	/**
	 * Decrypt the data with the private key
	 * @param data the data encrypted
	 * @param privateKey the private key
	 * @return the data decrypted
	 */
	public D descryptWithPrivateKey(D data);
}
