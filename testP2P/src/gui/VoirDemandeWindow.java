package gui;

import p2pEngine.Objet;

public class VoirDemandeWindow extends VoirObjetWindow{

	public VoirDemandeWindow(Application app, Objet o) {
		super(app, o);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getNameString() {
		return "Objet voulu : " + obj.getNom();
	}

	@Override
	protected String getOtherNameString() {
		return "Objet à donner : " + obj.getOtherName();
	}

}
