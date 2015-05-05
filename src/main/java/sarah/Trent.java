package sarah;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ElGamalKeyPairGenerator;
import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;


/*
 * TODO NEED DESCRIPTION
 */
public class Trent {

	SecureRandom  random = new SecureRandom();
	int keyLength = 1024;

	public Keys publicKeys = new Keys();
	private BigInteger privateKey;

	private AsymmetricKeyParameter publicKeyAs;
	private AsymmetricKeyParameter privateKeyAs;
	private HashMap<Masks,BigInteger> eph = new HashMap<Masks, BigInteger>();
	private ElGamalEngineEx e = new ElGamalEngineEx();

	public  Trent(){

		GenerateKeys gK = new GenerateKeys(false);
		publicKeys.setG(gK.getG());
		publicKeys.setP(gK.getP());
		publicKeys.setPublicKey(gK.getPublicKey());

		privateKey = gK.getPrivateKey();
		privateKeyAs = gK.getPrivateKeyAs();
		publicKeyAs = gK.getPublicKeyAs();

	}


	private Masks SendMasks(ResEncrypt res)
	{
		BigInteger s;
		s = Utils.rand(160, publicKeys.getP());

		BigInteger a, aBis;

		a = publicKeys.getG().modPow(s, publicKeys.getP());
		aBis = res.getU().modPow(s, publicKeys.getP());

		Masks masks = new Masks(a,aBis);
		eph.put(masks, s);

		return masks;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resume = hash_function.digest(buffer);
		challenge = new BigInteger(resume);
		return challenge;
	}

	private BigInteger SendAnswer(BigInteger challenge, Masks mask)
	{
		BigInteger r = (privateKey.multiply(challenge)).add(eph.get(mask));
		return r;	
	}

	public ResponsesCCD SendResponse(ResEncrypt resEncrypt)
	{		
		Masks mask = this.SendMasks(resEncrypt);
		BigInteger challenge = this.SendChallenge(mask, resEncrypt.getM());
		BigInteger response = this.SendAnswer(challenge, mask);

		return new ResponsesCCD(mask,challenge,response);
	}

	public  byte[] decryption (byte[]cipherText)
	{
		e.init(false, privateKeyAs);

		byte[] output = e.processBlock(cipherText, 0, cipherText.length) ;
		return output;
	}


	public Keys getPublicKeys() {
		return publicKeys;
	}


	public AsymmetricKeyParameter getPublicKeyAs() {
		return publicKeyAs;
	}


	public void setPublicKeyAs(AsymmetricKeyParameter publicKeyAs) {
		this.publicKeyAs = publicKeyAs;
	}

}
