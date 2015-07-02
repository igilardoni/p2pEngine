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
	public static final long MaxTimeSearch = 3000L;
	
	/* Network */
	public final static String NetworkFolderName = ".peerFolder";
	public final static String NetworkPeerName = "peer name";
	
	/* Store */
	public final static String ConfigFilePathWindows = "./SXPConfig.xml";
	public final static String ConfigFilePathLinux = "./SXPConfig.xml";
	public final static String ConfigFilePathMacOs = "";
	public final static String ConfigFilePathSolaris = "";
	public static final String ManagerFileName = "SXPManager.xml";
	public static final String ManagerFilePath = "./"+ManagerFileName;
	
	/* AVProtocol */
	public static final int AVProtocolN = 10;
	public static final int AVProtocolK = 5;
}
