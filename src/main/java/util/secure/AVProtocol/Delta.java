package util.secure.AVProtocol;

/**
 * all to send in step 1
 * @author sarah
 *
 */
public class Delta {

	private Proof proof;
	private TTP ttp;
	
	public Delta (Proof proof, TTP ttp)
	{
		this.proof = proof;
		this.ttp = ttp;
	}
	
	public Proof getProof() {
		return proof;
	}
	
	public void setProof(Proof proof) {
		this.proof = proof;
	}
	
	public TTP getTtp() {
		return ttp;
	}
	
	public void setTtp(TTP ttp) {
		this.ttp = ttp;
	}
	
}
