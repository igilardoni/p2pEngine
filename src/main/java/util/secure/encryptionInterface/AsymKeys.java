package util.secure.encryptionInterface;

public interface AsymKeys<K> {
	
	/**
	 * Generate the private and public key according to the implemented asymmetric algorithm
	 * @return True if successful generation
	 */
	public boolean generate();
	
	
	
	/**
	 * Get the publicKey
	 * @return
	 */
	public K getPublicKey();
	
	/**
	 * Get the privateKey
	 * @return
	 */
	public K getPrivateKey();
	
	/**
	 * Get G of the publicKey
	 * @return
	 */
	public K getG();
	
	/**
	 * Get P of the publicKey
	 * @return
	 */
	public K getP();
}
