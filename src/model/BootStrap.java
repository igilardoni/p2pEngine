package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.jxta.platform.NetworkConfigurator;

/**
 * Permet de récuperer une liste de peer sur un serveur web
 * Pratique pour ne plus avoir a rentrer manuellement des adresses la premiere fois que l'on souhaite se connecter au réseau
 * @author crashxxl
 *
 */

public class BootStrap {
	
	/**
	 *  A executer avant de lancer le réseau.
	 * @param url Url d'une page web qui contient uniquement une liste d'adresses (une par ligne)
	 * @param configurator le configurateur du peer
	 */
	public static void bootStrap(String url, NetworkConfigurator configurator) {
        URL bootstrapper = null;
		try {
			bootstrapper = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    bootstrapper.openStream()));
            String adresse;
            while( (adresse = in.readLine()) != null)
            		configurator.addRdvSeedingURI(new URI(adresse));
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
