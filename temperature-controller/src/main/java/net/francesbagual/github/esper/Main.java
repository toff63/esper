package net.francesbagual.github.esper;

import java.util.Random;

import com.espertech.esper.client.EPRuntime;


public class Main {

	public static void main(String[] args) {
		EsperEngine engine = new EsperEngine();
		engine.start();
		generateTemperatureEvents(engine.getEpService().getEPRuntime());
		generateErrors(engine.getEpService().getEPRuntime());
	}

	private static void generateErrors(EPRuntime runtine) {
		runtine.sendEvent(new TemperatureEvent(101));
		runtine.sendEvent(new TemperatureEvent(110));
		runtine.sendEvent(new TemperatureEvent(115));
		runtine.sendEvent(new TemperatureEvent(200));
	}

	private static void generateTemperatureEvents(EPRuntime runtine) {
		Random r = new Random();
		for(int i = 0; i< 50; i++) sendEventAndWait(runtine, r);
	}

	private static void sendEventAndWait(EPRuntime runtine, Random r) {
		runtine.sendEvent(new TemperatureEvent(r.nextInt(600)));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
