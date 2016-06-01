package network.impl.jxta;

import java.util.ArrayList;
import java.util.Collection;

import crypt.impl.signatures.ElGamalSignature;
import network.api.Advertisement;
import network.api.Search;
import network.api.SearchListener;
import network.api.Service;

public class JxtaSyncSearch<T extends Advertisement<ElGamalSignature>> implements Search<ElGamalSignature, T>, SearchListener<T>{

	private Service s;
	private ArrayList<T> results = new ArrayList<>();
	
	@Override
	public void initialize(Service s) {
		this.s = s;
	}

	@Override
	public Collection<T> search(String attribute, String value) {
		s.search(attribute, value, this);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public void notify(Collection<T> result) {
		results.addAll(result);
	}

}
