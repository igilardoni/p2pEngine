package util.secure;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.engines.ElGamalEngine;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

import util.Hasher;
import util.secure.encryptionInterface.AsymEncryption;
import util.secure.encryptionInterface.AsymKeys;
/**
 * This class is used for encryption, decryption, signs and verify signature.
 * @author michael
 *
 */
public class ElGamal implements AsymEncryption<byte[], BigInteger> {
	
	public SecureRandom  random = new SecureRandom();
	
	private AsymKeysImpl keys;
	
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
	
	/**
	 * To sign a message
	 * @param M - byte[]
	 */
	public byte[][] Signs(byte[] M) throws Exception
	{
		if(keys.getPrivateKey() == null)
			throw new Exception("Private key unknown");
		BigInteger k;
		BigInteger l;
		BigInteger r;
		BigInteger s;
		
		BigInteger m = new BigInteger(Hasher.SHA256(M).getBytes());
		k = BigInteger.probablePrime(1023, random);
		while(k.compareTo(BigInteger.ONE)<= 0 || k.gcd(keys.getP()).compareTo(BigInteger.ONE)!= 0 )
		{
			k = BigInteger.probablePrime(1023, random);
		}
		l = k.modInverse(keys.getP().subtract(BigInteger.ONE));
		
		r = keys.getG().modPow(k,keys.getP());
		s = l.multiply(m.subtract(r.multiply(keys.getPrivateKey())).mod(keys.getP().subtract(BigInteger.ONE)));
		return new byte[][]{r.toByteArray(),s.toByteArray()};
	}
	
	/**
	 * To verify a signature
	 * @param M - byte[]
	 * @return true if the signature is from public Key, false else
	 * @throws Exception
	 */
	public boolean VerifieSignature(byte[] M, byte[] rBytes, byte[] sBytes) throws Exception{
		if(rBytes==null || sBytes==null){
			throw new Exception("R or S unknown");
		}
		if(keys.getPublicKey() == null){
			throw new Exception("Public key unknown");
		}
		BigInteger r = new BigInteger(rBytes);
		BigInteger s = new BigInteger(sBytes);
		
		BigInteger m = new BigInteger(Hasher.SHA256(M).getBytes());
		BigInteger v = keys.getG().modPow(m, keys.getP());
		BigInteger w = (keys.getPublicKey().modPow(r, keys.getP()).multiply(r.modPow(s, keys.getP())).mod(keys.getP()));
		
		return (v.equals(w));
	}
	
	@Override
	@Deprecated
	public byte[] encryptWithPrivateKey(byte[] data) {
		// TODO Delete from interface ?
		return null;
	}

	@Override
	@Deprecated
	public byte[] decryptWithPublicKey(byte[] data) {
		// TODO Delete from interface ?
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
}
