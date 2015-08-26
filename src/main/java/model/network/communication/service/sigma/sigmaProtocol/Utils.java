package model.network.communication.service.sigma.sigmaProtocol;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * this class is util class
 * @author sarah
 *
 */
public class Utils {

	static SecureRandom  random = new SecureRandom();
	
	/**
	 * random value
	 * @param bitLength
	 * @param object
	 * @return
	 */
	static public BigInteger rand (int bitLength, BigInteger p)
	{
		BigInteger s;
		s = new BigInteger(bitLength,random);
		while(s.compareTo(BigInteger.ONE)<=0 && s.compareTo(p)>= 0)
			s = new BigInteger(bitLength,random);
		
		return s;
	}
    
	public static String toHex(byte[] donnees) {
	return javax.xml.bind.DatatypeConverter.printHexBinary(donnees);
    }
	/*static public BigInteger randPrime(int bitLength)
	{
		
	}*/
}
