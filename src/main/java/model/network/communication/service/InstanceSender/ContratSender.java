package model.network.communication.service.InstanceSender;

import org.jdom2.Element;

import model.data.contrat.Contrat;

public class ContratSender extends ClassSenderService<Contrat>{

	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Contrat newInstance(Element root) {
		return new Contrat(root);
	}

}
