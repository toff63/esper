package net.francesbagual.github.esper;

import java.util.Random;

import com.espertech.esper.client.EPRuntime;


public class Main {

	public static void main(String[] args) {
		EsperEngine engine = new EsperEngine();
		engine.start();
		generateTemperatureEvents(engine.getEpService().getEPRuntime());
	}

	private static void generateTemperatureEvents(EPRuntime runtine) {
		Random r = new Random();
		for(int i = 0; i< 100; i++) sendEventAndWait(runtine, r);
	}

	private static void sendEventAndWait(EPRuntime runtine, Random r) {
		runtine.sendEvent(new TemperatureEvent(r.nextInt(50)));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
