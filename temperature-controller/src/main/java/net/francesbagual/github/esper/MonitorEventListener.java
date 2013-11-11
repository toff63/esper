package net.francesbagual.github.esper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;

public class MonitorEventListener implements UpdateListenerWithExpression{

	Logger log = LoggerFactory.getLogger(MonitorEventListener.class);
	private String expression = "select avg(temperature) as avg_val from TemperatureEvent.win:time_batch(10 sec)";		

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		log.info("The current temperature is " + newEvents[0].get("avg_val"));
	}
	
	public String getExpression() {
		return expression;
	}

}
