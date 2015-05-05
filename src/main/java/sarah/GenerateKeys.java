package sarah;


import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ElGamalKeyPairGenerator;
import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

/**
 * AsymKeysImpl contains the public key (and P and G) and eventually the private key
 * If needed, this class can call KeyGenerator
 * @author Michael
 *
 */
public class GenerateKeys {
	static SecureRandom  random = new SecureRandom();

	private static BigInteger ONE = BigInteger.ONE;
	private static BigInteger TWO = new BigInteger("2");
	private BigInteger p = new BigInteger ("124233341635855292420681698148845681014844866056212176632655173602444135581779341928584451946831820357622587249219477577145009300106828967466602146104562163160400103396735672041344557638270362523343149686623705761738910044071399582025053147811261321814632661084042311141045136246602979886564584763268994320823");
	private boolean pGenerated = false;
	private BigInteger g = new BigInteger ("57879985263161130068016239981615161174385902716647642452899971198439084259551250230041086427537114453738884538337956090286524329552098304591825815816298805245947460536391128315522193556464285417135160058086869161063941463490748168352401178939129440934609861888674726565294073773971086710395310743717916632171");

	private boolean wellGenerated = false;
	private BigInteger q = null;
	private BigInteger privateKey = null;
	private BigInteger publicKey = null;

	private static int pLength = 1024;
	private static int keyLength = 160;

	private AsymmetricKeyParameter privateKeyAs;
	private AsymmetricKeyParameter publicKeyAs;
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*/																										  /*/
	/*/									SARAH'S GENERATION OF KEYS											  /*/
	/*/																										  /*/
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
		pGenerated = true;
		return params;
	}

	/**
	 * This method is used to generate Public Key and Private Key
	 * @param params
	 */
	private void GenerateKeys(ElGamalParameters params){
		ElGamalKeyGenerationParameters elGP = new ElGamalKeyGenerationParameters(random,params);
		ElGamalKeyPairGenerator KeyPair = new ElGamalKeyPairGenerator();
		KeyPair.init(elGP);
		AsymmetricCipherKeyPair cipher1 = KeyPair.generateKeyPair();
		setPrivateKeyAs(cipher1.getPrivate());
		setPublicKeyAs(cipher1.getPublic());
		publicKey = ((ElGamalPublicKeyParameters) cipher1.getPublic()).getY();
		privateKey = ((ElGamalPrivateKeyParameters)cipher1.getPrivate()).getX();
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*/																										  /*/
	/*/										CLASS GESTION BY MICHAEL										  /*/
	/*/																										  /*/
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This constructor is used for unknown PrivateKey
	 */
	public GenerateKeys(BigInteger p, BigInteger g, BigInteger publicKey){
		this.privateKey = null;
		this.publicKey = publicKey;
		this.p = p;
		this.g = g;
	}

	/**
	 * This constructor is used if Keys are already generated.
	 */
	public GenerateKeys(BigInteger p, BigInteger g, BigInteger publicKey, BigInteger privateKey) throws Exception{
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.p = p;
		this.g = g;
		if(!isCompatible())
			throw new IllegalArgumentException(this.getClass().getName()+" : Incompatible Keys !!!");
		else
			wellGenerated = true;
	}

	/**
	 * Empty Constructor.
	 */
	public GenerateKeys(){
	}

	/**
	 * This constructor is used for generate the public key and private key with possibility of generation p and q.
	 * @param pgGenerated - true for generate p and q, false else.
	 */
	public GenerateKeys(boolean pgGenerate){
		ElGamalParameters params;
		if(pgGenerate){
			params = GeneratePG();
		}
		else{
			params = new ElGamalParameters(p, g);
		}
		GenerateKeys(params);
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


	public boolean generate() {
		return wellGenerated;
	}

	/**
	 * Used to verify if publicKey, privateKey, p and g are compatible ! 
	 * @return true if keys are compatible, false else.
	 */
	public boolean isCompatible(){
		if(this.getG() == null ||
				this.getP() == null ||
				this.getPrivateKey() == null ||
				this.getPublicKey() == null)
			return false;
		BigInteger verif = this.getG().modPow(this.getPrivateKey(), this.getP());
		if(verif.compareTo(this.getPublicKey())==0)
			return true;
		else
			return false;
	}

	public AsymmetricKeyParameter getPrivateKeyAs() {
		return privateKeyAs;
	}

	public void setPrivateKeyAs(AsymmetricKeyParameter privateKeyAs) {
		this.privateKeyAs = privateKeyAs;
	}

	public AsymmetricKeyParameter getPublicKeyAs() {
		return publicKeyAs;
	}

	public void setPublicKeyAs(AsymmetricKeyParameter publicKeyAs) {
		this.publicKeyAs = publicKeyAs;
	}
}
