package util.secure.AVProtocol;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Proof ZeroKnowledge to send
 * Mi = g^mi
 * Aj = g^aj
 * M = g^m
 * @author sarah
 *
 */
public class Proof {

	private HashMap <ParticipantEx, BigInteger> MapMi = new HashMap<ParticipantEx, BigInteger>();
	private ArrayList <BigInteger> listAj = new ArrayList<BigInteger>();
	private BigInteger M;

	/**
	 * Constructor, with g^m, list g^mi, list g^aj 
	 * @param M
	 * @param ListMi
	 * @param ListAj
	 */
	public Proof (BigInteger M, HashMap<ParticipantEx, BigInteger> MapMi, ArrayList<BigInteger> ListAj)
	{
		this.M = M;
		this.MapMi = MapMi;
		this.listAj = ListAj;
	}
	
	/**
	 * Constructor, calcul g^m, list g^mi, list g^aj 
	 * @param M
	 * @param ListMi
	 * @param ListAj
	 */
	public Proof (BigInteger m, BigInteger g, BigInteger p, ArrayList<BigInteger> mi, ArrayList<BigInteger> aj, TTP TTP)
	{
		M = g.modPow(m, p);
		
		for (int i =0; i<mi.size(); i++)
			addMi(TTP.getParticipant(i),g.modPow(mi.get(i), p));
		
		for (BigInteger Aj : aj)
			addAj(g.modPow(Aj, p));
	}
	
	/**
	 * Constructor, just calcul g^m
	 * @param m
	 * @param p
	 * @param g
	 */
	public Proof (BigInteger m, BigInteger p, BigInteger g)
	{
		M = g.modPow(m, p);
	}
	
	/**
	 * 
	 * @param m
	 * @param p
	 * @param g
	 */
	public Proof (BigInteger m, BigInteger p, BigInteger g, ArrayList<BigInteger> aj)
	{
		M = g.modPow(m, p);
		
		for (BigInteger Aj : aj)
			addAj(g.modPow(Aj, p));
	}
	
	public BigInteger getM()
	{
		return M;
	}
	
	public void setM(BigInteger M)
	{
		this.M = M;
	}
	
	public void setM(BigInteger m, BigInteger p, BigInteger g)
	{
		M = g.modPow(m, p);
	}
	
	public ArrayList<BigInteger> getListAj()
	{
		return listAj;
	}	
	
	public BigInteger getAj(int j)
	{
		return listAj.get(j);
	}
	
	public void addAj(BigInteger Aj)
	{
		listAj.add(Aj);
	}
	
	public HashMap<ParticipantEx, BigInteger> getMapMi()
	{
		return MapMi;
	}
	
	public BigInteger getMapMi(ParticipantEx Pi)
	{
		return MapMi.get(Pi);
	}
	
	public void addMi(ParticipantEx Pi, BigInteger Mi)
	{
		MapMi.put(Pi, Mi);
	}
	
}
