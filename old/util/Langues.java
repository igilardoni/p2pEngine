/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package util;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Localisation of Strings.
 * @author Prudhomme Julien
 *
 */

public class Langues {
	private Langues() {
		// do not instantiate
	}
	
	private static final String BUNDLE_NAME = "util.messages";
	private static ResourceBundle RESOURCE_BUNDLE = loadBundle();
	
	public static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	/**
	 * Retrieve a string according to the key and the current Locale.
	 * @param key the string's key
	 * @return the according string in the current user language.
	 */
	public static String getString(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
	
	/**
	 * Call if the current langage may change.
	 */
	public static void updateLanguage() {
		RESOURCE_BUNDLE = loadBundle();
	}
}
