package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Classe permettant de connaitre son ip publique
 * @author Prudhomme Julien
 *
 */
public class IpChecker {

	/**
	 * Retourne notre ip publique (comme vu de l'extérieur)
	 * /!\ Attention aux NATs /!\
	 * 
	 * @return l'ip
	 * @throws Exception
	 */
    public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
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