package model.data.manager;

/**
 * This interface can be implemented for save and recover Local Data
 */
public interface RecoveryManager {
	public void recovery(String path);
	public void saving(String path);
}
