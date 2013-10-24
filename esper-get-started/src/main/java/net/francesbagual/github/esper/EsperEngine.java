package net.francesbagual.github.esper;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class EsperEngine {

	private EPServiceProvider epService;
	public void start(){
		epService = EPServiceProviderManager.getDefaultProvider();
		registerListeners();
	}

	private void registerListeners() {
		String expression = "select avg(price) from net.francesbagual.github.esper.OrderEvent.win:time(30 sec)";
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		statement.addListener(new OrderEventListener());
	}
	
	public EPServiceProvider getEpService() {
		return epService;
	}
}
