package util.secure.AVProtocol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

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

	public static BigInteger inter(BigInteger [] xin, BigInteger [] yin, BigInteger x)
	 {
		int n = xin.length;
 
		BigInteger L = BigInteger.ZERO;
		
		for (int i =0; i< n ; i++)
			L = L.add(CalculL(  xin,   yin,  x,  n,  i));
		
		return L;		
	 }
	
	public static BigInteger CalculL(BigInteger [] xin, BigInteger [] yin, BigInteger x, int n, int j){
		
		BigInteger G = BigInteger.ONE;
		BigInteger D = BigInteger.ONE;
		
		for (int i = 0 ; i< n; i++)
			if (j!=i)
				G = G.multiply(x.subtract(xin[i]));
		
		for (int i = 0; i< n; i++)
			if (j!=i)
				D = D.multiply(xin[j].subtract(xin[i]));
		
		BigInteger tp = (yin[j]).multiply(G);
		return tp.divide(D);
	}
}
