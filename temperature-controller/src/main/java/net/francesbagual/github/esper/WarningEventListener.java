package net.francesbagual.github.esper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;

public class WarningEventListener implements UpdateListenerWithExpression {

	Logger log = LoggerFactory.getLogger(WarningEventListener.class);
	
	private String expression = 
			"select *  from TemperatureEvent \n"
			+ "match_recognize (\n"
			+ "		measures A as temp1, B as temp2\n"
			+ "		pattern(A B)\n"
			+ "		define \n"
			+ "  		A as A.temperature > 400,\n"
			+ "  		B as B.temperature > 400)";
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		int temp1 = ((TemperatureEvent) newEvents[0].get("temp1")).getTemperature();
		int temp2 = ((TemperatureEvent) newEvents[0].get("temp2")).getTemperature();
		log.warn("Two temperature threshold breach (> 400) in a row: " + temp1 + " and " + temp2);

	}
	public String getExpression() {
		return expression;
	}

}
