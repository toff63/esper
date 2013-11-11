package net.francesbagual.github.esper;

import com.espertech.esper.client.UpdateListener;

public interface UpdateListenerWithExpression extends UpdateListener{

	public String getExpression();
}
