package model.network.communication.service.sigma.sigmaProtocol;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import org.bouncycastle.crypto.engines.ElGamalEngine;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;

/**
 * this class simulate the arbiter but in the end all users have this class
 * the arbiter can described message, and in the protocol CCD
 * @author sarah
 *
 */
public class Trent {

	SecureRandom  random = new SecureRandom();
	int keyLength = 1024;
	
	AsymKeysImpl keys;
	private HashMap<Masks,BigInteger> eph = new HashMap<Masks, BigInteger>();
	private ElGamalEngine e = new ElGamalEngine(); // TODO never used ?
	
	/**
	 * Constructor
	 */
	public  Trent(AsymKeysImpl keys){
			
		this.keys = keys;
	
	 }

	/**
	 * Create mask for the CCD response
	 * @param res
	 * @return Masks
	 */
	private Masks SendMasks(ResEncrypt res)
	{
		BigInteger s;
		s = Utils.rand(160, keys.getP());
		
		BigInteger a, aBis;
		
		a = keys.getG().modPow(s, keys.getP());
		aBis = res.getU().modPow(s, keys.getP());
		
		Masks masks = new Masks(a,aBis);
		eph.put(masks, s);
		
		return masks;
	}
	
	/**
	 * Create challenge for the not interactive version for the CCD
	 * @param mask
	 * @param message
	 * @return
	 */
	private BigInteger SendChallenge(Masks mask, byte[] message)
	{
		BigInteger challenge;
		byte[] buffer, resume;
		MessageDigest hash_function = null;
		
		String tmp = message.toString().concat(mask.getA().toString());
		
		buffer = tmp.getBytes();
		
		try {
			hash_function = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		resume = hash_function.digest(buffer);
		challenge = new BigInteger(resume);
		return challenge;
	}
	
	/**
	 * Create reponse CCD 
	 * @param challenge
	 * @param mask
	 * @return BigInteger
	 */
	private BigInteger SendAnswer(BigInteger challenge, Masks mask)
	{
		BigInteger r = (keys.getPrivateKey().multiply(challenge)).add(eph.get(mask));
		return r;	
	}

	/**
	 * Create response CCD will send
	 * @param resEncrypt
	 * @return
	 */
	public ResponsesCCD SendResponse(ResEncrypt resEncrypt)
	{		
		Masks mask = this.SendMasks(resEncrypt);
		BigInteger challenge = this.SendChallenge(mask, resEncrypt.getM());
		BigInteger response = this.SendAnswer(challenge, mask);
		
		return new ResponsesCCD(mask,challenge,response);
	}
	
	/**
	 * decrypt
	 * @param cipherText
	 * @return
	 */
	public  byte[] decryption (byte[]cipherText)
	{
		ElGamal elGamal = new ElGamal (keys);
        return elGamal.decryptWithPrivateKey(cipherText);
	}

	
	public AsymKeysImpl getKey() {
		return keys;
	}

}
