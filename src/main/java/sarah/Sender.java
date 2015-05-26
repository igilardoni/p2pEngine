package sarah;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;

/**
 * this class simulate the sender but in the end all users have this class
 * the sender sends all we need in the protocol (mask, responses), and encrypt
 * the sender extend Fabric, he has to be able to fabricated mask, challenge and response for the composability
 * @author sarah
 *
 */
public class Sender extends Fabric{
	
	SecureRandom  random = new SecureRandom();
	
	private Keys publicKeys = new Keys();
	
	
	private BigInteger privateKey;
	private AsymmetricKeyParameter privateKeyAs;
	
	private HashMap<Masks,BigInteger> eph = new HashMap<Masks, BigInteger>();
	private ElGamalEngineEx e = new ElGamalEngineEx();

	/**
	 * Constructor
	 * publicKey and PrivateKey are fixed
	 * @param publicKeys
	 * @param privateKeyAs
	 */
	public Sender (Keys publicKeys,AsymmetricKeyParameter privateKeyAs)
	{
		this.publicKeys = publicKeys;
		this.privateKeyAs = privateKeyAs;
		this.privateKey = ((ElGamalPrivateKeyParameters) privateKeyAs).getX();
	}
	
	/**
	 * Constructor
	 * All Keys are generated
	 */
	public Sender(){
		
		GenerateKeys gK = new GenerateKeys(false);
		publicKeys.setG(gK.getG());
		publicKeys.setP(gK.getP());
		publicKeys.setPublicKey(gK.getPublicKey());
		
		privateKey = gK.getPrivateKey();
		privateKeyAs = gK.getPrivateKeyAs();
        
    }
	
	/**
	 * Create mask to need send for the Shnorr 
	 * @return Masks
	 */
	public Masks SendMasksSchnorr() {
			
		BigInteger s, a;
		s = Utils.rand(1024, publicKeys.getP());
		a = publicKeys.getG().modPow(s,publicKeys.getP());
		
		Masks mask = new Masks(a,null);
		eph.put(mask, s);
		
		return mask;
	}
	
	/**
	 * Create response to need send for the Shnorr
	 * @return response in bigInteger
	 */
	private BigInteger ResponseSchnorr(BigInteger challenge,Masks mask)
	{
		BigInteger response = (privateKey.multiply(challenge)).add(eph.get(mask));
		return response;
	}
	
	/**
	 * Create responseSchnorr will send 
	 * @return response in bigInteger
	 */
	public ResponsesSchnorr SendResponseSchnorr(byte[] message)
	{
		Masks mask = this.SendMasksSchnorr();
		BigInteger challenge = this.SendChallenge(mask, message);
		BigInteger response = this.ResponseSchnorr(challenge, mask);
		
		return new ResponsesSchnorr(mask,challenge,response);
	}
	
	/**
	 * Create responseSchnorr will send, with challenge fixed
	 * @return response in bigInteger
	 */
	public ResponsesSchnorr SendResponseSchnorr(byte[] message, BigInteger challenge)
	{
		Masks mask = this.SendMasksSchnorr();
		BigInteger response = this.ResponseSchnorr(challenge, mask);
		
		return new ResponsesSchnorr(mask,challenge,response);
	}
	
	/**
	 * Create mask to need send for the CCE 
	 * @return Masks
	 */
	private Masks SendMasksCCE(Keys tKeys) {
		
		BigInteger s, a, aBis;
		s = Utils.rand(1024, tKeys.getP());
		
		a = tKeys.getG().modPow(s,tKeys.getP());		
		aBis = tKeys.getPublicKey().modPow(s, tKeys.getP());
		
		Masks masks = new Masks(a,aBis);
		eph.put(masks, s);
		
		return masks;
	}
	
	/**
	 * Create response to need send for the CCE
	 * @return response in bigInteger
	 */
	private BigInteger ResponseCCE(BigInteger challenge, Masks mask) {
		
		BigInteger k = e.getK();
		BigInteger response = (k.multiply(challenge)).add(eph.get(mask));
		return response;
	}
	
	/**
	 * Create responseCCE will send 
	 * @return response in bigInteger
	 */
	public ResponsesCCE SendResponseCCE(byte[] message, Keys tKeys)
	{
		Masks mask = this.SendMasksCCE(tKeys);
		BigInteger challenge = this.SendChallenge(mask, message);
		BigInteger response = this.ResponseCCE(challenge, mask);
		
		return new ResponsesCCE(mask,challenge,response);
	}
	
	/**
	 * Create responseCCE will send, with challenge fixed
	 * @return response in bigInteger
	 */
	public ResponsesCCE SendResponseCCE(byte[] message, Keys tKeys, BigInteger challenge)
	{
		Masks mask = this.SendMasksCCE(tKeys);
		BigInteger response = this.ResponseCCE(challenge, mask);
		
		return new ResponsesCCE(mask,challenge,response);
	}
	
	/**
	 * in not interactiv method the challenge is build, but we don't provide its
	 * @param mask
	 * @param message
	 * @return BigInteger (challenge)
	 */
	public BigInteger SendChallenge(Masks mask, byte[] message)
	{
		BigInteger challenge;
		byte[] buffer, resume;
		MessageDigest hash_function = null;
		
		String tmp = message.toString().concat(mask.getA().toString());
		
		buffer = tmp.getBytes();
		
		try {
			hash_function = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resume = hash_function.digest(buffer);
		challenge = new BigInteger(resume);
		return challenge;
	}
	
	/**
	 * Encryption
	 * @param input
	 * @param tpublicKeyAs
	 * @param tKeys
	 * @return resEncrypt (result of encryption)
	 */
	public  ResEncrypt Encryption(byte[] input, AsymmetricKeyParameter tpublicKeyAs, Keys tKeys)
	{
		e.init(true, tpublicKeyAs);
		
        byte[] cipherText = e.processBlock(input, 0, input.length) ;
        BigInteger M = new BigInteger (input); //a voir
        BigInteger u = tKeys.getG().modPow(e.getK(),tKeys.getP());
        BigInteger v = (tKeys.getPublicKey().modPow(e.getK(), tKeys.getP()).multiply(M));
        ResEncrypt res = new ResEncrypt(u,v,input);
        
        return res;
	}
	
	public Keys getPublicKeys() {
		return publicKeys;
	}
}
