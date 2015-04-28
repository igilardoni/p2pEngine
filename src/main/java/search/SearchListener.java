package search;

/**
 * Classes that want to know some searchs events should implements this interface
 * @author Julien Prudhomme
 *
 */
public interface SearchListener <T>{
	public void searchEvent(T event);
}
