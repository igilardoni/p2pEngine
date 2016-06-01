package network.api;

import java.util.Collection;

public interface Search<Sign, T extends Advertisement<Sign>> {
	public void initialize(Service s);
	public Collection<T> search(String attribute, String value);
}
