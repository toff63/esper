package net.francesbagual.github.esper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;

public class ErrorEventListener implements UpdateListenerWithExpression {
	
	private Logger log = LoggerFactory.getLogger(ErrorEventListener.class);
	
	private String expression = 
			  "select * from TemperatureEvent\n"
			+ "match_recognize(\n"
			+ "    measures A as temp1, B as temp2, C as temp3, D as temp4\n"
			+ "    pattern(A B C D)\n"
			+ "    define \n"
			+ "    		A as A.temperature > 100,\n"
			+ "			B as B.temperature > A.temperature,\n"
			+ "         C as C.temperature > B.temperature,\n"
			+ "         D as (D.temperature > C.temperature and D.temperature > (1.5 * A.temperature)))";		
	

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		Integer[] temperatures = new Integer[]{getTemperature(newEvents, "temp1"),
											   getTemperature(newEvents, "temp2"),
											   getTemperature(newEvents, "temp3"),
											   getTemperature(newEvents, "temp4")};
		log.error("Temperature is dangerously increasing: " + StringUtils.join(temperatures, " and then "));

	}

private int getTemperature(EventBean[] newEvents, String temp) {
	return ((TemperatureEvent) newEvents[0].get(temp)).getTemperature();
}

	public String getExpression() {
		return expression;
	}

}
