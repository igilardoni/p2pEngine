package gui;

import p2pEngine.Objet;

public class VoirOffreWindow extends VoirObjetWindow{

	public VoirOffreWindow(Application app, Objet o) {
		super(app, o);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getNameString() {
		return "Objet à offrir : " + this.obj.getNom() + "";
	}

	@Override
	protected String getOtherNameString() {
		return "Objet voulu : " + this.obj.getOtherName() + "";
	}

}
