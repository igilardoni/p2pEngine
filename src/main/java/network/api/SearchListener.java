package network.api;

public interface SearchListener<T extends Advertisement<?>> {
	public void notify(T result);
}
