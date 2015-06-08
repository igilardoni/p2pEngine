package util.secure.sigmaProtocol;

import java.math.BigInteger;

import util.secure.AsymKeysImpl;

/**
 * this class is used for the PCS and especially for the OR
 * it's a fabric for challenge, Mask and Response good for the different check
 * @author sarah
 *
 */

public class Fabric {

	/**
	 * fabric Challenge
	 * @param publicKeys
	 * @return
	 */
	private BigInteger FabricChallenge(AsymKeysImpl keys)
	{
		BigInteger c = Utils.rand(160, keys.getP());
		return c;
	}
	
	/**
	 * fabric Response
	 * @param publicKeys
	 * @return
	 */
	private BigInteger FabricResponse(AsymKeysImpl keys)
	{
		BigInteger r = Utils.rand(160, keys.getP());
		return r;
	}
	
	/**
	 * fabric a good Mask to send for the SchnorrProtocol
	 * @param c
	 * @param r
	 * @param publicKeys
	 * @return mask
	 */
	private Masks FabricMaskSchnorr(BigInteger c, BigInteger r,AsymKeysImpl keys)
	{
		BigInteger gPowr = keys.getG().modPow(r, keys.getP());
		BigInteger modInv = keys.getPublicKey().modPow(c,  keys.getP()).modInverse(keys.getP());
		BigInteger a = gPowr.multiply(modInv);
		Masks mask = new Masks(a,null);		
		return mask;
	}
	
	/**
	 * response to have send for Schnorr
	 * @param publicKeys
	 * @return responseSchnorr
	 */
	public ResponsesSchnorr SendResponseSchnorrFabric(AsymKeysImpl keys)
	{
		BigInteger challenge = this.FabricChallenge( keys);
		BigInteger response = this.FabricResponse(keys);
		Masks mask = this.FabricMaskSchnorr(challenge, response,keys);
		return new ResponsesSchnorr(mask,challenge,response);
	}
	
	/**
	 * fabric a good Mask to send for the CCE
	 * @param c
	 * @param r
	 * @param res
	 * @param keys
	 * @return
	 */
	private Masks FabricMaskCCE(BigInteger c, BigInteger r, ResEncrypt res, AsymKeysImpl keys)
	{
		BigInteger gPowr = keys.getG().modPow(r, keys.getP());
		BigInteger modInv = res.getU().modPow(c,  keys.getP()).modInverse(keys.getP());
		BigInteger a = gPowr.multiply(modInv);
		
		BigInteger pubPowr = keys.getPublicKey().modPow(r, keys.getP());
		BigInteger M = new BigInteger (res.getM());
		BigInteger vDivMPwc = res.getV().divide(M).modPow(c, keys.getP());
		BigInteger ModInv = vDivMPwc.modInverse(keys.getP());
		BigInteger aBis = pubPowr.multiply(ModInv);
		
		
		Masks mask = new Masks(a,aBis);		
		return mask;
	}
	
	/**
	 * response to have send for CCE
	 * @param publicKeys
	 * @return responseCCE
	 */
	public ResponsesCCE SendResponseCCEFabric(ResEncrypt res,AsymKeysImpl keys)
	{
		BigInteger challenge = this.FabricChallenge( keys);
		BigInteger response = this.FabricResponse(keys);
		Masks mask = this.FabricMaskCCE(challenge, response,res,keys);
		return new ResponsesCCE(mask,challenge,response);
	}
}
