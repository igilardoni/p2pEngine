package util.secure;

import java.math.BigInteger;

public class ElGamalEncrypt {
		private BigInteger u;
		private BigInteger v;
		private BigInteger k; // only for SigmaProtocol 
		/**
		 * Initialize the ElGamal signature with the right R & S
		 * @param r
		 * @param s
		 */
		public ElGamalEncrypt(BigInteger u, BigInteger v, BigInteger k) {
			this.u = u;
			this.v = v;
			this.k = k;
		}
		
		public BigInteger getU() {
			return u;
		}
		
		public BigInteger getV() {
			return v;
		}
		
		public BigInteger getK() {
			return k;
		}
		
}
