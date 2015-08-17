package view.interlocutors;

import controller.ManagerBridge;
import model.Application;

public class TurnOff extends AbstractInterlocutor {

	public TurnOff() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			ManagerBridge.logout();
			Application.getInstance().close();
		} finally {
			this.reset();
		}
	}

}
