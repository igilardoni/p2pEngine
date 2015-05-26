package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import model.item.Item;
import model.manager.Manager;
import model.user.Message;
import model.user.User;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import util.VARIABLES;

/**
 * This class recoveries data in local (hard drive)
 * @author michael
 *
 */
public class LocalRecovery {
	private static final String tagManager = "ManagerLocalPath";
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static File configFile = null;
	private static Document document = null;
	
	private static String managerSaved;
	
	public static void init(){
		if(isWindows())
			configFile = new File(VARIABLES.ConfigFilePathWindows);
		else if(isUnix())
			configFile = new File(VARIABLES.ConfigFilePathLinux);
		else if(isMac())
			configFile = null;
		else if(isSolaris())
			configFile = null;
		if(configFile == null){
			System.err.println("Unknow Os");
			return;
		}
		SAXBuilder sxb = new SAXBuilder();
		try{
			document = sxb.build(configFile);
		}
		catch(Exception e){}
		
		recoveryManager();
	}
	
	public static void saveManager(Manager m){
		try{
	    	XMLOutputter output=new XMLOutputter();
	    	output.output(document, new FileOutputStream(configFile));
		}catch (IOException e){
			e.printStackTrace();
		}
		Manager manager = new Manager(managerSaved, null);
		for(User u : m.getUsers()){
			manager.addUser(u);
			for (Item i : m.getUserItems(u.getKeys().getPublicKey().toString(16))) {
				manager.addItem(i);
			}
			for (Message msg : m.getUserMessages(u.getKeys().getPublicKey().toString(16))) {
				manager.addMessage(msg);
			}
			manager.addConversations(m.getUserConversations(u.getKeys().getPublicKey().toString(16)));
		}
		managerSaved = manager.toString();
		String managerLocalPath = document.getRootElement().getChildText(tagManager);
		try {
			OutputStream stream = new FileOutputStream(managerLocalPath);
			OutputStreamWriter writer = new OutputStreamWriter(stream);
			BufferedWriter buff = new BufferedWriter(writer);
			buff.write(managerSaved);
			buff.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setManagerConfig(String managerPath){
		document.getRootElement().removeChildren(tagManager);
		Element e = new Element(tagManager);
		e.addContent(managerPath);
		document.addContent(e);
	}
	
	public static boolean managerIsRecovered(){
		return !managerSaved.isEmpty();
	}
	
	public static String getManagerSaved(){
		return managerSaved != null ? managerSaved : "";
	}
	
	private static void recoveryManager(){
		String managerLocalPath = document.getRootElement().getChildText(tagManager);
		try{
			InputStream stream = new FileInputStream(managerLocalPath);
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buff = new BufferedReader(reader);
			StringBuffer manager = new StringBuffer();
			String line;
			while ((line=buff.readLine())!=null){
				manager.append(line); 
			}
			buff.close(); 
			managerSaved = manager.toString();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
 
	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
 
	private static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
 
	private static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}
	
	public static void main(String[] args){
		System.out.println(LocalRecovery.getManagerSaved());
	}
}
