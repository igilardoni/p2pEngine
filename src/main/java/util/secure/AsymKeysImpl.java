package util.secure;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ElGamalKeyPairGenerator;
import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;
import util.Printer;
import util.StringToElement;

/**
 * AsymKeysImpl contains the public key (and P and G) and eventually the private key
 * If needed, this class can call KeyGenerator
 * @author Julien Prudhomme
 * @author Micha�l Dubuis
 * @author Sarah Bourkis
 */
public class AsymKeysImpl extends AbstractAdvertisement implements util.secure.encryptionInterface.AsymKeys<BigInteger> {
	static SecureRandom  random = new SecureRandom();
	
	private BigInteger p = new BigInteger ("124233341635855292420681698148845681014844866056212176632655173602444135581779341928584451946831820357622587249219477577145009300106828967466602146104562163160400103396735672041344557638270362523343149686623705761738910044071399582025053147811261321814632661084042311141045136246602979886564584763268994320823");
	private BigInteger g = new BigInteger ("57879985263161130068016239981615161174385902716647642452899971198439084259551250230041086427537114453738884538337956090286524329552098304591825815816298805245947460536391128315522193556464285417135160058086869161063941463490748168352401178939129440934609861888674726565294073773971086710395310743717916632171");
	
	private boolean wellGenerated = false;
	private BigInteger privateKey = null;
	private BigInteger publicKey = null;
	private BigInteger encryptedPrivateKey = null;
	/**
	 * This method is used to generate P and G
	 * @return ElGamalParameters for GenerateKeys
	 */
	private ElGamalParameters GeneratePG(){	
		ElGamalParameters params;
		ElGamalParametersGenerator apg;
		apg = new ElGamalParametersGenerator();
		apg.init(1024, 20, random);
        
		params = apg.generateParameters();
        p = params.getP();
        g = params.getG();
        return params;
	}
	
	/**
	 * This method is used to generate Public Key and Private Key
	 * @param params
	 */
	private void GenerateKeys(ElGamalParameters params, String password){
		ElGamalKeyGenerationParameters elGP = new ElGamalKeyGenerationParameters(random,params);
		ElGamalKeyPairGenerator KeyPair = new ElGamalKeyPairGenerator();
		KeyPair.init(elGP);
		AsymmetricCipherKeyPair cipher1 = KeyPair.generateKeyPair();
		publicKey = ((ElGamalPublicKeyParameters) cipher1.getPublic()).getY();
		privateKey = ((ElGamalPrivateKeyParameters)cipher1.getPrivate()).getX();
		wellGenerated = true;
		encryptPrivateKey(password);
	}
	
	/**
	 * This constructor is used for unknown PrivateKey
	 */
	public AsymKeysImpl(BigInteger p, BigInteger g, BigInteger publicKey){
		super();
		this.privateKey = null;
		this.publicKey = publicKey;
		this.p = p;
		this.g = g;
	}
	
	/**
	 * This constructor is used if Keys are already generated.
	 */
	public AsymKeysImpl(BigInteger p, BigInteger g, BigInteger publicKey, BigInteger privateKey) throws Exception{
		super();
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.p = p;
		this.g = g;
		if(!isCompatible())
			throw new IllegalArgumentException(this.getClass().getName()+" : Incompatible Keys !!!");
		else
			wellGenerated = true;
	}
	
	public AsymKeysImpl(String xml) {
		super(xml);
	}
	
	/**
	 * Empty Constructor.
	 */
	public AsymKeysImpl(){
		super();
	}
	
	/**
	 * This constructor is used for generate the public key and private key with possibility of generation p and q.
	 * @param pgGenerated - true for generate p and q, false else.
	 * @param password - Password to encrypt the privatekey.
	 */
	public AsymKeysImpl(boolean pgGenerate, String password){
		super();
		ElGamalParameters params;
		if(pgGenerate){
			params = GeneratePG();
		}
		else{
			params = new ElGamalParameters(p, g);
		}
		GenerateKeys(params, password);
	}
	
	/**
	 * Used to return the private key.
	 * @return
	 */
	public BigInteger getPrivateKey(){
		return privateKey;
	}
	
	/**
	 * Used to return the public key.
	 * @return
	 */
	public BigInteger getPublicKey(){
		return publicKey;
	}
	
	/**
	 * Used to return the p (of the public key).
	 * @return
	 */
	public BigInteger getP(){
		return p;
	}
	
	/**
	 * Used to return the q (of the public key).
	 * @return
	 */
	public BigInteger getG(){
		return g;
	}
	
