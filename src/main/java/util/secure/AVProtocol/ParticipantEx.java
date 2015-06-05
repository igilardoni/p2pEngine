package util.secure.AVProtocol;

import java.math.BigInteger;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;
import util.secure.ElGamalEncrypt;

/**
 * each Participant in set TTP
 * public key's particpant, message mi encrypt for him, number in the set, x publicly element 
 * @author sarah
 *
 */

public class ParticipantEx {

	private AsymKeysImpl keys ;
	private BigInteger x;
	private int number ;
	private byte[] Mi;
	private byte [] miD;
	
	public ParticipantEx (AsymKeysImpl keys, int number, byte[] Mi)
	{
		this.setKeys(keys);
		this.setNumber(number);
		this.setMi(Mi);
	}

	public AsymKeysImpl getKeys() {
		return keys;
	}

	public void setKeys(AsymKeysImpl keys) {
		this.keys = keys;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public byte[] getMi() {
		return Mi;
	}

	public void setMi(byte[] mi) {
		Mi = mi;
	}

	public BigInteger getX() {
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}
	
	public void decryptMi ()
	{
		ElGamal elGamal = new ElGamal (keys);
		setMiD(elGamal.decryptWithPrivateKey(Mi));
	}

	public byte [] getMiD() {
		return miD;
	}

	public void setMiD(byte [] miD) {
		this.miD = miD;
	}
}