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

/**
 * AsymKeysImpl contains the public key (and P and G) and eventually the private key
 * If needed, this class can call KeyGenerator
 */
public class AsymKeysImpl implements util.secure.encryptionInterface.AsymKeys<BigInteger> {
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
		publicKey = ((ElGamalPublicKeyParameters) cipher1.getPublic()).getY();
		privateKey = ((ElGamalPrivateKeyParameters)cipher1.getPrivate()).getX();
		wellGenerated = true;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*/																										  /*/
	/*/									OLD SARAH'S GENERATION OF KEYS										  /*/
	/*/																			@DEPRECATED					  /*/
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @deprecated
	 * This method is used for generate P (without BountyCastle)
	 */
	@SuppressWarnings("unused")
	private void GenerateP()
	{
		BigInteger p,q;
		p = BigInteger.valueOf(4);
		q = ONE;
		while (!p.isProbablePrime(20))
		{
			q = BigInteger.probablePrime(pLength-1, random);
			while (q.bitLength()!=pLength-1)
				q = BigInteger.probablePrime(pLength-1, random);
			p = q.multiply(TWO).add(ONE);
		}
		this.p = p;
		this.q = q;
		pGenerated = true;
	}
	
	/**
	 * @deprecated
	 * This method is used for generate G. If P isn't generated, the system crash (without BountyCastle)
	 */
	@SuppressWarnings("unused")
	private void GenerateG()
	{
		if(!pGenerated){
			System.err.println("ERROR "+this.getClass().getName()+" : G can't be generated !!!");
			System.exit(-1);
		}
		BigInteger g = BigInteger.ZERO;
		BigInteger n = p.subtract(ONE);
		BigInteger p1 = TWO;
		BigInteger p2 = q;
		Boolean ok = false;
		while (!ok)
		{
			g = new BigInteger(pLength,random);
			while (g.compareTo(p) >= 0 || g.compareTo(BigInteger.ONE)<=0)
				g = new BigInteger(pLength,random);
			
				BigInteger b1 = g.modPow(n.divide(p1), p);
				BigInteger b2 = g.modPow(n.divide(p2), p);
				
				if (!b1.equals(ONE) && !b2.equals(ONE))
					ok = true;	
		}
		this.g = g;
	}
	
	/**
	 * @deprecated
	 * This method is used to generate Keys (without BountyCastle)
	 */
	@SuppressWarnings("unused")
	private void GenerateKeys(){
		privateKey = new BigInteger(keyLength,random);
		while (privateKey.compareTo(p) >= 0 || privateKey.compareTo(BigInteger.ONE)<=0)
			privateKey = new BigInteger(keyLength,random);
		publicKey = g.modPow(privateKey,p);
		this.wellGenerated = true;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*/																										  /*/
	/*/										CLASS GESTION BY MICHAEL										  /*/
	/*/																										  /*/
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This constructor is used for unknown PrivateKey
	 */
	public AsymKeysImpl(BigInteger p, BigInteger g, BigInteger publicKey){
		this.privateKey = null;
		this.publicKey = publicKey;
		this.p = p;
		this.g = g;
	}
	
	/**
	 * This constructor is used if Keys are already generated.
	 */
	public AsymKeysImpl(BigInteger p, BigInteger g, BigInteger publicKey, BigInteger privateKey) throws Exception{
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
	public AsymKeysImpl(){
	}
	
	/**
	 * This constructor is used for generate the public key and private key with possibility of generation p and q.
	 * @param pgGenerated - true for generate p and q, false else.
	 */
	public AsymKeysImpl(boolean pgGenerate){
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
	
	@Override
	public boolean generate() {
		ElGamalParameters params;
		params = GeneratePG();
		GenerateKeys(params);
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
}