	/**
	 * @param publicKey
	 */
	public void setPublicKey(BigInteger publicKey){
		this.publicKey = publicKey;
	}
	/**
	 * @param privateKey
	 */
	public void setPrivateKey(BigInteger privateKey){
		this.privateKey = privateKey;
	}
	/**
	 * @param p
	 */
	public void setP(BigInteger p){
		this.p = p;
	}
	/**
	 * @param g
	 */
	public void setG(BigInteger g){
		this.g = g;
	}
	
	@Override
	public boolean generate(String password) {
		ElGamalParameters params;
		params = GeneratePG();
		GenerateKeys(params, password);
		return wellGenerated;
	}
	
	/**
	 * Used to verify if publicKey, privateKey, p and g are compatible ! 
	 * @return true if keys are compatible, false else.
	 */
	public boolean isCompatible(BigInteger privateKey){
		if(this.getG() == null ||
				this.getP() == null ||
				privateKey == null)
			return false;
		BigInteger verif = this.getG().modPow(privateKey, this.getP());
		if(verif.compareTo(this.getPublicKey())==0)
			return true;
		else
			return false;
	}
	
	public boolean isCompatible() {
		return isCompatible(this.privateKey);
	}
	
	@Override
	public AsymKeysImpl clone(){
		AsymKeysImpl newKey = new AsymKeysImpl();
		newKey.setP(this.getP());
		newKey.setG(this.getG());
		newKey.setPrivateKey(this.getPrivateKey());
		newKey.setPublicKey(this.getPublicKey());
		return newKey;
	}
	
	/**
	 * Clone these keys including private key
	 * @param privateKey
	 * @return
	 */
	public AsymKeysImpl clone(boolean privateKey) {
		AsymKeysImpl res = clone();
		if(!privateKey) return res;
		res.setPrivateKey(this.privateKey);
		return res;
	}
	
	@Override
	public boolean equals(Object k){
		if(!(k instanceof AsymKeysImpl))
				return false;
		AsymKeysImpl key = (AsymKeysImpl)k;
		if(key.getP().compareTo(this.getP())!=0)
			return false;
		if(key.getG().compareTo(this.getG())!=0)
			return false;
		if(key.getPrivateKey().compareTo(this.getPrivateKey())!=0)
			return false;
		if(key.getPublicKey().compareTo(this.getPublicKey())!=0)
			return false;
		return true;
	}
	
	
	/**
	 * Crypt the private key with the given password.
	 * @param password
	 * @return
	 */
	public BigInteger getEncryptedPrivateKey(String password) {
		if(encryptedPrivateKey != null) return encryptedPrivateKey; //key already encrypted.
		
		Serpent cypher = new Serpent(password);
		return new BigInteger(cypher.encrypt(privateKey.toByteArray()));	
	}
	
	public BigInteger getDecryptedPrivateKey(String password) {
		if(encryptedPrivateKey == null)  {
			Printer.printError(this, "getDecriptedKey", "encyrpted key is null");
			return null;
		}
		if(privateKey != null) return privateKey;
		
		Serpent cypher = new Serpent(password);
		BigInteger clear = new BigInteger(cypher.decrypt(encryptedPrivateKey.toByteArray()));
		if(!isCompatible(clear)) {
			
			Printer.printError(this, "getDecryptedPrivateKey", "key no compatible");
			return null;
		}
		return clear;
	}
	
	public boolean decryptPrivateKey(String password) {
		BigInteger pk = getDecryptedPrivateKey(password);
		if(pk == null) return false;
		privateKey = pk;
		return true;
	}
	
	public void encryptPrivateKey(String password) {
		encryptedPrivateKey = getEncryptedPrivateKey(password);
		privateKey = null;
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected void setKeys() {
		addKey("publicKey", false, false);
		addKey("p", false, false);
		addKey("g", false, false);
		addKey("privateKey", false, false);
	}

	@Override
	protected void putValues() {
		addValue("publicKey", publicKey.toString(16));
		addValue("p", p.toString(16));
		addValue("g", g.toString(16));
		addValue("privateKey", encryptedPrivateKey.toString(16)); //the private key is always send encrypted.
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "publicKey": publicKey = new BigInteger(e.getValue()); return true;
		case "p": p = new BigInteger(e.getValue()); return true;
		case "g": g = new BigInteger(e.getValue()); return true;
		case "privateKey": 
			encryptedPrivateKey = new BigInteger(e.getValue()); 
			privateKey = null; 
			return true;
		}
		return false;
	}
	
}
