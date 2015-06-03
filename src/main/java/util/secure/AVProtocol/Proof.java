package util.secure.AVProtocol;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Proof ZeroKnowledge to send
 * Mi = g^mi
 * Aj = g^aj
 * M = g^m
 * @author sarah
 *
 */
public class Proof {

	private ArrayList <BigInteger> listMi = new ArrayList<BigInteger>();
	private ArrayList <BigInteger> listAj = new ArrayList<BigInteger>();
	private BigInteger M;

	/**
	 * Constructor, with g^m, list g^mi, list g^aj 
	 * @param M
	 * @param ListMi
	 * @param ListAj
	 */
	public Proof (BigInteger M, ArrayList<BigInteger> ListMi, ArrayList<BigInteger> ListAj)
	{
		this.M = M;
		this.listMi = ListMi;
		this.listAj = ListAj;
	}
	
	/**
	 * Constructor, calcul g^m, list g^mi, list g^aj 
	 * @param M
	 * @param ListMi
	 * @param ListAj
	 */
	public Proof (BigInteger m, BigInteger g, BigInteger p, ArrayList<BigInteger> mi, ArrayList<BigInteger> aj)
	{
		M = g.modPow(m, p);
		
		for (BigInteger Mi : mi)
			addMi(g.modPow(Mi, p));
		
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
	
	public BigInteger getM()
	{
		return M;
	}
	
	public void setM(BigInteger M)
	{
		this.M = M;
	}
	
	public void setGM(BigInteger m, BigInteger p, BigInteger g)
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
	
	public ArrayList<BigInteger> getListMi()
	{
		return listMi;
	}
	
	public BigInteger getMi(int i)
	{
		return listMi.get(i);
	}
	
	public void addMi(BigInteger Mi)
	{
		listMi.add(Mi);
	}
	
}
