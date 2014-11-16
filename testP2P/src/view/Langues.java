package view;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * MultiLangue. Les messages sont pris dans message_[fr/en..].properties
 * @author Prudhomme Julien
 *
 */

public class Langues {
	private Langues() {
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	////////////////////////////////////////////////////////////////////////////
	private static final String BUNDLE_NAME = "view.messages"; //$NON-NLS-1$
	private static ResourceBundle RESOURCE_BUNDLE = loadBundle();
	public static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	////////////////////////////////////////////////////////////////////////////
	public static String getString(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
	
	public static void updateLanguage() {
		RESOURCE_BUNDLE = loadBundle();
	}
}
