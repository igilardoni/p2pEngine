package model.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import crypt.impl.key.ElGamalAsymKey;
import crypt.impl.signatures.ElGamalSignature;
import model.api.ContractEntity;
import protocol.api.Wish;

@Entity
public class ElGamalContractEntity implements ContractEntity<ElGamalSignature>{
	
	private List<ElGamalAsymKey> parties;
	private Map<ElGamalAsymKey, ElGamalSignature> signatures;
	private Wish wish;
	
	@Override
	public List<ElGamalAsymKey> getParties() {
		return parties;
	}
	@Override
	public Map<ElGamalAsymKey, ElGamalSignature> getSignatures() {
		return signatures;
	}
	public Wish getWish() {
		return wish;
	}
	public void setWish(Wish wish) {
		this.wish = wish;
	}
}
	
	