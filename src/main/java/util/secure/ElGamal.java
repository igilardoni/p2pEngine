package util.secure;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.engines.ElGamalEngine;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

import util.Hasher;
import util.Hexa;
import util.secure.encryptionInterface.AsymEncryption;
import util.secure.encryptionInterface.AsymKeys;

public class ElGamal implements AsymEncryption<byte[], BigInteger> {
	
	public SecureRandom  random = new SecureRandom();
	
	private AsymKeysImpl keys;
	
	private BigInteger r = null;
	private BigInteger s = null;
	
	/**
	 * Constructor
	 * @param keys
	 */
	public ElGamal(AsymKeysImpl keys){
		this.keys = keys;
	}
	
	/**
	 * Empty constructor
	 */
	public ElGamal(){}
	
	public void setKeys(AsymKeysImpl keys){
		this.keys = keys;
	}
	
	public void setR(byte[] r){
		this.r = new BigInteger(r);
	}
	public void setS(byte[] s){
		this.s = new BigInteger(s);
	}
	public byte[] getR(){
		return r.toByteArray();
	}
	public byte[] getS(){
		return s.toByteArray();
	}
	
	/**
	 * To sign a message
	 * @param M - byte[]
	 */
	public void Signs(byte[] M) throws Exception
	{
		if(keys.getPrivateKey() == null)
			throw new Exception("Private key unknown");
		BigInteger k;
		BigInteger l;
		BigInteger m = new BigInteger(Hasher.SHA256(M).getBytes());
		k = BigInteger.probablePrime(1023, random);
		while(k.compareTo(BigInteger.ONE)<= 0 || k.gcd(keys.getP()).compareTo(BigInteger.ONE)!= 0 )
		{
			k = BigInteger.probablePrime(1023, random);
		}
		l = k.modInverse(keys.getP().subtract(BigInteger.ONE));
		
		r = keys.getG().modPow(k,keys.getP());
		s = l.multiply(m.subtract(r.multiply(keys.getPrivateKey())).mod(keys.getP().subtract(BigInteger.ONE)));
	}
	
	/**
	 * To verify a signature
	 * @param M - byte[]
	 * @return true if the signature is from public Key, false else
	 * @throws Exception
	 */
	public boolean VerifieSignature(byte[] M) throws Exception
	{
		if(r==null || s==null)
			throw new Exception("R or S unknown");
		BigInteger m = new BigInteger(Hasher.SHA256(M).getBytes());
		BigInteger v = keys.getG().modPow(m, keys.getP());
		BigInteger w = (keys.getPublicKey().modPow(r, keys.getP()).multiply(r.modPow(s, keys.getP())).mod(keys.getP()));
		
		return (v.equals(w));
	}
	
	@Override
	public byte[] encryptWithPrivateKey(byte[] data) {
		/*TODO ATTENTION CECI N'A PAS D'INTERET
		 * A REMPLACER PAR "SIGNATURE"
		 * SORTIE :
		 * 			BigInteger r
		 * 			BigInteger s
		 * 			byte[] m
		 */
		return null;
	}

	@Override
	public byte[] decryptWithPublicKey(byte[] data) {
		/* TODO ATTENTION CECI N'A PAS D'INTERET
		* A REMPLACER PAR VERIFICATION DE LA SIGNATURE
		*/
		return null;
	}
	
	@Override
	public byte[] encryptWithPublicKey(byte[] data) {
		ElGamalParameters params = new ElGamalParameters(keys.getP(), keys.getG());
		ElGamalPublicKeyParameters pubKey = new ElGamalPublicKeyParameters(keys.getPublicKey(), params);
		
		ElGamalEngine e = new ElGamalEngine();
		e.init(true, pubKey);
        return e.processBlock(data, 0, data.length) ;
	}

	@Override
	public byte[] descryptWithPrivateKey(byte[] data) {
		ElGamalParameters params = new ElGamalParameters(keys.getP(), keys.getG());
		ElGamalPrivateKeyParameters privKey = new ElGamalPrivateKeyParameters(keys.getPrivateKey(), params);
		
		ElGamalEngine e = new ElGamalEngine();
		e.init(false, privKey);
		
        return e.processBlock(data, 0, data.length) ;
	}

	@Override
	public void setAsymsKeys(AsymKeys<BigInteger> keys) {
		this.keys = (AsymKeysImpl) keys;
	}
	
	///////////////////////// JUST FOR TEST \\\\\\\\\\\\\\\\\\\\\\\\\
	public static void main(String[] args){
		AsymKeysImpl key = new AsymKeysImpl(false);
		ElGamal eg = new ElGamal();
		eg.setAsymsKeys(key);
		
		String message = "Test message with\n\t $om€ €xØtic sø¥mbols\n\0";
		byte[] en = eg.encryptWithPublicKey(message.getBytes());
		String enString = Hexa.bytesToHex(en);
		String enReadable = Hexa.bytesToString(en);
		byte[] de = eg.descryptWithPrivateKey(Hexa.hexToBytes(enString));
		String deString = Hexa.bytesToHex(de);
		String deReadable = Hexa.bytesToString(de);
		
		System.out.println("Message : ");
		System.out.println("\"\t"+message+" \"\n");
		System.out.println("Encrypt Hex Format :");
		System.out.println("\"\t"+enString+" \"\n");
		System.out.println("Encrypt Readable Format :");
		System.out.println("\"\t"+enReadable+" \"\n");
		System.out.println("Decrypt Hex Format :");
		System.out.println("\"\t"+deString+" \"\n");
		System.out.println("Decrypt Readable Format :");
		System.out.println("\"\t"+deReadable+" \"\n");
	}
}
