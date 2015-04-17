package util.secure;

import java.math.BigInteger;
import java.security.SecureRandom;

/*
 * Attention au effets de bords plz. (utiliser les valeurs de retour. GenerateP devrait retourner P.
 * Les 2 constructeurs sont quasi les meme, un peu de factorisation avec super() ca serait pas mal
 * 
 */

public class KeyGenerator {
	static SecureRandom  random = new SecureRandom();
	
	private static BigInteger ONE = BigInteger.ONE;
	private static BigInteger TWO = new BigInteger("2");
	private BigInteger p = new BigInteger ("124233341635855292420681698148845681014844866056212176632655173602444135581779341928584451946831820357622587249219477577145009300106828967466602146104562163160400103396735672041344557638270362523343149686623705761738910044071399582025053147811261321814632661084042311141045136246602979886564584763268994320823");
	private boolean pGenerated = false;
	private BigInteger g = new BigInteger ("57879985263161130068016239981615161174385902716647642452899971198439084259551250230041086427537114453738884538337956090286524329552098304591825815816298805245947460536391128315522193556464285417135160058086869161063941463490748168352401178939129440934609861888674726565294073773971086710395310743717916632171");
	
	private BigInteger q;
	private BigInteger privateKey;
	private BigInteger publicKey;
	
	private static int pLength = 1024;
	private static int keyLength = 160;
	
	/**
	 * This constructor is used for generate the public key and the private key with default p and q.
	 */
	public KeyGenerator(){
		privateKey = new BigInteger(keyLength,random);
		while (privateKey.compareTo(p) >= 0 || privateKey.compareTo(BigInteger.ONE)<=0)
			privateKey = new BigInteger(keyLength,random);
		publicKey = g.modPow(privateKey,p);
	}
	
	/**
	 * This constructor is used for generate the public key and private key with possibility of generation p and q.
	 * @param pgGenerated - true for generate p and q, false else.
	 */
	public KeyGenerator(boolean pgGenerate){
		if(pgGenerate){
			GenerateP();
			GenerateG();
		}
		privateKey = new BigInteger(keyLength,random);
		while (privateKey.compareTo(p) >= 0 || privateKey.compareTo(BigInteger.ONE)<=0)
			privateKey = new BigInteger(keyLength,random);
		publicKey = g.modPow(privateKey,p);
	}
	
	/**
	 * This method is used for generate P.
	 * @return
	 */
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
}
