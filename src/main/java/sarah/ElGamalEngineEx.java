package sarah;


import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ElGamalKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.BigIntegers;

/**
 * this classe is an extention of class ElGamalEngine from BouncyCastle 's library
 * we need him for the signature, to recover the random value which used for the encryption by ElGamal
 * @author sarah
 *
 */
public class ElGamalEngineEx extends org.bouncycastle.crypto.engines.ElGamalEngine {
	
	private ElGamalKeyParameters    key;
    private SecureRandom            random;
    private boolean                 forEncryption;
    private int                     bitSize;

    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
	private BigInteger k;
	private BigInteger gamma;
	private BigInteger phi;
	
	/**
	 * Constructor
	 */
	
	public ElGamalEngineEx()
	{
		super();
	}
	
	/**
	 * init like the ElGamalEngine 
	 */
	public void init(
	        boolean             forEncryption,
	        CipherParameters    param)
	        {
		super.init(forEncryption, param);
		if (param instanceof ParametersWithRandom)
        {
            ParametersWithRandom    p = (ParametersWithRandom)param;

            this.key = (ElGamalKeyParameters)p.getParameters();
            this.random = p.getRandom();
        }
        else
        {
            this.key = (ElGamalKeyParameters)param;
            this.random = new SecureRandom();
        }

        this.forEncryption = forEncryption;

        BigInteger p = key.getParameters().getP();

        bitSize = p.bitLength();

        if (forEncryption)
        {
            if (!(key instanceof ElGamalPublicKeyParameters))
            {
                throw new IllegalArgumentException("ElGamalPublicKeyParameters are required for encryption.");
            }
        }
        else
        {
            if (!(key instanceof ElGamalPrivateKeyParameters))
            {
                throw new IllegalArgumentException("ElGamalPrivateKeyParameters are required for decryption.");
            }
        }
	        }
	
	/**
	 * this method is used for encryption and decryption by ElGamal 
	 * it's the same in BouncyCastle
	 * Just the random variable k is in parameter of class
	 */
	public byte[] processBlock(
	        byte[]  in,
	        int     inOff,
	        int     inLen)
	    {
	        if (key == null)
	        {
	            throw new IllegalStateException("ElGamal engine not initialised");
	        }

	        int maxLength = forEncryption
	            ?   (bitSize - 1 + 7) / 8
	            :   getInputBlockSize();

	        if (inLen > maxLength)
	        {
	            throw new DataLengthException("input too large for ElGamal cipher.\n");
	        }

	        BigInteger  p = key.getParameters().getP();

	        if (key instanceof ElGamalPrivateKeyParameters) // decryption
	        {
	            byte[]  in1 = new byte[inLen / 2];
	            byte[]  in2 = new byte[inLen / 2];

	            System.arraycopy(in, inOff, in1, 0, in1.length);
	            System.arraycopy(in, inOff + in1.length, in2, 0, in2.length);

	            gamma = new BigInteger(1, in1);
	            phi = new BigInteger(1, in2);

	            ElGamalPrivateKeyParameters  priv = (ElGamalPrivateKeyParameters)key;
	            // a shortcut, which generally relies on p being prime amongst other things.
	            // if a problem with this shows up, check the p and g values!
	            BigInteger  m = gamma.modPow(p.subtract(ONE).subtract(priv.getX()), p).multiply(phi).mod(p);

	            return BigIntegers.asUnsignedByteArray(m);
	        }
	        else // encryption
	        {
	            byte[] block;
	            if (inOff != 0 || inLen != in.length)
	            {
	                block = new byte[inLen];

	                System.arraycopy(in, inOff, block, 0, inLen);
	            }
	            else
	            {
	                block = in;
	            }

	            BigInteger input = new BigInteger(1, block);

	            if (input.compareTo(p) >= 0)
	            {
	                throw new DataLengthException("input too large for ElGamal cipher.\n");
	            }

	            ElGamalPublicKeyParameters  pub = (ElGamalPublicKeyParameters)key;

	            int                         pBitLength = p.bitLength();
	            k = new BigInteger(pBitLength, random);

	            while (k.equals(ZERO) || (k.compareTo(p.subtract(TWO)) > 0))
	            {
	                k = new BigInteger(pBitLength, random);
	            }

	            BigInteger  g = key.getParameters().getG();
	            gamma = g.modPow(k, p);
	            phi = input.multiply(pub.getY().modPow(k, p)).mod(p);

	            byte[]  out1 = gamma.toByteArray();
	            byte[]  out2 = phi.toByteArray();
	            byte[]  output = new byte[this.getOutputBlockSize()];

	            if (out1.length > output.length / 2)
	            {
	                System.arraycopy(out1, 1, output, output.length / 2 - (out1.length - 1), out1.length - 1);
	            }
	            else
	            {
	                System.arraycopy(out1, 0, output, output.length / 2 - out1.length, out1.length);
	            }

	            if (out2.length > output.length / 2)
	            {
	                System.arraycopy(out2, 1, output, output.length - (out2.length - 1), out2.length - 1);
	            }
	            else
	            {
	                System.arraycopy(out2, 0, output, output.length - out2.length, out2.length);
	            }

	            return output;
	        }
	    }
	
	/**
	 * @return k the random variable used for encryption
	 */
	public BigInteger getK()
	{
		return k;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigInteger getGamma()
	{
		return gamma;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigInteger getPhi()
	{
		return phi;
	}

}
