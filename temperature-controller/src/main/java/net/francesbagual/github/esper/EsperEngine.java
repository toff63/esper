package net.francesbagual.github.esper;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class EsperEngine {

	private EPServiceProvider epService;
	
	public void start(){
		Configuration config = new Configuration();
		config.addEventTypeAutoName("net.francesbagual.github.esper");
		epService = EPServiceProviderManager.getDefaultProvider(config);
		registerMonitoring();
		registerWarnings();
	}

	private void registerWarnings() {
		WarningEventListener listener = new WarningEventListener();
		EPStatement statement = epService.getEPAdministrator().createEPL(listener.getExpression());
		statement.addListener(listener);
	}

	private void registerMonitoring() {
		MonitorEventListener listener = new MonitorEventListener();
		EPStatement statement = epService.getEPAdministrator().createEPL(listener.getExpression());
		statement.addListener(listener);
	}
	
	public EPServiceProvider getEpService() {
		return epService;
	}
}
