package crypt.impl.contracts;

import java.math.BigInteger;

import crypt.api.signatures.Signable;
import crypt.base.BaseContract;
import crypt.impl.key.ElGamalAsymKey;
import crypt.impl.signatures.ElGamalSignature;
import crypt.impl.signatures.ElGamalSigner;

/**
 * El-gamal based contract
 * @author Prudhomme Julien
 *
 */
public class ElGamalContract extends BaseContract<BigInteger, ElGamalAsymKey, ElGamalSignature, ElGamalSigner>{
	
	
	/**
	 * Create a new contract with an ElGamalSigner
	 */
	public ElGamalContract() {
		super();
		this.signer = new ElGamalSigner();
	}
	
	public ElGamalContract(Signable<ElGamalSignature> clauses) {
		super(clauses);
		this.signer = new ElGamalSigner();
	}
}
