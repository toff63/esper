package net.francesbagual.github.esper;

import com.espertech.esper.client.EPServiceProvider;

public class Main {

	public static void main(String[] args) {
		EsperEngine engine = new EsperEngine();
		engine.start();
		sendEvents(engine.getEpService());
	}

	private static void sendEvents(EPServiceProvider epService) {
		epService.getEPRuntime().sendEvent(new OrderEvent("shirt", 74.50));
		epService.getEPRuntime().sendEvent(new OrderEvent("shirt", 100));
		epService.getEPRuntime().sendEvent(new OrderEvent("shirt", 60));
		epService.getEPRuntime().sendEvent(new OrderEvent("shirt", 74.50));
	}
}
