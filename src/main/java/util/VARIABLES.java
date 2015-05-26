package util;

public class VARIABLES {
	/* Items */
	public static final long LifeTimeAfterDisconnected = 2592000000L;
	public static final long TimeBeforeDeleteAfterLifeTime = 2678400L;
	
	/* Communication */
	public final static String SERVICE_TAG = "toService";
	
	/* SharingManager */
	public final static int ReplicationsAccount = 5;
	public final static int CheckTimeAccount = 3000;
	
	/* Network */
	public final static String NetworkFolderName = ".peerFolder";
	public final static String NetworkPeerName = "peer name";
	
	/* Store */
	public final static String ConfigFilePathWindows = "./SXPConfig.xml";
	public final static String ConfigFilePathLinux = "/etc/SXPConfig.xml";
	public final static String ConfigFilPathMacOs = "";
}
