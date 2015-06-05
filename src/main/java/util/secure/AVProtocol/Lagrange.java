package util.secure.AVProtocol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * use for resolve message
 * @author sarah
 *
 */
public class Lagrange {

	 /*public static BigInteger inter(BigInteger [] xin, BigInteger [] yin, BigInteger x)
	 {
		 int n = xin.length;
		 
		 BigInteger L = BigInteger.ZERO;
		 
		 for (int j = 0; j<n ; j++)
		 {
			 BigInteger tmp = BigInteger.ONE;
			 for (int i = 0 ; i< n ; i++)
			 {
				 if (i!=j)
				 {
					 BigInteger t [] = (x.subtract(xin[i])).divideAndRemainder(xin[j].subtract(xin[i]));
					 tmp = tmp.multiply(t[0].add(t[1]));
				 }
			 }
			 System.out.println(tmp);

			 tmp = tmp.multiply(yin[j]);
			 L = L.add(tmp);
		 }
		 return L;
	 }*/

	/**
	 * lagrange formula
	 * @param xin
	 * @param yin
	 * @param x
	 * @return
	 */
	public static BigInteger inter(BigDecimal [] xin, BigDecimal [] yin, BigDecimal x)
	 {
		int n = xin.length;
 
		BigDecimal L = BigDecimal.ZERO;
		
		for (int i =0; i< n ; i++)
			L = L.add(CalculL(  xin,   yin,  x,  n,  i));
		
		BigInteger result = L.toBigInteger();
		return result;		
	 }
	
	/**
	 * Lagrnage polynom
	 * problem with BigInteger and divisor 
	 * @param xin
	 * @param yin
	 * @param x
	 * @param n
	 * @param j
	 * @return
	 */
	public static BigDecimal CalculL(BigDecimal [] xin, BigDecimal [] yin, BigDecimal x, int n, int j){
		
		BigDecimal G = BigDecimal.ONE;
		BigDecimal D = BigDecimal.ONE;
		
		for (int i = 0 ; i< n; i++)
			if (j!=i)
				G = G.multiply(x.subtract(xin[i]));
		
		for (int i = 0; i< n; i++)
			if (j!=i)
				D = D.multiply(xin[j].subtract(xin[i]));
		
		BigDecimal tp = (yin[j]).multiply(G);
		return tp.divide(D,100, BigDecimal.ROUND_HALF_DOWN);
	}
}
