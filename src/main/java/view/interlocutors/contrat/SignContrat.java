package view.interlocutors.contrat;

import view.interlocutors.AbstractInterlocutor;

public class SignContrat extends AbstractInterlocutor {

	public SignContrat() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			System.out.println("Trying to sign contract... Next release maybe ?");
			// TODO Interlocutor signature contract 
		} finally {
			this.reset();
		}
	}

}
