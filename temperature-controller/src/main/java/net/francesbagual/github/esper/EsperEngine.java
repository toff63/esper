package net.francesbagual.github.esper;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class EsperEngine {

	private EPServiceProvider epService;
	
	public void start(){
		Configuration config = new Configuration();
		config.addEventTypeAutoName("net.francesbagual.github.esper");
		epService = EPServiceProviderManager.getDefaultProvider(config);
		registerListeners();

	}

	private void registerListeners() {
		registerListener(new MonitorEventListener());
		registerListener(new WarningEventListener());
		registerListener(new ErrorEventListener());
	}

	private void registerListener(UpdateListenerWithExpression listener){
		EPStatement statement = epService.getEPAdministrator().createEPL(listener.getExpression());
		statement.addListener(listener);
	}
	
	public EPServiceProvider getEpService() {
		return epService;
	}
}
